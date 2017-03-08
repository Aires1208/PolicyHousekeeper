package com.zte.oespaas.cep.rules.service.impl;

import com.zte.oespaas.cep.common.io.Receiver;
import com.zte.oespaas.cep.common.model.ApmSourceEvent;
import com.zte.oespaas.cep.common.utils.JsonUtilForAPM;
import com.zte.oespaas.cep.engine.api.EngineManager;
import com.zte.oespaas.cep.engine.api.RuleEngine;
import com.zte.oespaas.cep.engine.io.kafka.KafkaReceiver;
import com.zte.oespaas.cep.rules.api.CepRulesConfig;
import com.zte.oespaas.cep.rules.service.Acceptor;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Service
public class EventsAcceptor {

    private final Logger logger = LoggerFactory.getLogger(EventsAcceptor.class.getName());

    @Inject
    private EngineManager engineManager;

    @Inject
    private CepRulesConfig configuration;

    private final Executor executor = Executors.newCachedThreadPool();

    private Acceptor acceptor;

    private Receiver receiver;

    public void initReceiver() {
        /*Set<Action> actions = new HashSet<>();
        Set<String> actionIds = new HashSet<>();
        List<RuleEntity> entities = ruleEntityResource.getAll();
        entities.forEach(entity -> {
            Rule rule = new Gson().fromJson(entity.getJson(), Rule.class);
            Action[] actionArray = rule.getActions();
            Arrays.stream(actionArray).forEach(action ->
            {
                if (action.getActionPlugin().equalsIgnoreCase("kafka")) {
                    String actionId = action.getActionId();
                    if (!actionIds.contains(actionId)) {
                        actions.add(action);
                        actionIds.add(actionId);
                    }
                }
            });

            for (Action action : actions) {
                String srcTopic = action.getProperties().getKafkaUrl().getSrcTopic();
                KafkaSeverConfig kafkaSeverConfig = configuration.getUrlForKafkaSeverConfig();
                kafkaSeverConfig.setBootStrapServers(action.getProperties().getKafkaUrl().getSrcUrl());
                Receiver receiver = new KafkaReceiver(srcTopic, kafkaSeverConfig);
                ((KafkaReceiver) receiver).obtainConsumer();

                startAcceptor(receiver);
            }
        });*/

        startAcceptor();
    }

    public void modifyReceiver(String topic, String url) {
        stopAcceptor();

        setKafkaReceiveServer(url, topic);

        startAcceptor();
    }

    private void startAcceptor() {
        receiver = new KafkaReceiver(configuration.getKafkaReceiveServer());
        ((KafkaReceiver) receiver).obtainConsumer();
        // start the acceptor if not already started
        if (acceptor == null) {
            acceptor = new Acceptor(receiver) {
                @Override
                public void handlerMapping(String record) {
                    List<ApmSourceEvent> apmObjects = JsonUtilForAPM.json2ApmSourceEvents(record);
                    for (Object apmObject : apmObjects) {
                        ApmSourceEvent apmEvent = (ApmSourceEvent) apmObject;
                        String type = apmEvent.getObjecttype();
                        String engineName = "apm-apm-" + type;
                        RuleEngine engine = engineManager.getEngine(engineName);
                        if (engine != null) {
                            engine.insert(apmEvent);
                            engine.fire();
                        } else {
                            logger.warn("RuleEngine does not exist. " + type);
                        }
                    }
                }
            };

            executor.execute(acceptor);
        }
    }

    private void setKafkaReceiveServer(String srcUrl, String srcTopic) {

        if (!isEmpty(srcUrl)) {
            configuration.getKafkaReceiveServer().setBootStrapServers(srcUrl);
            return;
        }

        String url = System.getenv("KAFKA_RECEIVE_RUL");
        if (url != null && !"".equals(url)) {
            configuration.getKafkaReceiveServer().setBootStrapServers(url);
        }

        if (!isEmpty(srcTopic)) {
            configuration.getKafkaReceiveServer().setTopic(srcTopic);
        }

    }

    private boolean isEmpty(String srcUrl) {
        return srcUrl == null || srcUrl.isEmpty();
    }

    public void stopAcceptor() {

        if (acceptor != null) {
            acceptor.stop();
            acceptor = null;
        }

        if (receiver != null){
            receiver.close();
        }
    }
}
