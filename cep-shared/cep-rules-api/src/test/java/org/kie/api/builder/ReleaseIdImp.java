package org.kie.api.builder;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class ReleaseIdImp implements ReleaseId {
    @Override
    public String getGroupId() {
        return "";
    }

    @Override
    public String getArtifactId() {
        return "";
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String toExternalForm() {
        return "";
    }

    @Override
    public boolean isSnapshot() {
        return false;
    }
}
