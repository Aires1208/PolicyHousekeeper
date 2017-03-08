package org.kie.api.builder;

/**
 * Created by 10154680 on 2016/12/27.
 */
public interface ReleaseId {
    String getGroupId();

    String getArtifactId();

    String getVersion();

    String toExternalForm();

    boolean isSnapshot();
}
