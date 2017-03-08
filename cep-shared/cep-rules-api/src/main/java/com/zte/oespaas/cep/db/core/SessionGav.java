package com.zte.oespaas.cep.db.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class SessionGav {

    @NotNull
    @JsonProperty
    private String sessionId;

    @NotNull
    @JsonProperty
    private String groupId;

    @NotNull
    @JsonProperty
    private String artifactId;

    @NotNull
    @JsonProperty
    private String version;

    public String getSessionId() {
        return sessionId;
    }

    public SessionGav setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public SessionGav setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public SessionGav setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SessionGav setVersion(String version) {
        this.version = version;
        return this;
    }
}
