package com.zte.oespaas.cep.db.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.zte.oespaas.cep.db.core.RuleEntity;

public class RuleEntityMapper implements ResultSetMapper<RuleEntity>
{
    public RuleEntity map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
    {
        return new RuleEntity()
        		.setTenantId(resultSet.getString("TENANTID"))
        		.setAppId(resultSet.getString("APPID"))
        		.setRuleType(resultSet.getString("RULETYPE"))
        		.setMetricName(resultSet.getString("METRICNAME"))
        		.setJson(resultSet.getString("JSON"));
    }
}
