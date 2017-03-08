package com.zte.oespaas.cep.common.model;

import org.immutables.value.internal.$generator$.$Generator;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/23.
 */
public class RulesMessageTest {
    @Test
    public void 测试RulesMessage所有方法(){
        RulesMessage rulesMessage=new RulesMessage();
        rulesMessage.setTenantId("testTenantId");
        rulesMessage.setAppId("testAppId");
        Rule rule=new Rule();
        rule.setRuleName("testName");
        rulesMessage.setRules(new Rule[]{rule});

        assertThat(rulesMessage.getTenantId(),is("testTenantId"));
        assertThat(rulesMessage.getAppId(),is("testAppId"));
        assertThat(rulesMessage.getRules()[0].getRuleName(),is("testName"));
    }
}