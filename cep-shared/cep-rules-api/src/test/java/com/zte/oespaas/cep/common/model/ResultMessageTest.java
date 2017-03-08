package com.zte.oespaas.cep.common.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/23.
 */
public class ResultMessageTest {
    @Test
    public void 测试ResultMessage的所有方法() {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMsg("testMessage");
        resultMessage.setResult("testResult");
        assertThat(resultMessage.getMsg(), is("testMessage"));
        assertThat(resultMessage.getResult(), is("testResult"));

        resultMessage = new ResultMessage("testResult1", "testMessage1");
        assertThat(resultMessage.getMsg(),is("testMessage1"));
        assertThat(resultMessage.getResult(),is("testResult1"));

    }
}