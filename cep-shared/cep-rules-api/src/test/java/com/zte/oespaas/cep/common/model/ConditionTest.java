package com.zte.oespaas.cep.common.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/23.
 */
public class ConditionTest {
    @Test
    public void 测试Condition的所有方法() {
        Condition condition = new Condition();
        condition.setConditionType("testType");
        condition.setCountValue(1);
        condition.setEvaluationValue(2);
        condition.setOperator(">");
        condition.setSummaryMethod("testMethod");

        assertThat(condition.getConditionType(), is("testType"));
        assertThat(condition.getCountValue(), is(1.0));
        assertThat(condition.getEvaluationValue(), is(2.0));
        assertThat(condition.getOperator(), is(">"));
        assertThat(condition.getSummaryMethod(), is("testMethod"));
    }
}