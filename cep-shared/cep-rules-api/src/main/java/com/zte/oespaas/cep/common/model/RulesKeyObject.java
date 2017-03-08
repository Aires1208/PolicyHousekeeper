package com.zte.oespaas.cep.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RulesKeyObject
{
    @JsonProperty
    private String tenantId;
    @JsonProperty
    private String appId;
    @JsonProperty
    private String ruleType;
    @JsonProperty
    private String metricName;

    public RulesKeyObject(String tenantId, String appId, String ruleType)
    {
        this.tenantId = tenantId;
        this.appId = appId;
        this.ruleType = ruleType;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getRuleType()
    {
        return ruleType;
    }

    public void setRuleType(String ruleType)
    {
        this.ruleType = ruleType;
    }

    public String getMetricName()
    {
        return metricName;
    }

    public void setMetricName(String metricName)
    {
        this.metricName = metricName;
    }
}
