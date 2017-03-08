package com.zte.oespaas.cep.rules.service;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jvnet.hk2.annotations.Contract;
import org.kie.api.builder.ReleaseId;

import java.io.File;

@Contract
public interface RulesRepository {

    void deployRule(ReleaseId releaseId,InternalKieModule kieModule,File pomFile);

    void deployRule(ReleaseId releaseId,byte[] jarContents,byte[] pomContents);

}
