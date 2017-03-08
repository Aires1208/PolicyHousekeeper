package com.zte.oespaas.cep.rules.service.impl;

import com.zte.oespaas.cep.common.model.Rule;
import com.zte.oespaas.cep.common.model.RulesKeyObject;
import com.zte.oespaas.cep.rules.service.RulesManager;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.core.util.FileManager;
import org.jvnet.hk2.annotations.Service;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class RulesManagerImpl implements RulesManager
{
    private final Logger logger = LoggerFactory.getLogger(RulesManagerImpl.class.getName());
    private KieServices ks = KieServices.Factory.get();
    @Inject
    private RuleTemplate template;

    @Override
    public ReleaseId createReleaseId(RulesKeyObject object)
    {
        String artifactId = "cep-" + object.getTenantId() + "-" + object.getAppId() + "-" + object.getRuleType() + "-kjar";
        return ks.newReleaseId("com.zte.oespaas.cep", artifactId, "1.0");
    }

    @Override
    public InternalKieModule createKieJar(List<Rule> rules, ReleaseId releaseId)
    {
        KieFileSystem kfs = ks.newKieFileSystem();

        KieModuleModel kieModuleModel = ks.newKieModuleModel();

        kfs.writeKModuleXML(kieModuleModel.toXML());
        kfs.writePomXML(getPom(releaseId));

        createDrlByObjectType(kfs, rules);

        //Build the KieJar (compiling the DRL) and return the generated KieModule.
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        return (InternalKieModule) kieBuilder.getKieModule();
    }

    @Override
    public File createKPom(ReleaseId releaseId)
    {
        FileManager fileManager = new FileManager();
        File pomFile = fileManager.newFile("pom.xml");
        try
        {
            fileManager.write(pomFile, getPom(releaseId));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return pomFile;
    }

    private void createDrlByObjectType(KieFileSystem kfs, List<Rule> rules)
    {

        StringBuilder packageBuilder = createDrlHeader(); //TODO 需要根据类型确定drl的头部
        for (Rule rule : rules)
        {
            packageBuilder.append(template.createRules(rule));
        }
        logger.info("rules has created:\n" + packageBuilder.toString());
        String file = "com/zte/oespaas/cep/rules/" + rules.get(0).getRuleType() + "-rules.drl";
        kfs.write("src/main/resources/" + file, packageBuilder.toString());
    }

    private StringBuilder createDrlHeader()
    {
        StringBuilder packageBuilder = new StringBuilder();
        packageBuilder.append("package com.zte.oespaas.cep.rules;\n");
        packageBuilder.append("import com.zte.oespaas.cep.common.model.ApmSourceEvent;\n");
        packageBuilder.append("import com.zte.oespaas.cep.common.model.ApmResultEvent;\n");
        packageBuilder.append("import com.zte.oespaas.cep.common.model.ApmResultSet;\n");
        packageBuilder.append("declare ApmSourceEvent\n");
        packageBuilder.append("     @role(event)\n");
        packageBuilder.append("     @Expires(10m)\n");
        packageBuilder.append("     @Timestamp(timestamp)\n");
        packageBuilder.append("end\n");
        return packageBuilder;
    }

    private String getPom(ReleaseId releaseId, ReleaseId... dependencies)
    {
        String pom
                = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + " xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
                + " <modelVersion>4.0.0</modelVersion>\n"
                + "\n"
                + " <groupId>" + releaseId.getGroupId() + "</groupId>\n"
                + " <artifactId>" + releaseId.getArtifactId() + "</artifactId>\n"
                + " <version>" + releaseId.getVersion() + "</version>\n"
                + " <packaging>jar</packaging>\n"
                + " <url>http://maven.apache.org</url>\n"
                + " <properties>\n"
                + " <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n"
                + " </properties>\n"
                + "\n";
        if (dependencies != null && dependencies.length > 0)
        {
            pom += "<dependencies>\n";
            for (ReleaseId dep : dependencies)
            {
                pom += "<dependency>\n";
                pom += " <groupId>" + dep.getGroupId() + "</groupId>\n";
                pom += " <artifactId>" + dep.getArtifactId() + "</artifactId>\n";
                pom += " <version>" + dep.getVersion() + "</version>\n";
                pom += "</dependency>\n";
            }
            pom += "</dependencies>\n";
        }
        pom += "</project>";
        return pom;
    }

    public void setTemplate(RuleTemplate template)
    {
        this.template = template;
    }
}
