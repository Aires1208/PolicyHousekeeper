package com.zte.oespaas.cep.rules.service.impl;

import com.zte.oespaas.cep.common.model.Rule;
import com.zte.oespaas.cep.common.model.RulesKeyObject;
import com.zte.oespaas.cep.common.utils.JsonUtilForAPM;
import com.zte.oespaas.cep.db.core.RuleEntity;
import com.zte.oespaas.cep.db.resources.RuleEntityResource;
import com.zte.oespaas.cep.engine.api.EngineManager;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;


@Service
public class DispatcherCepService {

    private static Logger logger = LoggerFactory.getLogger(DispatcherCepService.class);

    /**
     * A flag set when the acceptor has been created and initialized
     */

    @Inject
    private EngineManager engineManager;

    @Inject
    private RuleEntityResource ruleEntityResource;

    @Inject
    private RulesServiceImpl rulesService;

    @Inject
    private EventsAcceptor eventsAcceptor;

    @PostConstruct
    private void startUpAcceptor() {
        try {
            ruleEntityResource.create();
            loadAllKsessions(); //TODO  流程控制
            eventsAcceptor.initReceiver();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to initialize.", e);
        }
    }

    private void loadAllKsessions() {

        List<RuleEntity> entities = ruleEntityResource.getAll();
        Set<RulesKeyObject> objects = classifyRule(entities);
        Map<RulesKeyObject, List<Rule>> kjarGroup = matchRules(entities, objects);

        rulesService.doDeploy(kjarGroup);

        for (RulesKeyObject object : objects) {
            String sessionName = rulesService.getSessionName(object);
            engineManager.createEngine(sessionName, "com.zte.oespaas.cep", "cep-" + sessionName + "-kjar", "1.0");
        }
    }

    private Set<RulesKeyObject> classifyRule(List<RuleEntity> entities) {
        Set<RulesKeyObject> objects = new HashSet<>();
        for (RuleEntity entity : entities) {
            RulesKeyObject object = new RulesKeyObject(entity.getTenantId(), entity.getAppId(), entity.getRuleType());
            if (!objects.contains(object)) {
                objects.add(object);
            }
        }
        return objects;
    }

    private Map<RulesKeyObject, List<Rule>> matchRules(List<RuleEntity> entities, Set<RulesKeyObject> objects) {
        Map<RulesKeyObject, List<Rule>> kjarGroup = new HashMap<>();
        for (RulesKeyObject object : objects) {
            List<Rule> rules = new ArrayList<>();
            if (entities.size() != 0) {
                entities.stream().filter(entity -> object.getTenantId().equalsIgnoreCase(entity.getTenantId()) &&
                        object.getAppId().equalsIgnoreCase(entity.getAppId()) &&
                        object.getRuleType().equalsIgnoreCase(entity.getRuleType())).forEach(entity -> {
                            Rule rul = null;
                            try {
                                rul = JsonUtilForAPM.fromJson(entity.getJson(), Rule.class);
                            } catch (IOException e) {
                                logger.error("Unable to process JSON! " + e.getMessage());
                                return;
                            }
                            rules.add(rul);
                        }
                );
                kjarGroup.put(object, rules);
            }
        }
        return kjarGroup;
    }
}