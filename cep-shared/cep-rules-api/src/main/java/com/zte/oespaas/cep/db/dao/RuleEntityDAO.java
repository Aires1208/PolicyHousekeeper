package com.zte.oespaas.cep.db.dao;

import com.zte.oespaas.cep.db.core.RuleEntity;
import com.zte.oespaas.cep.db.core.mapper.RuleEntityMapper;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(RuleEntityMapper.class)
public interface RuleEntityDAO {

    @SqlUpdate("create table if not exists ruleentity(id bigint(20) auto_increment primary key,tenantid varchar(64), appid varchar(64), ruletype varchar(64), metricname varchar(64), json text, unique index rule_unique (tenantid,appid,ruletype,metricname) using btree)")
    void create();

    @SqlQuery("select * from RULEENTITY")
    List<RuleEntity> getAll();

    @SqlQuery("select * from RULEENTITY where TENANTID = :tenantId and APPID = :appId")
    List<RuleEntity> findById(@Bind("tenantId") String tenantId,@Bind("appId") String appId);

    @SqlQuery("select * from RULEENTITY where TENANTID = :tenantId and APPID = :appId and RULETYPE = :ruleType")
    List<RuleEntity> findByType(@Bind("tenantId") String tenantId,@Bind("appId") String appId, @Bind("ruleType") String ruleType);

    @SqlUpdate("delete from RULEENTITY where TENANTID = :tenantId and APPID = :appId")
    int deleteById(@Bind("tenantId") String tenantId,@Bind("appId") String appId);

    @SqlUpdate("delete from RULEENTITY where TENANTID = :tenantId and APPID = :appId and RULETYPE = :ruleType")
    int deleteById(@Bind("tenantId") String tenantId,@Bind("appId") String appId, @Bind("ruleType") String ruleType);

    @SqlUpdate("update RULEENTITY set JSON = :json where TENANTID = :tenantId and APPID = :appId and RULETYPE = :ruleType and METRICNAME = :metricName")
    int update(@BindBean RuleEntity rule);

    @SqlUpdate("insert into RULEENTITY (TENANTID, APPID,RULETYPE,METRICNAME,JSON) values (:tenantId, :appId,:ruleType,:metricName,:json)")
    int insert(@BindBean RuleEntity rule);
}
