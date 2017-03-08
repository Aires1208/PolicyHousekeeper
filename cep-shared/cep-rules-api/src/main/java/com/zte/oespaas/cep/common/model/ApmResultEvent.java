package com.zte.oespaas.cep.common.model;

import java.io.Serializable;

public class ApmResultEvent implements Serializable
{

    private static final long serialVersionUID = 1L;

    private int eventType;

    private long startTime;
//
//    private long endTime;

    private String objDN;

    private String detail;

    public ApmResultEvent()
    {
        super();
    }

    public ApmResultEvent(int eventType, String objDN, String detail)
    {
        this.eventType = eventType;
        this.objDN = objDN;
        this.detail = detail;
    }

    public ApmResultEvent(int eventType, String objDN, String detail, long startTime)
    {
        this.eventType = eventType;
        this.objDN = objDN;
        this.detail = detail;
        this.setStartTime(startTime);
    }

    public int getEventType()
    {
        return eventType;
    }

    public void setEventType(int eventType)
    {
        this.eventType = eventType;
    }

    public String getObjDN()
    {
        return objDN;
    }

    public void setObjDN(String objDN)
    {
        this.objDN = objDN;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    @Override
    public String toString()
    {
        return "ResultEvent [eventType=" + eventType + ", objDN=" + objDN + ", detail=" + detail + "]";
    }

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
}
