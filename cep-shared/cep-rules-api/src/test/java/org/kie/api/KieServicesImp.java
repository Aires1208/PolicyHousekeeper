package org.kie.api;

import org.kie.api.builder.*;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieModuleModelImp;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionImp;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class KieServicesImp implements KieServices {
    @Override
    public ReleaseId newReleaseId(String var1, String var2, String var3) {
        return new ReleaseIdImp();
    }

    @Override
    public KieScanner newKieScanner(KieContainer var1) {
        return new KieScannerImp();
    }

    @Override
    public KieSession newKieSession() {
        return null;
    }

    @Override
    public KieContainer newKieContainer(ReleaseId var1) {
        return new KieContainer() {

            @Override
            public KieSession newKieSession() {
                return new KieSessionImp();
            }
        };
    }

    @Override
    public KieFileSystem newKieFileSystem() {
        return new KieFileSystemImp();
    }

    @Override
    public KieModuleModel newKieModuleModel() {
        return new KieModuleModelImp();
    }

    @Override
    public KieBuilder newKieBuilder(KieFileSystem var1) {
        return new KieBuilderImp();
    }
}
