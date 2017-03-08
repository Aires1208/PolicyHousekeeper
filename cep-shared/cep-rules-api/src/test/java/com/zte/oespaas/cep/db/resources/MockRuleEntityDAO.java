package com.zte.oespaas.cep.db.resources;

import com.zte.oespaas.cep.db.core.RuleEntity;
import com.zte.oespaas.cep.db.dao.RuleEntityDAO;
import com.zte.ums.zenap.db.jdbi.JdbiDaoFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;

import java.util.List;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class MockRuleEntityDAO implements RuleEntityDAO {
    public String resultString;

    @Override
    public void create() {
        resultString="testCreate";
    }

    @Override
    public List<RuleEntity> getAll() {
        resultString="testGetAll";
        return null;
    }

    @Override
    public List<RuleEntity> findById(@Bind("tenantId") String tenantId, @Bind("appId") String appId) {
        resultString="testFindById";
        return null;
    }

    @Override
    public List<RuleEntity> findByType(@Bind("tenantId") String tenantId, @Bind("appId") String appId, @Bind("ruleType") String ruleType) {
        resultString="testFindByType";
        return null;
    }

    @Override
    public int deleteById(@Bind("tenantId") String tenantId, @Bind("appId") String appId) {
        resultString="testDeleteById";
        return 0;
    }

    @Override
    public int deleteById(@Bind("tenantId") String tenantId, @Bind("appId") String appId, @Bind("ruleType") String ruleType) {
        resultString="testDeleteByIdandRuleType";
        return 0;
    }

    @Override
    public int update(@BindBean RuleEntity rule) {
        resultString="testUpdate";
        return 0;
    }

    @Override
    public int insert(@BindBean RuleEntity rule) {
        resultString="testInsert";
        return 0;
    }
}
