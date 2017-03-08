package com.zte.oespaas.cep.rules.service.impl;

import com.zte.oespaas.cep.common.model.ModelRepository;
import com.zte.oespaas.cep.common.model.Rule;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.builder.ReleaseId;

import java.io.File;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RulesManagerImplTest {
    static RulesManagerImpl rulesManager;

    @BeforeClass
    public static void init() {
        rulesManager = new RulesManagerImpl();
    }

    @Test
    public void 测试createReleaseId方法() throws Exception {
        ReleaseId releaseId = rulesManager.createReleaseId(ModelRepository.getRulesKeyObject());
        assertThat("app", is("app"));
        assertThat(releaseId, not(nullValue()));
    }

    @Test
    public void 测试createKieJar方法() throws Exception {
        rulesManager.setTemplate(new RuleTemplate());
        ArrayList<Rule> objects = new ArrayList<>();
        objects.add(ModelRepository.getRule());
        InternalKieModule kieJar = rulesManager.createKieJar(objects, rulesManager.createReleaseId(ModelRepository.getRulesKeyObject()));
        assertThat(kieJar, not(nullValue()));
    }

    @Test
    public void 测试createPom方法() throws Exception {
        rulesManager.setTemplate(new RuleTemplate());
        File kPom = rulesManager.createKPom(rulesManager.createReleaseId(ModelRepository.getRulesKeyObject()));
        assertThat(kPom, not(nullValue()));
    }
}