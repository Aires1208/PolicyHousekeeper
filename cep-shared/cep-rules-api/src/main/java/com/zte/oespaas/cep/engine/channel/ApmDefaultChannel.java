package com.zte.oespaas.cep.engine.channel;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.zte.oespaas.cep.common.io.Sender;
import com.zte.oespaas.cep.common.model.ApmResultEvent;
import com.zte.oespaas.cep.common.model.ApmResultSet;
import com.zte.oespaas.cep.common.utils.JsonUtilForAPM;
import org.kie.api.runtime.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApmDefaultChannel implements Channel {
    private Logger logger = LoggerFactory.getLogger(ApmDefaultChannel.class.getName());
    protected Map<String, Integer> sendMap = new ConcurrentHashMap<>();
    protected Sender sender;

    public ApmDefaultChannel(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void send(Object object) {
        ApmResultSet set = (ApmResultSet) object;
        ApmResultEvent event = set.getApmResultEvent();
        String metricName = set.getMetricName();
        String key = event.getObjDN() + ";" + metricName;

        if (!sendMap.containsKey(key) || event.getEventType() != sendMap.get(key)) {
            save(key, event.getEventType());
            sendToKafka(event);
        }
    }

    private void sendToKafka(ApmResultEvent event) {

        String result;
        try {
            result = JsonUtilForAPM.apmSourceObject2Json(event);
        } catch (JsonProcessingException e) {
            logger.error("Unable to process JSON" + e.getMessage());
            return;
        }
        sender.send(result);

    }

    private void save(String key, int level) {
        sendMap.put(key, level);
    }

    public Map<String, Integer> getSendMap() {
        return sendMap;
    }
}
