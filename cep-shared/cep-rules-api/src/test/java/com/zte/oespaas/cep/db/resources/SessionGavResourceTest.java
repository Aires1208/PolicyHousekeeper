package com.zte.oespaas.cep.db.resources;

import com.zte.oespaas.cep.db.core.SessionGav;
import com.zte.oespaas.cep.db.dao.RuleEntityDAO;
import com.zte.oespaas.cep.db.dao.SessionGavDao;
import com.zte.ums.zenap.db.jdbi.JdbiDaoFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class SessionGavResourceTest {
    SessionGavResource sessionGavResource;
    MockSessionGavDao mockSessionGavDao;
    @Before
    public void 初始化(){
        mockSessionGavDao=new MockSessionGavDao();
        sessionGavResource = new SessionGavResource();
        JdbiDaoFactory jdbiDaoFactory = Mockito.mock(JdbiDaoFactory.class);
        when(jdbiDaoFactory.getJdbiDaoByOnDemand(SessionGavDao.class)).thenReturn(mockSessionGavDao);
        sessionGavResource.setJdbiDaoFactory(jdbiDaoFactory);
    }

    @Test
    public void 测试SessionGavResource的insert方法() throws Exception {
        sessionGavResource.insert(new SessionGav());
        assertThat(mockSessionGavDao.resultString,is("testInsert"));
    }

    @Test
    public void query() throws Exception {
        sessionGavResource.query("sessionId");
        assertThat(mockSessionGavDao.resultString,is("testQuery"));
    }

    @Test
    public void queryAll() throws Exception {
        sessionGavResource.queryAll();
        assertThat(mockSessionGavDao.resultString,is("testQueryAll"));
    }

    @Test
    public void delete() throws Exception {
        sessionGavResource.delete("sessionId");
        assertThat(mockSessionGavDao.resultString,is("testDelete"));
    }

}