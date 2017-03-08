package com.zte.oespaas.cep.rules.service.impl;

import com.zte.oespaas.cep.common.model.Condition;
import com.zte.oespaas.cep.common.model.Rule;
import org.jvnet.hk2.annotations.Service;


@Service
public class RuleTemplate {

    private long counter = 1;

    public String createRules(Rule rule){
        StringBuilder ruleBuilder = new StringBuilder();

        String ruleName = rule.getRuleName();
        String ruleType = rule.getRuleType();
        String metricName = rule.getMetricName();
        double warningThreshold = 0;
        double criticalThreshold = 0;
        Condition[] conditions = rule.getConditions();
        for(Condition condition: conditions){
        	if(condition.getConditionType().equalsIgnoreCase("warning")){
        		warningThreshold = condition.getEvaluationValue();
        	}else if(condition.getConditionType().equalsIgnoreCase("critical")){
        		criticalThreshold = condition.getEvaluationValue();
        	}
        }

        int normalId = Integer.parseInt(rule.getProperty("normal"));
        int warningId = Integer.parseInt(rule.getProperty("warning"));
        int criticalId = Integer.parseInt(rule.getProperty("critical"));

        boolean enabled = rule.isEnableStatus();
        int inputDataRanges = rule.getInputDataRanges();
        String inputDataRangesEndTime=generateEndTime(inputDataRanges);
        String affectObjects = rule.getAffectObjects();


        ruleBuilder.append("rule \"").append(ruleType)
                .append(ruleName)
                .append(counter++ + "\"\n")
                .append("   no-loop\n")
                .append("   enabled ")
                .append(enabled)
                .append(" \n")
                .append("    when\n")
                .append("       $t1: ApmSourceEvent($objectName: objectname,[").append(affectObjects)
                .append("] contains $objectName,$objectType: objecttype == \"").append(ruleType)
                .append("\",  $timestamp:timestamp,$metricName:metricname == \"").append(metricName).append("\")\n")
                .append( "       $flag : Integer() from accumulate(\n")
                .append( "            $t2: ApmSourceEvent(\n")
                .append("                objectname == $objectName,\n")
                .append("                objecttype == $objectType,\n")
                .append("                metricname == $metricName,\n")
                .append("                $metricValue : metricvalue,\n")
                .append("                this before[0m, ").append(inputDataRangesEndTime).append("] $t1\n")
                .append("            ),\n")
                .append("            init(int result = " ).append(normalId).append( ";),\n")
                .append( "            action(  if ($metricValue <=").append(warningThreshold)
                .append(" && result <=").append(normalId).append(") {result =").append(normalId).append(";} ")
                .append(" else if ($metricValue >").append(criticalThreshold)
                .append("){result =").append(criticalId).append(";} ")
                .append(" else if ($metricValue >").append(warningThreshold)
                .append(" && $metricValue <= ").append(criticalThreshold)
                .append(" && result !=").append(criticalId).append("){result =").append(warningId).append(" ;} ),\n") 
                .append("            result( new Integer( result ) )\n")
                .append("        )\n")
                .append("       \n")
                .append("    then\n")
                .append("       String info = \"rulename:").append(ruleName).append(",").append(ruleType).append("\" + $metricName;\n")
                .append("       String detail = \"\";\n")
                .append("       if($flag == "+ normalId + "){\n")
                .append("         detail = info + \";ruledetail:calls< " + warningThreshold + " in last ").append(inputDataRanges).append("min;\";\n")
                .append("       }\n")
                .append("       if($flag == "+ warningId + "){\n")
                .append("          detail = info + \";ruledetail: " + warningThreshold + "<" +  metricName + "< "+ criticalThreshold + " in last ").append(inputDataRanges).append("min;\";\n")
                .append("       }\n")
                .append("       if($flag == "+ criticalId + "){\n")
                .append("          detail = info + \";ruledetail: " + metricName + ">" + criticalThreshold + "  in last ").append(inputDataRanges).append("min;\";\n     }")
                .append("\n")
                .append("         channels[\"apmDefaultChannel\"].send(new ApmResultSet($metricName,new ApmResultEvent($flag,$objectName,detail, $timestamp)));\n")
                .append("end\n");
        return ruleBuilder.toString();
    }
    public String generateEndTime(int inputDataRanges){
        return (inputDataRanges-1)+"m59s";
    }
}