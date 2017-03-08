package com.zte.oespaas.cep.db.core;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/23.
 */
public class RuleEntityTest {
    @Test
    public void 测试RuleEntity的所有方法() {
        RuleEntity ruleEntity=new RuleEntity();
        ruleEntity.setAppId("testAppId");
        ruleEntity.setTenantId("testTenantId");
        ruleEntity.setRuleType("testType");
        ruleEntity.setJson("testJson");
        ruleEntity.setMetricName("testMetricName");

        assertThat(ruleEntity.getAppId(),is("testAppId"));
        assertThat(ruleEntity.getTenantId(),is("testTenantId"));
        assertThat(ruleEntity.getRuleType(),is("testType"));
        assertThat(ruleEntity.getMetricName(),is("testMetricName"));
        assertThat(ruleEntity.getJson(),is("testJson"));
    }
}