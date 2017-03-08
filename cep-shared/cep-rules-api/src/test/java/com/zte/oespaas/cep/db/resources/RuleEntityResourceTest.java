package com.zte.oespaas.cep.db.resources;

import com.zte.oespaas.cep.db.dao.RuleEntityDAO;
import com.zte.ums.zenap.db.jdbi.JdbiDaoFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by 10154680 on 2016/12/26.
 */
public class RuleEntityResourceTest {
    private MockRuleEntityDAO mockRuleEntityDAO;
    private RuleEntityResource ruleEntityResource;

    @Before
    public void 初始化() {
        mockRuleEntityDAO = new MockRuleEntityDAO();
        ruleEntityResource = new RuleEntityResource();
        JdbiDaoFactory jdbiDaoFactory = Mockito.mock(JdbiDaoFactory.class);
        when(jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class)).thenReturn(mockRuleEntityDAO);
        ruleEntityResource.setJdbiDaoFactory(jdbiDaoFactory);
    }

    @Test
    public void 测试RuleEntityResource的create方法() throws Exception {
        ruleEntityResource.create();
        assertThat(mockRuleEntityDAO.resultString, is("testCreate"));
    }

    @Test
    public void 测试RuleEntityResource的getAll方法() throws Exception {
        ruleEntityResource.getAll();
        assertThat(mockRuleEntityDAO.resultString, is("testGetAll"));
    }

    @Test
    public void 测试RuleEntityResource的get方法() throws Exception{
        ruleEntityResource.get("tenanid","appid");
        assertThat(mockRuleEntityDAO.resultString,is("testFindById"));

        ruleEntityResource.get("tenanid","appid","ruleType");
        assertThat(mockRuleEntityDAO.resultString,is("testFindByType"));
    }

    @Test
    public void 测试RuleEntityResource的add方法() throws Exception {
        ruleEntityResource.add(null);
        assertThat(mockRuleEntityDAO.resultString,is("testInsert"));
    }

    @Test
    public void 测试RuleEntityResource的update方法() throws Exception {
        ruleEntityResource.update(null);
        assertThat(mockRuleEntityDAO.resultString,is("testUpdate"));
    }

    @Test
    public void 测试RuleEntityResource的delete方法() throws Exception {
        ruleEntityResource.delete("tenantId","appid");
        assertThat(mockRuleEntityDAO.resultString,is("testDeleteById"));

        ruleEntityResource.delete("tenantId","appid","ruletype");
        assertThat(mockRuleEntityDAO.resultString,is("testDeleteByIdandRuleType"));

    }
}