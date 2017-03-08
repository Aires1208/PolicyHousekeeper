package org.kie.api.builder;

/**
 * Created by 10154680 on 2016/12/27.
 */
public interface KieFileSystem {
    KieFileSystem generateAndWritePomXML(ReleaseId var1);

    KieFileSystem writePomXML(byte[] var1);

    KieFileSystem writePomXML(String var1);

    KieFileSystem writeKModuleXML(byte[] var1);

    KieFileSystem writeKModuleXML(String var1);

    KieFileSystem write(String var1, byte[] var2);

    KieFileSystem write(String var1, String var2);

    void delete(String... var1);

    byte[] read(String var1);
}
