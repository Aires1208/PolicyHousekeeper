package com.zte.oespaas.cep.engine.io.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaSeverConfig {

	private String bootStrapServers;

	private String topic;
	
	private String groupId = "group1";
	
	private String clientId = "client1";

	private String autoCommitInterval = "1000";

	private String enableAutoCommit = "true";

	private String sessionTimeout = "3000";
	
	private String autoOffsetReset = "latest";
	
	private String keyDeserializerCalss = "org.apache.kafka.common.serialization.StringDeserializer";
	
	private String valueDeserializerClass = "org.apache.kafka.common.serialization.StringDeserializer";

	public KafkaSeverConfig() {
		
	}
	
	@JsonProperty
	public String getBootStrapServers() {
		return bootStrapServers;
	}

	@JsonProperty
	public void setBootStrapServers(String bootStrapServers) {
		this.bootStrapServers = bootStrapServers;
	}

	@JsonProperty
	public String getTopic() {
		return topic;
	}

	@JsonProperty
	public void setTopic(String topic) {
		this.topic = topic;
	}

	@JsonProperty
	public String getGroupId() {
		return groupId;
	}

	@JsonProperty
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@JsonProperty
	public String getClientId() {
		return clientId;
	}

	@JsonProperty
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JsonProperty
	public String getAutoCommitInterval() {
		return autoCommitInterval;
	}

	@JsonProperty
	public void setAutoCommitInterval(String autoCommitInterval) {
		this.autoCommitInterval = autoCommitInterval;
	}

	@JsonProperty
	public String getEnableAutoCommit() {
		return enableAutoCommit;
	}

	@JsonProperty
	public void setEnableAutoCommit(String enableAutoCommit) {
		this.enableAutoCommit = enableAutoCommit;
	}

	@JsonProperty
	public String getSessionTimeout() {
		return sessionTimeout;
	}

	@JsonProperty
	public void setSessionTimeout(String sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	@JsonProperty
	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}

	@JsonProperty
	public void setAutoOffsetReset(String autoOffsetReset) {
		this.autoOffsetReset = autoOffsetReset;
	}

	@JsonProperty
	public String getKeyDeserializerCalss() {
		return keyDeserializerCalss;
	}

	@JsonProperty
	public void setKeyDeserializerCalss(String keyDeserializerCalss) {
		this.keyDeserializerCalss = keyDeserializerCalss;
	}

	@JsonProperty
	public String getValueDeserializerClass() {
		return valueDeserializerClass;
	}

	@JsonProperty
	public void setValueDeserializerClass(String valueDeserializerClass) {
		this.valueDeserializerClass = valueDeserializerClass;
	}
	
}
