package com.zte.oespaas.cep.rules.exception;

/**
 * Created by 10184056 on 2016/12/6.
 */
public class TransactionException extends Exception {

    private Exception e;

    public TransactionException(Exception e) {
        this.e = e;
    }

    public String getMessage() {
        return e.getMessage();
    }
}
