package com.zte.oespaas.cep.db.dao;

import com.zte.oespaas.cep.db.core.SessionGav;
import com.zte.oespaas.cep.db.core.mapper.SessionGavMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;


@RegisterMapper(SessionGavMapper.class)
public interface SessionGavDao {

    @SqlUpdate("insert into SESSION_GAV (SESSIONID, GROUPID,ARTIFACTID,VERSION) values (:sessionId, :groupId,:artifactId,:version)")
    int insert(@BindBean SessionGav sessionGav);
    
    @SqlQuery("select * from SESSION_GAV where SESSIONID = :sessionId")
    SessionGav query(@Bind("sessionId") String sessionId);
    
    @SqlQuery("select * from SESSION_GAV")
    List<SessionGav> queryAll();

    @SqlUpdate("delete from SESSION_GAV where SESSIONID = :sessionId")
    void delete(@Bind("sessionId") String sessionId);
}
