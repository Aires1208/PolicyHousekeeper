package com.zte.oespaas.cep.db.core.mapper;

import com.zte.oespaas.cep.db.core.SessionGav;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by 10154680 on 2016/12/26.
 */
public class SessionGavMapperTest {
    ResultSet resultSet;

    @Before
    public void 初始化() {
        resultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    public void 测试map方法() throws Exception {
        SessionGavMapper sessionGavMapper = new SessionGavMapper();
        when(resultSet.getString("SESSIONID")).thenReturn("testSession");
        when(resultSet.getString("GROUPID")).thenReturn("testGroupId");
        when(resultSet.getString("VERSION")).thenReturn("testVersion");
        when(resultSet.getString("ARTIFACTID")).thenReturn("testArtifactId");

        SessionGav sessionGav = sessionGavMapper.map(1, resultSet, null);

        assertThat(sessionGav.getSessionId(), is("testSession"));
        assertThat(sessionGav.getVersion(), is("testVersion"));
        assertThat(sessionGav.getArtifactId(), is("testArtifactId"));
        assertThat(sessionGav.getGroupId(), is("testGroupId"));

    }
}