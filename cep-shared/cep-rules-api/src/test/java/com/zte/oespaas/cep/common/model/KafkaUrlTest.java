package com.zte.oespaas.cep.common.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/16.
 */
public class KafkaUrlTest {
    @Test
    public void 测试KkfkaUrl的所有方法(){
        KafkaUrl kafkaUrl=new KafkaUrl();
        kafkaUrl.setSrcUrl("srcUrl");
        kafkaUrl.setDstTopic("dstTopic");
        kafkaUrl.setDstUrl("dstUrl");
        kafkaUrl.setSrcTopic("srcTopic");
        assertThat(kafkaUrl.getDstTopic(),is("dstTopic"));
        assertThat(kafkaUrl.getDstUrl(),is("dstUrl"));
        assertThat(kafkaUrl.getSrcTopic(),is("srcTopic"));
        assertThat(kafkaUrl.getSrcUrl(),is("srcUrl"));
    }
}