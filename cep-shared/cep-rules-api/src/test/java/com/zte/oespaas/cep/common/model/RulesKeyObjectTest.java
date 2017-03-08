package com.zte.oespaas.cep.common.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/16.
 */
public class RulesKeyObjectTest {
    @Test
    public void 测试所有get和set方法() {
        RulesKeyObject rulesKeyObject = new RulesKeyObject("testTenantId", "testAppId", "testRuleType");
        rulesKeyObject.setMetricName("testMetricName");
        assertThat(rulesKeyObject.getAppId(),is("testAppId"));
        assertThat(rulesKeyObject.getMetricName(),is("testMetricName"));
        assertThat(rulesKeyObject.getTenantId(),is("testTenantId"));
        assertThat(rulesKeyObject.getRuleType(),is("testRuleType"));

        rulesKeyObject.setAppId("testAppId1");
        assertThat(rulesKeyObject.getAppId(),is("testAppId1"));
        rulesKeyObject.setRuleType("testType1");
        assertThat(rulesKeyObject.getRuleType(),is("testType1"));
        rulesKeyObject.setTenantId("testTenantId1");
        assertThat(rulesKeyObject.getTenantId(),is("testTenantId1"));
    }
}