package com.zte.oespaas.cep.common.model;

import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class ActionTest
{
    @Test
    public void 测试action的所有方法() throws Exception
    {
        Action action = new Action();
        action.setActionId("1");
        action.setActionPlugin("mail");
        action.setProperties(new Properties());
        assertThat(action.getActionId(), is("1"));
        assertThat(action.getActionPlugin(), is("mail"));
        assertThat(action.getProperties(), not(nullValue()));
    }
}