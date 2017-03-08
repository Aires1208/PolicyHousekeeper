package org.kie.api.runtime;

import org.drools.core.factmodel.Fact;
import org.kie.api.runtime.rule.FactHandle;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class KieSessionImp implements KieSession {
    public String resultString="";
    @Override
    public void unregisterChannel(String var1) {

    }

    @Override
    public void registerChannel(String var1, Channel var2) {
        resultString="registerChannel";
    }

    @Override
    public FactHandle insert(Object fact) {
        resultString="insert";
        return null;
    }

    @Override
    public int fireAllRules() {
        return 1;
    }

    @Override
    public void destroy() {
        resultString="destory";
    }
}
