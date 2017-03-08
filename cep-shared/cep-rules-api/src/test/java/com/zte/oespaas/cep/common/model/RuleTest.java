package com.zte.oespaas.cep.common.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RuleTest
{
    @Test
    public void 测试Rule类() throws Exception
    {
        Rule rule = ModelRepository.getRule();

        assertThat(rule.getRuleType(),is("app"));
        assertThat(rule.getRuleName(),is("app-calls-warn-greater-10"));
        assertThat(rule.isEnableStatus(),is(true));
        assertThat(rule.getAffectObjects(),is("\"app=app1\""));
        assertThat(rule.getCheckTime(),is("weekday"));
        assertThat(rule.getInputDataRanges(),is(10));
        assertThat(rule.getMetricName(),is("calls"));
        assertThat(rule.getConditions(),not(nullValue()));
        assertThat(rule.getActions(),not(nullValue()));
        assertThat(rule.getProperty("address"),is("address@zte.com.cn"));
        assertThat(rule.getProperty("not_address"),is("-1"));
    }


}