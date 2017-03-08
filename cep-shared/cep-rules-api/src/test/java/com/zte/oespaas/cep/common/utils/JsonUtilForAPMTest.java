package com.zte.oespaas.cep.common.utils;

import com.zte.oespaas.cep.common.model.ApmSourceEvent;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class JsonUtilForAPMTest
{
    @Test
    public void 测试bean转换为json() throws Exception
    {
        ApmSourceEvent apmSourceEvent = new ApmSourceEvent();
        apmSourceEvent.setObjecttype("app");
        apmSourceEvent.setObjectname("appName=101");
        apmSourceEvent.setMetricname("calls");
        apmSourceEvent.setMetricvalue(80);
        apmSourceEvent.setTimestamp(14600012345L);

        String json = "{\"objectname\":\"appName=101\",\"objecttype\":\"app\",\"metricname\":\"calls\",\"metricvalue\":80.0,\"timestamp\":14600012345}";
        System.out.println(json);
        assertThat(JsonUtilForAPM.apmSourceObject2Json(apmSourceEvent), is(json));
    }

    @Test
    public void 测试json转换为单个bean() throws Exception
    {
        String json = "{\"objectname\":\"appName=101\",\"objecttype\":\"app\",\"metricname\":\"calls\",\"metricvalue\":80.0,\"timestamp\":14600012345}";
        List<ApmSourceEvent> apmSourceEvents = JsonUtilForAPM.json2ApmSourceEvents(json);
        assertThat(apmSourceEvents.size(), is(1));
        ApmSourceEvent apmSourceEvent = apmSourceEvents.get(0);
        assertThat(apmSourceEvent.getObjectname(), is("appName=101"));
    }

    @Test
    public void 测试json转换为多个bean() throws Exception
    {
        String json = "[{\"objectname\":\"appName=101\",\"objecttype\":\"app\",\"metricname\":\"calls\",\"metricvalue\":80.0,\"timestamp\":14600012345},{\"objectname\":\"appName=101\",\"objecttype\":\"app\",\"metricname\":\"calls\",\"metricvalue\":80.0,\"timestamp\":14600012345}]\n";
        List<ApmSourceEvent> apmSourceEvents = JsonUtilForAPM.json2ApmSourceEvents(json);
        assertThat(apmSourceEvents.size(), is(2));
        ApmSourceEvent apmSourceEvent = apmSourceEvents.get(1);
        assertThat(apmSourceEvent.getObjectname(), is("appName=101"));
    }

    @Test
    public void 测试空json() throws Exception
    {
        String json = "";
        List<ApmSourceEvent> apmSourceEvents = JsonUtilForAPM.json2ApmSourceEvents(json);
        assertThat(apmSourceEvents.size(), is(0));
    }

    @Test
    public void 行测试包括类的构造函数() throws Exception
    {
        JsonUtilForAPM jsonUtilForAPM = new JsonUtilForAPM();
        assertThat(jsonUtilForAPM,not(nullValue()));
    }
}