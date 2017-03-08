package com.zte.oespaas.cep.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaUrl
{
    @JsonProperty
    private String srcUrl;
    @JsonProperty
    private String srcTopic;
    @JsonProperty
    private String dstUrl;
    @JsonProperty
    private String dstTopic;

    @JsonProperty
    public String getSrcUrl()
    {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl)
    {
        this.srcUrl = srcUrl;
    }

    @JsonProperty
    public String getSrcTopic()
    {
        return srcTopic;
    }

    public void setSrcTopic(String srcTopic)
    {
        this.srcTopic = srcTopic;
    }

    @JsonProperty
    public String getDstUrl()
    {
        return dstUrl;
    }

    public void setDstUrl(String dstUrl)
    {
        this.dstUrl = dstUrl;
    }

    @JsonProperty
    public String getDstTopic()
    {
        return dstTopic;
    }

    public void setDstTopic(String dstTopic)
    {
        this.dstTopic = dstTopic;
    }
}
