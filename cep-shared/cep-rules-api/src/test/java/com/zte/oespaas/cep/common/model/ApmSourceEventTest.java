package com.zte.oespaas.cep.common.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class ApmSourceEventTest {
    ApmSourceEvent apmSourceEvent;

    @Before
    public void 初始化() {
        apmSourceEvent = new ApmSourceEvent();
        apmSourceEvent.setObjecttype("app");
        apmSourceEvent.setObjectname("app1");
        apmSourceEvent.setMetricname("calls");
        apmSourceEvent.setMetricvalue(20);
        apmSourceEvent.setTimestamp(1432710115000L);
    }

    @Test
    public void 测试ApmSourceEvent所有方法() throws Exception {
        assertThat(apmSourceEvent.getObjecttype(), is("app"));
        assertThat(apmSourceEvent.getObjectname(), is("app1"));
        assertThat(apmSourceEvent.getMetricname(), is("calls"));
        assertThat(apmSourceEvent.getMetricvalue(), is(20.0));
        assertThat(apmSourceEvent.getTimestamp(), not(nullValue()));
        assertThat(apmSourceEvent.toString(), not(nullValue()));
    }

    @Test
    public void 测试equals和hashcode方法() {
        ApmSourceEvent anotherApmSourceEvent = new ApmSourceEvent();
        anotherApmSourceEvent.setObjecttype("app");
        anotherApmSourceEvent.setObjectname("app1");
        anotherApmSourceEvent.setMetricname("calls");
        anotherApmSourceEvent.setMetricvalue(20);
        anotherApmSourceEvent.setTimestamp(1432710115000L);

        assertThat(apmSourceEvent.hashCode() == anotherApmSourceEvent.hashCode(), is(true));
        assertThat(apmSourceEvent.equals(anotherApmSourceEvent), is(true));
        assertThat(apmSourceEvent.equals(apmSourceEvent), is(true));
        assertThat(apmSourceEvent.equals(null), is(false));
        assertThat(apmSourceEvent.equals(new Object()), is(false));

        anotherApmSourceEvent.setObjecttype("transaction");
        assertThat(apmSourceEvent.equals(anotherApmSourceEvent), is(false));

        anotherApmSourceEvent.setObjecttype("app");
        anotherApmSourceEvent.setObjectname("app2");
        assertThat(apmSourceEvent.equals(anotherApmSourceEvent), is(false));

        anotherApmSourceEvent.setObjectname("app1");
        anotherApmSourceEvent.setMetricvalue(21);
        assertThat(apmSourceEvent.equals(anotherApmSourceEvent), is(false));

        anotherApmSourceEvent.setMetricvalue(20);
        anotherApmSourceEvent.setTimestamp(1432710115001L);
        assertThat(apmSourceEvent.equals(anotherApmSourceEvent), is(false));
    }

}