package com.zte.oespaas.cep.db.resources;

import com.zte.oespaas.cep.db.core.SessionGav;
import com.zte.oespaas.cep.db.dao.SessionGavDao;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;

import java.util.List;

/**
 * Created by 10154680 on 2016/12/27.
 */
public class MockSessionGavDao implements SessionGavDao {
    public String resultString="";
    @Override
    public int insert(@BindBean SessionGav sessionGav) {
        resultString="testInsert";
        return 0;
    }

    @Override
    public SessionGav query(@Bind("sessionId") String sessionId) {
        resultString="testQuery";
        return null;
    }

    @Override
    public List<SessionGav> queryAll() {
        resultString="testQueryAll";
        return null;
    }

    @Override
    public void delete(@Bind("sessionId") String sessionId) {
        resultString="testDelete";
    }
}
