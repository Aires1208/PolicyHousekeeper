package com.zte.oespaas.cep.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultMessage
{
    private String result;
    private String msg;

    public ResultMessage()
    {
    }

    public ResultMessage(String result, String msg)
    {
        this.result = result;
        this.msg = msg;
    }

    @JsonProperty
    public String getResult()
    {
        return result;
    }

    @JsonProperty
    public void setResult(String result)
    {
        this.result = result;
    }

    @JsonProperty
    public String getMsg()
    {
        return msg;
    }

    @JsonProperty
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
