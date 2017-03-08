package com.zte.oespaas.cep.engine.api;

import com.zte.oespaas.cep.common.io.Sender;
import com.zte.oespaas.cep.engine.channel.ApmDefaultChannel;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieRuleEngine implements RuleEngine {

    private final Logger logger = LoggerFactory.getLogger(KieRuleEngine.class.getName());

    private String name;

    private String groupId;

    private String artifactId;

    private String version;

    private KieSession kieSession;

    private KieScanner kieScanner;

    private Sender sender;

    public KieRuleEngine(String name, String groupId, String artifactId, String version) {
        checkParameters(name, groupId, artifactId, version);

        this.name = name;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    private void checkParameters(String name, String groupId, String artifactId, String version) {
        checkParameter(name, "Engine name can not be empty!");

        checkParameter(groupId, "groupId can not be empty!");

        checkParameter(artifactId, "artifactId can not be empty!");

        checkParameter(version, "version can not be empty!");
    }

    private void checkParameter(String content, String s) {
        if (content == null || "".equals(content)) {
            throw new IllegalArgumentException(s);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void start() {
        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId(groupId, artifactId, version);
        KieContainer kieContainer = ks.newKieContainer(releaseId);

//        Results results = kieContainer.verify();
//
//        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
//            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
//            for (Message message : messages) {
//                logger.info("[%s] - %s[%s,%s]: %s", message.getLevel(), message.getPath(), message.getLine(), message.getColumn(), message.getText());
//            }
//            throw new IllegalStateException("Compilation errors were found. Check the logs.");
//        }

        kieScanner = ks.newKieScanner(kieContainer);
        kieSession = kieContainer.newKieSession();

        registerChannel(this.sender);
    }

    public void registerChannel(Sender sender) {
        Channel channel = new ApmDefaultChannel(sender);
        kieSession.registerChannel("apmDefaultChannel", channel);
    }

    public void unregisterChannel() {
        //TODO 当事件处理正在进行时，注销该channel是否会丢失一些结果事件
        this.closeSender();
        kieSession.unregisterChannel("apmDefaultChannel");
    }

    @Override
    public void insert(Object fact) {
        kieSession.insert(fact);
    }

    @Override
    public int fire() {
        return kieSession.fireAllRules();
    }

    @Override
    public void reload() {
        if (kieScanner != null) {
            kieScanner.scanNow();
        }
    }

    @Override
    public void destroy() {
        if (kieSession != null) {
            kieSession.destroy();
        }
    }

    @Override
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    private void closeSender() {
        this.sender.close();
    }

    public void setKieSession(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    public KieSession getKieSession() {
        return kieSession;
    }

    public KieScanner getKieScanner() {
        return kieScanner;
    }
}
