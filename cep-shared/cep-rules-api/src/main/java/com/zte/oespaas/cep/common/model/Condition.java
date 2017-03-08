package com.zte.oespaas.cep.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Condition {

	@JsonProperty
	private String conditionType;
	
	@JsonProperty
	private String summaryMethod;
	
	@JsonProperty
	private String operator;
	
	@JsonProperty
	private double countValue;
	
	@JsonProperty
	private double evaluationValue;
	
	@JsonProperty
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	
	@JsonProperty
	public String getSummaryMethod() {
		return summaryMethod;
	}
	public void setSummaryMethod(String summaryMethod) {
		this.summaryMethod = summaryMethod;
	}
	
	@JsonProperty
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@JsonProperty
	public double getCountValue() {
		return countValue;
	}
	public void setCountValue(double countValue) {
		this.countValue = countValue;
	}
	
	@JsonProperty
	public double getEvaluationValue() {
		return evaluationValue;
	}
	public void setEvaluationValue(double evaluationValue) {
		this.evaluationValue = evaluationValue;
	}
	
	
}
