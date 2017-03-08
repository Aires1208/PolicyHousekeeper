package org.kie.api.builder;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class KieScannerImp implements KieScanner {
    public String resultString="";
    @Override
    public void scanNow() {
        resultString="scanNow";
    }
}
