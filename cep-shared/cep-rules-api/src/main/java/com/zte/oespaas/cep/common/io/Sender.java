package com.zte.oespaas.cep.common.io;

public interface Sender
{
    void send(String message);

    void close();
}
