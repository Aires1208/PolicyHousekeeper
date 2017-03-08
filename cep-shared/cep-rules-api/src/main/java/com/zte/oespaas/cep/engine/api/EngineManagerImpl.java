package com.zte.oespaas.cep.engine.api;

import com.zte.oespaas.cep.common.io.Sender;
import com.zte.oespaas.cep.engine.io.kafka.KafkaSender;
import com.zte.oespaas.cep.rules.api.CepRulesConfig;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EngineManagerImpl implements EngineManager {
    protected Map<String, RuleEngine> ruleEngines = new ConcurrentHashMap<>();

    @Inject
    private CepRulesConfig configuration;

    /**
     * The thread responsible of accepting apm event
     */
    private AtomicReference<Sender> acceptorRef = new AtomicReference<Sender>();

    public void setConfiguration(CepRulesConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public RuleEngine getEngine(String name) {
        return ruleEngines.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RuleEngine> getEngineList() {
        return new ArrayList(ruleEngines.values());
    }

    @Override
    public void addEngine(RuleEngine engine) {
        ruleEngines.put(engine.getName(), engine);
    }

    @Override
    public void removeEngine(RuleEngine engine) {
        ruleEngines.remove(engine.getName());
    }

    @Override
    public void createEngine(String name, String groupId, String artifactId, String version) {
        RuleEngine engine = new KieRuleEngine(name, groupId, artifactId, version);
        engine.setSender(getSender());
        engine.start();
        ruleEngines.put(name, engine);
    }

    @Override
    public void registerChannels(String url, String topic) {
        for (RuleEngine engine : ruleEngines.values()) {
            engine.unregisterChannel();
            engine.registerChannel(getSender(url, topic));
        }
    }

    private Sender getSender(String url, String topic) {
        setKafkaSenderServer(url, topic);
        Sender sender = new KafkaSender(configuration.getKafkaSendServer());
        ((KafkaSender) sender).obtainProducer();

        return sender;
    }

    private Sender getSender() {
        // start the acceptor if not already started
        Sender sender = acceptorRef.get();

        if (sender == null) {
            sender = new KafkaSender(configuration.getKafkaSendServer());
            ((KafkaSender) sender).obtainProducer();
            acceptorRef.compareAndSet(null, sender);
        }

        return sender;
    }

    private void setKafkaSenderServer(String srcUrl, String srcTopic) {

        if (!isEmpty(srcUrl)) {
            configuration.getKafkaSendServer().setBootStrapServers(srcUrl);
            return;
        }

        String url = System.getenv("KAFKA_SEND_RUL");
        if (url != null && !"".equals(url)) {
            configuration.getKafkaSendServer().setBootStrapServers(url);
        }

        if (!isEmpty(srcTopic)) {
            configuration.getKafkaSendServer().setTopic(srcTopic);
        }
    }

    private boolean isEmpty(String srcUrl) {
        return srcUrl == null || srcUrl.isEmpty();
    }
}
