package com.zte.oespaas.cep.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.oespaas.cep.common.model.ApmSourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtilForAPM {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtilForAPM.class.getName());
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<ApmSourceEvent> json2ApmSourceEvents(String jsonString) {
        List<ApmSourceEvent> results = new ArrayList<>();
        try {
            JsonNode node = mapper.readTree(jsonString);
            if (node.isArray()) {
                results = mapper.readValue(jsonString, new TypeReference<List<ApmSourceEvent>>() {
                });
            }
            if (node.isObject()) {
                ApmSourceEvent apmSourceEvent = fromJson(jsonString, ApmSourceEvent.class);
                results.add(apmSourceEvent);
            }
            return results;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public static String apmSourceObject2Json(Object apmObject) throws JsonProcessingException {
        return toJson(apmObject);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws IOException {
        return mapper.readValue(json, classOfT);
    }

//    public static void main(String[] args) {
//        String json = "{\"objectname\":\"appName=101\",\"objecttype\":\"app\",\"metricname\":\"calls\",\"metricvalue\":80.0,\"timestamp\":14600012345}";
//        List<ApmSourceEvent> apmSourceEvents = new JsonUtilForAPM().json2ApmSourceEvents(json);
//        for (ApmSourceEvent obj : apmSourceEvents) {
//            System.out.println(obj.getObjectname());
//        }
//    }
}
