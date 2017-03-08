package com.zte.oespaas.cep.rules.service;


import java.io.File;
import java.util.List;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jvnet.hk2.annotations.Contract;
import org.kie.api.builder.ReleaseId;

import com.zte.oespaas.cep.common.model.Rule;
import com.zte.oespaas.cep.common.model.RulesKeyObject;

@Contract
public interface RulesManager {
	
	ReleaseId createReleaseId(RulesKeyObject object);
	
	InternalKieModule createKieJar(List<Rule> rules,ReleaseId releaseId);

	File createKPom(ReleaseId releaseId);

}
