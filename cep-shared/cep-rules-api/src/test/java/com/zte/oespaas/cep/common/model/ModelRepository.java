package com.zte.oespaas.cep.common.model;

import com.zte.oespaas.cep.engine.io.kafka.KafkaSeverConfig;

import java.util.Properties;

public class ModelRepository
{
    public static Rule getRule()
    {
        Rule rule = new Rule();
        rule.setRuleType("app");
        rule.setRuleName("app-calls-warn-greater-10");
        rule.setEnableStatus(true);
        rule.setAffectObjects("\"app=app1\"");
        rule.setCheckTime("weekday");
        rule.setInputDataRanges(10);
        rule.setMetricName("calls");

        Condition[] conditions = getConditions();
        rule.setConditions(conditions);

        Action[] actions = getActions();
        rule.setActions(actions);
        return rule;
    }

    public static Condition[] getConditions()
    {
        Condition[] conditions = new Condition[2];
        Condition warnCondition = getWarnCondition();
        conditions[0] = warnCondition;
        Condition criticalCondition = getCriticalCondition();
        conditions[1] = criticalCondition;
        return conditions;
    }

    public static Condition getWarnCondition()
    {
        Condition condition = new Condition();
        condition.setConditionType("warning");
        condition.setCountValue(10.0);
        condition.setEvaluationValue(20.0);
        condition.setOperator(">");
        condition.setSummaryMethod("max");
        return condition;
    }

    public static Condition getCriticalCondition()
    {
        Condition condition = new Condition();
        condition.setConditionType("critical");
        condition.setCountValue(10.0);
        condition.setEvaluationValue(30.0);
        condition.setOperator(">");
        condition.setSummaryMethod("max");
        return condition;
    }

    public static Action[] getActions()
    {
        Action[] actions = new Action[1];
        Action action = getAction();
        actions[0] = action;
        return actions;
    }

    public static Action getAction()
    {
        Action action = new Action();
        action.setActionId("email=1");
        action.setActionPlugin("email");
        Properties properties = new Properties();
        properties.put("address", "address@zte.com.cn");
        action.setProperties(properties);
        return action;
    }

    public static RulesKeyObject getRulesKeyObject()
    {
        return new RulesKeyObject("apm", "app", "calls");
    }

    public static KafkaSeverConfig getKafkaSeverConfig()
    {
        KafkaSeverConfig kafkaSeverConfig = new KafkaSeverConfig();
        kafkaSeverConfig.setBootStrapServers("10.92.252.61:9092");
        kafkaSeverConfig.setTopic("policy_event");
        kafkaSeverConfig.setGroupId("group12");
        kafkaSeverConfig.setClientId("client1");
        kafkaSeverConfig.setAutoCommitInterval("1000");
        kafkaSeverConfig.setEnableAutoCommit("true");
        kafkaSeverConfig.setSessionTimeout("30000");
        kafkaSeverConfig.setAutoOffsetReset("latest");
        kafkaSeverConfig.setKeyDeserializerCalss("org.apache.kafka.common.serialization.StringDeserializer");
        kafkaSeverConfig.setValueDeserializerClass("org.apache.kafka.common.serialization.StringDeserializer");
        return kafkaSeverConfig;
    }
}
