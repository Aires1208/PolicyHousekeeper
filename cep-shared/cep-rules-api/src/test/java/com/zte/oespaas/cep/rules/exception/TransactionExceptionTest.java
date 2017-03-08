package com.zte.oespaas.cep.rules.exception;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/16.
 */
public class TransactionExceptionTest {
    @Test
    public void 测试TransactionExcepiton的所有方法(){
        Exception exception=new Exception("t message");
        TransactionException tException=new TransactionException(exception);
        assertThat(tException.getMessage(),is("t message"));
    }
}