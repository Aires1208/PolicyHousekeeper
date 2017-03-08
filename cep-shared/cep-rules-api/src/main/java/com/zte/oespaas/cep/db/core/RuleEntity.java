package com.zte.oespaas.cep.db.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class RuleEntity {

    @NotNull
    @JsonProperty
    private String tenantId;

    @NotNull
    @JsonProperty
    private String appId;

    @NotNull
    @JsonProperty
    private String ruleType;


    @NotNull
    @JsonProperty
    private String metricName;


    @NotNull
    @JsonProperty
    private String json;

    public String getTenantId() {
        return tenantId;
    }


    public RuleEntity setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }


    public String getAppId() {
        return appId;
    }


    public RuleEntity setAppId(String appId) {
        this.appId = appId;
        return this;
    }


    public String getRuleType() {
        return ruleType;
    }


    public RuleEntity setRuleType(String ruleType) {
        this.ruleType = ruleType;
        return this;
    }


    public String getMetricName() {
        return metricName;
    }


    public RuleEntity setMetricName(String metricName) {
        this.metricName = metricName;
        return this;
    }


    public String getJson() {
        return json;
    }


    public RuleEntity setJson(String json) {
        this.json = json;
        return this;
    }


}
