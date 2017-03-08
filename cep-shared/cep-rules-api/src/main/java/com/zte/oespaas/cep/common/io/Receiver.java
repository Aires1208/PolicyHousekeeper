package com.zte.oespaas.cep.common.io;

import java.util.List;

public interface Receiver
{
    List<String> receive();

    void close();
}
