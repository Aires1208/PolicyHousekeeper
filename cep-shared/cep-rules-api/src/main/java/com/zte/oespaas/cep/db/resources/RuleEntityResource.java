package com.zte.oespaas.cep.db.resources;

import com.zte.oespaas.cep.db.core.RuleEntity;
import com.zte.oespaas.cep.db.dao.RuleEntityDAO;
import com.zte.ums.zenap.db.jdbi.JdbiDaoFactory;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;


@Service
public class RuleEntityResource {

	@Inject
	private JdbiDaoFactory jdbiDaoFactory;

    public void create(){
        jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).create();
    }

    public List<RuleEntity> getAll(){
        return jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).getAll();
    }

    public List<RuleEntity> get(String tenantId,String appId){
        return jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).findById(tenantId,appId);
    }

    public List<RuleEntity> get(String tenantId,String appId, String ruleType){
        return jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).findByType(tenantId,appId,ruleType);
    }

    public RuleEntity add(@Valid RuleEntity rule){
        
    	jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).insert(rule);

        return rule;
    }

    public RuleEntity update(@Valid RuleEntity rule) {

        jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).update(rule);

        return rule;
    }

    public void delete(String tenantId,String appId) {
    	jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).deleteById(tenantId,appId);
    }

    public void delete(String tenantId,String appId, String ruleType) {
        jdbiDaoFactory.getJdbiDaoByOnDemand(RuleEntityDAO.class).deleteById(tenantId,appId, ruleType);
    }

    public void setJdbiDaoFactory(JdbiDaoFactory jdbiDaoFactory) {
        this.jdbiDaoFactory = jdbiDaoFactory;
    }
}
