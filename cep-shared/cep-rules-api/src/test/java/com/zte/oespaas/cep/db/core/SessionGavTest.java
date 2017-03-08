package com.zte.oespaas.cep.db.core;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by 10154680 on 2016/12/23.
 */
public class SessionGavTest {
@Test
    public void 测试SessionGav的所有方法(){
    SessionGav sessionGav=new SessionGav();
    sessionGav.setArtifactId("testArifactId");
    sessionGav.setGroupId("testGroupId");
    sessionGav.setSessionId("testSessionId");
    sessionGav.setVersion("testVersion");

    assertThat(sessionGav.getArtifactId(),is("testArifactId"));
    assertThat(sessionGav.getGroupId(),is("testGroupId"));
    assertThat(sessionGav.getSessionId(),is("testSessionId"));
    assertThat(sessionGav.getVersion(),is("testVersion"));
}
}