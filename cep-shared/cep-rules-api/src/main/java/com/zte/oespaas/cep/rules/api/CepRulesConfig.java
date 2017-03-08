package com.zte.oespaas.cep.rules.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jvnet.hk2.annotations.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zte.oespaas.cep.engine.io.kafka.KafkaSeverConfig;
import com.zte.ums.zenap.db.wrapper.ExtDataSourceFactory;
import com.zte.ums.zenap.msb.client.MsbClientConfig;
import com.zte.ums.zenap.swagger.SwaggerConfig;

import io.dropwizard.Configuration;

@Service
public class CepRulesConfig extends Configuration {
	
	@JsonProperty
	private SwaggerConfig swaggeconfig;

    
    @JsonProperty
    @NotNull
    @Valid
    private MsbClientConfig msbClientConfig;
    

	@Valid
    @NotNull
    @JsonProperty("database")
    private ExtDataSourceFactory database = new ExtDataSourceFactory();
	

	@NotNull
	private KafkaSeverConfig kafkaReceiveServer  = new KafkaSeverConfig();
    
    @NotNull
    private KafkaSeverConfig kafkaSendServer  = new KafkaSeverConfig();
    
	
    public MsbClientConfig getMsbClientConfig() {
		return msbClientConfig;
	}


	public void setMsbClientConfig(MsbClientConfig msbClientConfig) {
		this.msbClientConfig = msbClientConfig;
	}


	public ExtDataSourceFactory getDatabase() {
		return database;
	}


	public void setDatabase(ExtDataSourceFactory database) {
		this.database = database;
	}

    public KafkaSeverConfig getKafkaReceiveServer() {
		return kafkaReceiveServer;
	}


	public void setKafkaReceiveServer(KafkaSeverConfig kafkaReceiveServer) {
		this.kafkaReceiveServer = kafkaReceiveServer;
	}


	public KafkaSeverConfig getKafkaSendServer() {
		return kafkaSendServer;
	}


	public void setKafkaSendServer(KafkaSeverConfig kafkaSendServer) {
		this.kafkaSendServer = kafkaSendServer;
	}
    
    
}


