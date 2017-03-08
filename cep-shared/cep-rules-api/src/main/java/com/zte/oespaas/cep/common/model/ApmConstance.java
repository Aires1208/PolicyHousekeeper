package com.zte.oespaas.cep.common.model;

public interface ApmConstance {
    String OBJECTTYPE_APP = "app";
    String OBJECTTYPE_SERVICE = "service";
    String OBJECTTYPE_INSTANCE = "instance";
    String OBJECTTYPE_NODE = "node";
    String OBJECTTYPE_TRANSATION = "transaction";
    String OBJECTTYPE_DB = "db";

    String METRICNAME_CALLS = "calls";
    String METRICNAME_ERRORS = "errors";
    String METRICNAME_RESPONSETIME = "responsetime";
    String METRICNAME_MAXPRESPONSETIME = "maxresponsetime";
    String METRICNAME_MINIRESPONSETIME = "minresponsetime";
}
