package com.zte.oespaas.cep.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Properties;

public class Action
{
    @JsonProperty
    private String actionPlugin;
    @JsonProperty
    private String actionId;
    @JsonProperty
    private Properties properties;

    @JsonProperty
    public String getActionPlugin()
    {
        return actionPlugin;
    }

    public void setActionPlugin(String actionPlugin)
    {
        this.actionPlugin = actionPlugin;
    }

    @JsonProperty
    public String getActionId()
    {
        return actionId;
    }

    public void setActionId(String actionId)
    {
        this.actionId = actionId;
    }

    @JsonProperty
    public Properties getProperties()
    {
        return properties;
    }

    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }
}
