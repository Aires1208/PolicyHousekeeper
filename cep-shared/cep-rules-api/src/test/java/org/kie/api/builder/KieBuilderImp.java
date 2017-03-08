package org.kie.api.builder;

import org.drools.compiler.kie.builder.impl.InternalKieModule;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class KieBuilderImp implements KieBuilder {
    @Override
    public KieModule getKieModule() {
        return new InternalKieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return null;
            }
        };
    }
}
