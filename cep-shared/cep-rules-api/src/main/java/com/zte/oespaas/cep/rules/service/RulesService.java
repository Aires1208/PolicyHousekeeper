package com.zte.oespaas.cep.rules.service;


import com.zte.oespaas.cep.rules.exception.TransactionException;
import org.jvnet.hk2.annotations.Contract;

import com.zte.oespaas.cep.common.model.RulesMessage;


@Contract
public interface RulesService {

	RulesMessage getAllRules(String tenantId, String appId);

	RulesMessage getRules(String tenantId, String appId, String ruleType);

	void addRules(RulesMessage rulesMessage) throws TransactionException;

	void updateRules(RulesMessage rulesMessage);
	
	void deleteRules(String tenantId, String appId);

	void deleteRules(String tenantId, String appId, String ruleType);

	void modifySenders(String url, String topic);
}
