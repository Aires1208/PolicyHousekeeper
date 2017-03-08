package org.kie.api;

import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by 10154680 on 2016/12/27.
 */
public interface KieServices {
//    public static KieServices Factory;
    public static class Factory {
        private static KieServices INSTANCE=new KieServicesImp();

        public Factory() {
        }

        public static KieServices get() {
            return INSTANCE;
        }

    }

    ReleaseId newReleaseId(String var1, String var2, String var3);

    KieScanner newKieScanner(KieContainer var1);

    KieSession newKieSession();

    KieContainer newKieContainer(ReleaseId var1);

    KieFileSystem newKieFileSystem();

    KieModuleModel newKieModuleModel();

    KieBuilder newKieBuilder(KieFileSystem var1);
}
