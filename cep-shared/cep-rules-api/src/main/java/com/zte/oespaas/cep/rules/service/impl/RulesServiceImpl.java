package com.zte.oespaas.cep.rules.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zte.oespaas.cep.common.model.Rule;
import com.zte.oespaas.cep.common.model.RulesKeyObject;
import com.zte.oespaas.cep.common.model.RulesMessage;
import com.zte.oespaas.cep.common.utils.JsonUtilForAPM;
import com.zte.oespaas.cep.db.core.RuleEntity;
import com.zte.oespaas.cep.db.resources.RuleEntityResource;
import com.zte.oespaas.cep.engine.api.EngineManager;
import com.zte.oespaas.cep.engine.api.RuleEngine;
import com.zte.oespaas.cep.rules.exception.TransactionException;
import com.zte.oespaas.cep.rules.service.RulesManager;
import com.zte.oespaas.cep.rules.service.RulesRepository;
import com.zte.oespaas.cep.rules.service.RulesService;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jvnet.hk2.annotations.Service;
import org.kie.api.builder.ReleaseId;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class RulesServiceImpl implements RulesService
{
    private final Logger logger = LoggerFactory.getLogger(RulesServiceImpl.class.getName());
    @Inject
    private RulesManager rulesManager;
    @Inject
    private RulesRepository ruleRepository;
    @Inject
    private RuleEntityResource ruleEntityResource;
    @Inject
    private EngineManager engineManager;


    @Override
    public RulesMessage getAllRules(String tenantId, String appId)
    {
        List<RuleEntity> ruleEntities = ruleEntityResource.get(tenantId, appId);
        return getRulesMessage(tenantId, appId, ruleEntities);
    }

    @Override
    public RulesMessage getRules(String tenantId, String appId, String ruleType)
    {
        List<RuleEntity> ruleEntities = ruleEntityResource.get(tenantId, appId, ruleType);

        return getRulesMessage(tenantId, appId, ruleEntities);


    }

    private RulesMessage getRulesMessage(String tenantId, String appId, List<RuleEntity> ruleEntities)
    {
        RulesMessage rulesMessage = new RulesMessage();
        rulesMessage.setTenantId(tenantId);
        rulesMessage.setAppId(appId);
        Rule[] rules = new Rule[ruleEntities.size()];

        for (int i = 0; i < ruleEntities.size(); i++)
        {
            Rule rule;
            try
            {
                rule = JsonUtilForAPM.fromJson(ruleEntities.get(i).getJson(), Rule.class);
            } catch (IOException e)
            {
                logger.error("Unable to process JSON");
                continue;
            }
            rules[i] = rule;
        }
        rulesMessage.setRules(rules);
        return rulesMessage;
    }

    @Override
    @Transaction
    public void addRules(RulesMessage rulesMessage) throws TransactionException
    {

        logger.info(rulesMessage.toString());
        Map<RulesKeyObject, List<Rule>> rules;
        try
        {
            addNewRuleToDb(rulesMessage);
            rules = queryRulesFromDbByRuleType(rulesMessage);
            doDeploy(rules);
        } catch (RuntimeException e)
        {
            // 1. 顺序只能是先插入，后部署（生成kjar，启动创建并启动ksession）
            // 2. 插入和部署属于原子操作，任一失败需要回退。插入失败，捕获异常后自动就不会部署了；但是如果插入成功，部署失败
            // 需要看是生成kjar失败还是ksession启动失败（由于是遍历操作，所以都存在部分和全部失败的可能性。），如果kjar成功了
            // ksession失败了需要有重试机制。反之恢复原来的kjar
//            if (rules != null) {
//                rules.entrySet().forEach(entry -> destroyRuleEngine(entry.getKey()));
//                ruleEntityResource.delete();
//            }
            throw new TransactionException(e);
        }
    }

    private void addNewRuleToDb(RulesMessage rulesMessage)
    {
        for (Rule rule : rulesMessage.getRules())
        {
            RuleEntity ruleEntity = new RuleEntity();
            ruleEntity.setAppId(rulesMessage.getAppId());
            ruleEntity.setTenantId(rulesMessage.getTenantId());

            ruleEntity.setRuleType(rule.getRuleType());
            ruleEntity.setMetricName(rule.getMetricName());
            String json;
            try
            {
                json = JsonUtilForAPM.toJson(rule);
            } catch (JsonProcessingException e)
            {
                logger.error("Unable to process JSON " + rule.getRuleName());
                return;
            }
            ruleEntity.setJson(json);
            ruleEntityResource.add(ruleEntity);
        }
    }

    private Map<RulesKeyObject, List<Rule>> queryRulesFromDbByRuleType(RulesMessage rulesMessage)
    {

        Map<RulesKeyObject, List<Rule>> ruleMap = new HashMap<>();
        Set<RulesKeyObject> keysSet = getQueryCondition(rulesMessage);
        for (RulesKeyObject key : keysSet)
        {
            List<RuleEntity> ruleEntities = ruleEntityResource.get(key.getTenantId(), key.getAppId(), key.getRuleType());
            List<Rule> rules = new ArrayList<>();
            if (ruleEntities.size() != 0)
            {  //如果不判断，那么当ruleEntities为空时，ruleMap会增加一条记录，导致后面空指针
                for (RuleEntity ruleEntity : ruleEntities)
                {
                    Rule ruleFromDb;
                    try
                    {
                        ruleFromDb = JsonUtilForAPM.fromJson(ruleEntity.getJson(), Rule.class);
                    } catch (IOException e)
                    {
                        logger.error("Unable to process JSON: " + ruleEntity.getJson());
                        continue;  // or return ?
                    }
                    rules.add(ruleFromDb);
                }
                ruleMap.put(key, rules);
            }
        }

        return ruleMap;
    }

    public void doDeploy(Map<RulesKeyObject, List<Rule>> rules)
    {

        for (Map.Entry<RulesKeyObject, List<Rule>> entry : rules.entrySet())
        {
            ReleaseId releaseId = rulesManager.createReleaseId(entry.getKey());
            deployKjar(releaseId, entry.getValue());
            String sessionName = getSessionName(entry.getKey());
            startRuleEngine(releaseId, sessionName);
        }
    }

    private void deployKjar(ReleaseId releaseId, List<Rule> rules)
    {

        InternalKieModule kieModule = rulesManager.createKieJar(rules, releaseId);
        File pomFile = rulesManager.createKPom(releaseId);
        ruleRepository.deployRule(releaseId, kieModule, pomFile);
    }

    private void startRuleEngine(ReleaseId releaseId, String sessionName)
    {
        engineManager.createEngine(sessionName, releaseId.getGroupId(), releaseId.getArtifactId(), releaseId.getVersion());
    }

    @Override
    public void updateRules(RulesMessage rulesMessage)
    {

        String tenantId = rulesMessage.getTenantId();
        String appId = rulesMessage.getAppId();
        Rule[] rules = rulesMessage.getRules();
        for (Rule rule : rules)
        {

            RuleEntity ruleEntity = new RuleEntity();
            ruleEntity.setTenantId(tenantId);
            ruleEntity.setAppId(appId);
            ruleEntity.setRuleType(rule.getRuleType());
            ruleEntity.setMetricName(rule.getMetricName());
            String json;
            try
            {
                json = JsonUtilForAPM.toJson(rule);
            } catch (JsonProcessingException e)
            {
                logger.error("Unable to process JSON " + rule.getRuleName());
                continue; //or retrun;??
            }
            ruleEntity.setJson(json);
            ruleEntityResource.update(ruleEntity);
        }

        Map<RulesKeyObject, List<Rule>> ruleMap = queryRulesFromDbByRuleType(rulesMessage);

        doUpdate(ruleMap);
    }

    private void doUpdate(Map<RulesKeyObject, List<Rule>> rules)
    {

        for (Map.Entry<RulesKeyObject, List<Rule>> entry : rules.entrySet())
        {
            ReleaseId releaseId = rulesManager.createReleaseId(entry.getKey());
            deployKjar(releaseId, entry.getValue());

            String sessionName = getSessionName(entry.getKey());

            reloadRuleEngine(sessionName);
        }
    }

    private void reloadRuleEngine(String sessionName)
    {
        if (engineManager.getEngine(sessionName) != null)
        {
            engineManager.getEngine(sessionName).reload();
        }
    }

    @Override
    public void deleteRules(String tenantId, String appId)
    {

        RulesMessage rulesMessage = getAllRules(tenantId, appId);
        Set<RulesKeyObject> rulesKeyObjects = getQueryCondition(rulesMessage);
        rulesKeyObjects.forEach(this::cleanArtifact);

        ruleEntityResource.delete(tenantId, appId);
    }

    @Override
    public void deleteRules(String tenantId, String appId, String ruleType)
    {

        RulesMessage rulesMessage = getRules(tenantId, appId, ruleType);
        Set<RulesKeyObject> rulesKeyObjects = getQueryCondition(rulesMessage);
        rulesKeyObjects.forEach(this::cleanArtifact);

        ruleEntityResource.delete(tenantId, appId, ruleType);
    }

    private Set<RulesKeyObject> getQueryCondition(RulesMessage rulesMessage)
    {

        Set<RulesKeyObject> result = new HashSet<>();
        String tenantId = rulesMessage.getTenantId();
        String appId = rulesMessage.getAppId();
        String ruleType;
        RulesKeyObject object;
        for (Rule rule : rulesMessage.getRules())
        {
            ruleType = rule.getRuleType();
            object = new RulesKeyObject(tenantId, appId, ruleType);
            if (!result.contains(object))
            {
                result.add(object);
            }
        }

        return result;
    }

    private void cleanArtifact(RulesKeyObject object)
    {

        ReleaseId deleteReleaseId = rulesManager.createReleaseId(object);
        destroyRuleEngine(object);
        ruleRepository.deployRule(deleteReleaseId, new byte[0], new byte[0]);
//      sessionGavResource.delete(getSessionName(object));

    }

    private void destroyRuleEngine(RulesKeyObject object)
    {
        String sessionName = getSessionName(object);
        RuleEngine engine = engineManager.getEngine(sessionName);
        if (engine != null)
        {
            engine.destroy();
            engineManager.removeEngine(engine);
            logger.info("remove the engine: " + engine.getName());
            StringBuilder remaining = new StringBuilder();
            engineManager.getEngineList().forEach(ruleEngine -> remaining.append(ruleEngine.getName() + " "));
            logger.info("Now the EngineManager has engines: " + remaining.toString());
        }
    }

    public String getSessionName(RulesKeyObject object)
    {
        return object.getTenantId() + "-" + object.getAppId() + "-" + object.getRuleType();
    }

    public void modifySenders(String url, String topic)
    {
        engineManager.registerChannels(url, topic);
    }
}
