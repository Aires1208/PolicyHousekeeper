package com.zte.oespaas.cep.engine.api;

import org.jvnet.hk2.annotations.Contract;

import java.util.List;

@Contract
public interface EngineManager
{
    RuleEngine getEngine(String name);

    List<RuleEngine> getEngineList();

    void addEngine(RuleEngine engine);

    void removeEngine(RuleEngine engine);
    
    void createEngine(String name, String groupId, String artifactId, String version);

    void registerChannels(String url, String topic);
}
