package com.zte.oespaas.cep.common.model;

/**
 * Created by 10184056 on 2016/12/23.
 */
public class ApmResultSet {
    private ApmResultEvent apmResultEvent;
    private String metricName;

    public ApmResultSet(String metricName,ApmResultEvent apmResultEvent){
        this.metricName=metricName;
        this.apmResultEvent=apmResultEvent;
    }

    public ApmResultEvent getApmResultEvent() {
        return apmResultEvent;
    }

    public void setApmResultEvent(ApmResultEvent apmResultEvent) {
        this.apmResultEvent = apmResultEvent;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
}
