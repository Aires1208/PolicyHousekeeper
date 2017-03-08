package com.zte.oespaas.cep.rules.service;

import com.zte.oespaas.cep.common.io.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by 10184056 on 2016/12/6.
 */
public abstract class Acceptor implements Runnable
{

    private final Logger logger = LoggerFactory.getLogger(Acceptor.class.getName());
    private boolean start = true;
    private Receiver receiver;

    public Acceptor(Receiver receiver) {
        this.receiver = receiver;
    }

    public void stop() {
        this.start = false;
    }

    @Override
    public void run() {
        while (start) {
            try{
                List<String> records = receiver.receive();
                for (String record : records) {
                    logger.info("receive msg: " + record);
                    handlerMapping(record);
                }
            }catch (Throwable e) {
                if (e instanceof Error) {
                    throw (Error) e;
                }
                logger.warn("Unexpected exception.", e);
            }
        }
    }

    public  abstract void handlerMapping(String record);
}
