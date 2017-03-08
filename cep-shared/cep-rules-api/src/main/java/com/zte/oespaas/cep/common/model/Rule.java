package com.zte.oespaas.cep.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {

	@JsonProperty
	private String ruleName;
	
	@JsonProperty
	private String ruleType;
	
	@JsonProperty
	private String checkTime ;
	
	@JsonProperty
	private boolean enableStatus;
	
	@JsonProperty
	private int inputDataRanges;
	
	@JsonProperty
	private String affectObjects;

	@JsonProperty
	private String metricName;
	
	@JsonProperty
	private Condition[] conditions;
	
	@JsonProperty
	private Action[] actions;
	
	
	@JsonProperty
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	@JsonProperty
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	@JsonProperty
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	
	@JsonProperty
	public boolean isEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(boolean enableStatus) {
		this.enableStatus = enableStatus;
	}
	
	@JsonProperty
	public int getInputDataRanges() {
		return inputDataRanges;
	}
	public void setInputDataRanges(int inputDataRanges) {
		this.inputDataRanges = inputDataRanges;
	}
	
	@JsonProperty
	public String getAffectObjects() {
		return affectObjects;
	}
	public void setAffectObjects(String affectObjects) {
		this.affectObjects = affectObjects;
	}

	@JsonProperty
	public String getMetricName() {
		return metricName;
	}
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	@JsonProperty
	public Condition[] getConditions() {
		return conditions;
	}
	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}
	
	@JsonProperty
	public Action[] getActions() {
		return actions;
	}
	public void setActions(Action[] actions) {
		this.actions = actions;
	}
	
	public String getProperty(String propertyName){

		if(actions != null && actions.length==1){

			if(actions[0].getProperties().getProperty(propertyName) != null){
				return actions[0].getProperties().getProperty(propertyName);
			}
		}

		return "-1";
	}
	
}
