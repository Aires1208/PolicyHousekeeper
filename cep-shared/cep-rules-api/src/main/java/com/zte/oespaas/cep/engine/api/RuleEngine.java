package com.zte.oespaas.cep.engine.api;

import com.zte.oespaas.cep.common.io.Sender;

public interface RuleEngine
{
    String getName();

    void insert(Object fact);

    void start();

    int fire();

    void reload();

    void destroy();
    
    void setSender(Sender sender);

    void registerChannel(Sender sender);

    void unregisterChannel();
}
