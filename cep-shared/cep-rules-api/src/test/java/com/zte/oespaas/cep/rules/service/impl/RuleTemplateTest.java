package com.zte.oespaas.cep.rules.service.impl;

import com.zte.oespaas.cep.common.model.ModelRepository;
import com.zte.oespaas.cep.common.model.Rule;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RuleTemplateTest
{
    static RuleTemplate ruleTemplate;
    static Rule rule;

    @BeforeClass
    public static void init()
    {
        ruleTemplate = new RuleTemplate();
        rule = ModelRepository.getRule();
    }

    @Test
    public void testCreateRules() throws Exception
    {
        String ruleStr = "rule \"appapp-calls-warn-greater-102\"\n   no-loop\n   enabled true \n    when\n       $t1: ApmSourceEvent($objectName: objectname,[\"app=app1\"] contains $objectName,$objectType: objecttype == \"app\",  $timestamp:timestamp,$metricName:metricname == \"calls\")\n       $flag : Integer() from accumulate(\n            $t2: ApmSourceEvent(\n                objectname == $objectName,\n                objecttype == $objectType,\n                metricname == $metricName,\n                $metricValue : metricvalue,\n                this before[0m, 9m59s] $t1\n            ),\n            init(int result = -1;),\n            action(  if ($metricValue <=20.0 && result <=-1) {result =-1;}  else if ($metricValue >30.0){result =-1;}  else if ($metricValue >20.0 && $metricValue <= 30.0 && result !=-1){result =-1 ;} ),\n            result( new Integer( result ) )\n        )\n       \n    then\n       String info = \"rulename:app-calls-warn-greater-10,app\" + $metricName;\n       String detail = \"\";\n       if($flag == -1){\n         detail = info + \";ruledetail:calls< 20.0 in last 10min;\";\n       }\n       if($flag == -1){\n          detail = info + \";ruledetail: 20.0<calls< 30.0 in last 10min;\";\n       }\n       if($flag == -1){\n          detail = info + \";ruledetail: calls>30.0  in last 10min;\";\n     }\n         channels[\"apmDefaultChannel\"].send(new ApmResultSet($metricName,new ApmResultEvent($flag,$objectName,detail, $timestamp)));\nend\n";
        System.out.println(ruleTemplate.createRules(rule));
        assertThat(ruleTemplate.createRules(rule), is(ruleStr));
    }

    @Test
    public void testGenerateEndTimeMethod() throws Exception
    {
        int inputDataRanges = rule.getInputDataRanges();
        assertThat(ruleTemplate.generateEndTime(inputDataRanges), is("9m59s"));
    }
}