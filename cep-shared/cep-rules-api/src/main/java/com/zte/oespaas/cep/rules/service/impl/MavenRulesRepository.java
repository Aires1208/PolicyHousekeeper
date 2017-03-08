package com.zte.oespaas.cep.rules.service.impl;

import com.zte.oespaas.cep.rules.service.RulesRepository;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.jvnet.hk2.annotations.Service;
import org.kie.api.builder.ReleaseId;
import org.kie.scanner.MavenRepository;

import java.io.File;

@Service
public class MavenRulesRepository implements RulesRepository {

    /**
     * This class represents a Maven Repository. This class can be used to
     * manually deploy artifacts into a repository.
     */
    private MavenRepository repository;

    public MavenRulesRepository() {
        this.repository = MavenRepository.getMavenRepository();
    }


    @Override
    public void deployRule(ReleaseId releaseId, InternalKieModule kieModule, File pomFile) {
        repository.deployArtifact(releaseId, kieModule, pomFile);
    }

    @Override
    public void deployRule(ReleaseId releaseId, byte[] jarContents, byte[] pomContents) {
        repository.deployArtifact(releaseId, jarContents, pomContents);
    }
}
