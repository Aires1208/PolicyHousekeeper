package com.zte.oespaas.cep.common.model;

public class ApmSourceEvent
{
    protected String objectname;

    protected String objecttype;

    protected String metricname;

    protected double metricvalue;

    protected long timestamp;
    
    public ApmSourceEvent(){}
    
	public String getObjectname() {
		return objectname;
	}



	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}



	public String getObjecttype() {
		return objecttype;
	}



	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}



	public String getMetricname() {
		return metricname;
	}



	public void setMetricname(String metricname) {
		this.metricname = metricname;
	}



	public double getMetricvalue() {
		return metricvalue;
	}



	public void setMetricvalue(double metricvalue) {
		this.metricvalue = metricvalue;
	}



	public long getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}



	@Override
    public String toString()
    {
        return "ApmResultEvent{" +
                "objectName='" + objectname + '\'' +
                ", objectType='" + objecttype + '\'' +
                ", metricName='" + metricname + '\'' +
                ", metricValue=" + metricvalue +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ApmSourceEvent that = (ApmSourceEvent) o;

        if (metricvalue != that.metricvalue)
            return false;
        if (timestamp != that.timestamp)
            return false;
        if (!objectname.equals(that.objectname))
            return false;
        if (!objecttype.equals(that.objecttype))
            return false;
        return metricname.equals(that.metricname);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = objectname != null ? objectname.hashCode() : 0;
        result = 31 * result + (objecttype != null ? objecttype.hashCode() : 0);
        result = 31 * result + (metricname != null ? metricname.hashCode() : 0);
        temp = Double.doubleToLongBits(metricvalue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
