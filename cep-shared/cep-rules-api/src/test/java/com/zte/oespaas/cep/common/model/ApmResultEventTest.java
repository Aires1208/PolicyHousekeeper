package com.zte.oespaas.cep.common.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class ApmResultEventTest
{
    @Test
    public void 测试ApmResultEvent所有方法() throws Exception
    {
        ApmResultEvent apmResultEvent = new ApmResultEvent();
        apmResultEvent.setEventType(1);
        apmResultEvent.setObjDN("cpu=1");
        apmResultEvent.setDetail("cpu-rate-90");
        apmResultEvent.setStartTime(1L);

        assertThat(apmResultEvent.getEventType(), is(1));
        assertThat(apmResultEvent.getObjDN(), is("cpu=1"));
        assertThat(apmResultEvent.getDetail(), is("cpu-rate-90"));
        assertThat(apmResultEvent.getStartTime(), is(1L));
        assertThat(apmResultEvent.toString(), not(nullValue()));
    }

    @Test
    public void 测试ApmResultEvent构造方法without时间() throws Exception
    {
        ApmResultEvent apmResultEvent = new ApmResultEvent(1,"cpu=1","cpu-rate-90");
        assertThat(apmResultEvent.toString(), not(nullValue()));
    }

    @Test
    public void 测试ApmResultEvent构造方法with时间() throws Exception
    {
        ApmResultEvent apmResultEvent = new ApmResultEvent(1,"cpu=1","cpu-rate-90",1L);
        assertThat(apmResultEvent.toString(), not(nullValue()));
    }
}