package com.zte.oespaas.cep.engine.io.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.zte.oespaas.cep.common.io.Receiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaReceiver implements Receiver
{
    private KafkaConsumer<String, String> consumer;
    private String topic;
    private KafkaSeverConfig config;

    public KafkaReceiver(KafkaSeverConfig config)
    {
        this.topic = config.getTopic();
        this.config = config;
    }

    public void obtainConsumer()
    {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootStrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, config.getAutoCommitInterval());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, config.getEnableAutoCommit());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, config.getSessionTimeout());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getKeyDeserializerCalss());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getValueDeserializerClass());

        consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    public List<String> receive()
    {
        List<String> kafkaRecords = new ArrayList<>();
        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records)
        {
            kafkaRecords.add(record.value());
        }
        return kafkaRecords;
    }

    public void close()
    {
        if (consumer != null)
        {
//          consumer.close();  //KafkaConsumer is not safe for multi-threaded access
            consumer = null;
            topic = null;
            config = null;
        }
    }
}
