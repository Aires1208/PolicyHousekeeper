package com.zte.oespaas.cep.engine.io.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zte.oespaas.cep.common.io.Sender;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaSender implements Sender
{
    private Logger logger = LoggerFactory.getLogger(KafkaSender.class.getName());
    private KafkaProducer<Long, String> producer;
    private final String topic;
    private long messageNo = 1;
    private KafkaSeverConfig config;

    public KafkaSender(KafkaSeverConfig config)
    {
        this.topic = config.getTopic();
        this.config = config;
    }

    public void obtainProducer()
    {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootStrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, config.getClientId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.getKeyDeserializerCalss());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.getValueDeserializerClass());
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void send(String message)
    {
        try
        {
            producer.send(new ProducerRecord<>(topic, messageNo, message)).get();
            logger.info("Send APM Event to kafka: (" + messageNo + ", " + message + ")");
        } catch (InterruptedException | ExecutionException e)
        {
            logger.warn(e.getMessage(), e);
        }
        ++messageNo;
    }

    @Override
    public void close()
    {
        if (producer != null)
        {
            producer.flush();
            producer.close();
        }
    }
}
