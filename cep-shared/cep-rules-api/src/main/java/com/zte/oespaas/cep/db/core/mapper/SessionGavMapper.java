package com.zte.oespaas.cep.db.core.mapper;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.zte.oespaas.cep.db.core.SessionGav;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionGavMapper implements ResultSetMapper<SessionGav> {
    @Override
    public SessionGav map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new SessionGav().setSessionId(resultSet.getString("SESSIONID"))
                               .setGroupId(resultSet.getString("GROUPID"))
                               .setVersion(resultSet.getString("VERSION"))
                               .setArtifactId(resultSet.getString("ARTIFACTID"));
    }
}
