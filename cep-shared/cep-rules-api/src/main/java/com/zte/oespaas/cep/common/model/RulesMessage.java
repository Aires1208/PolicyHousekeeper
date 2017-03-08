package com.zte.oespaas.cep.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RulesMessage {

    @JsonProperty
    private String tenantId;

    @JsonProperty
    private String appId;

    @JsonProperty
    private Rule[] rules;

    @JsonProperty
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @JsonProperty
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @JsonProperty
    public Rule[] getRules() {
        return rules;
    }

    public void setRules(Rule[] rules) {
        this.rules = rules;
    }
}
