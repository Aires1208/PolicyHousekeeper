package org.kie.api.runtime;

import org.kie.api.runtime.rule.FactHandle;

/**
 * Created by 10154680 on 2016/12/27.
 */
public interface KieSession {
    void unregisterChannel(String var1);
    void registerChannel(String var1, Channel var2);
    FactHandle insert(Object var1);
    int fireAllRules();
    void destroy();
}
