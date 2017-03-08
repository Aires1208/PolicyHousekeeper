package com.zte.oespaas.cep.db.core.mapper;

import com.zte.oespaas.cep.db.core.RuleEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by 10154680 on 2016/12/23.
 */
public class RuleEntityMapperTest {
    ResultSet resultSet;

    @Before
    public void 初始化() {
        resultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    public void 测试map方法() throws Exception {
        RuleEntityMapper ruleEntityMapper = new RuleEntityMapper();
        when(resultSet.getString("TENANTID")).thenReturn("testTenandId");
        when(resultSet.getString("APPID")).thenReturn("testAppId");
        when(resultSet.getString("RULETYPE")).thenReturn("testRuleType");
        when(resultSet.getString("METRICNAME")).thenReturn("testMetricName");
        when(resultSet.getString("JSON")).thenReturn("testJson");

        RuleEntity ruleEntity=ruleEntityMapper.map(1,resultSet,null );

        assertThat(ruleEntity.getJson(),is("testJson"));
        assertThat(ruleEntity.getMetricName(),is("testMetricName"));
        assertThat(ruleEntity.getRuleType(),is("testRuleType"));
        assertThat(ruleEntity.getTenantId(),is("testTenandId"));
        assertThat(ruleEntity.getAppId(),is("testAppId"));
    }
}