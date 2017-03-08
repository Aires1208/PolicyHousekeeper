package com.zte.oespaas.cep.db.resources;

import com.zte.oespaas.cep.db.core.SessionGav;
import com.zte.oespaas.cep.db.dao.SessionGavDao;
import com.zte.ums.zenap.db.jdbi.JdbiDaoFactory;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@Service
public class SessionGavResource {
    @Inject
    private JdbiDaoFactory jdbiDaoFactory;

    public void setJdbiDaoFactory(JdbiDaoFactory jdbiDaoFactory) {
        this.jdbiDaoFactory = jdbiDaoFactory;
    }

    public SessionGav insert(@Valid SessionGav sessionGav){
        jdbiDaoFactory.getJdbiDaoByOnDemand(SessionGavDao.class).insert(sessionGav);
        return sessionGav;
    }
    
    public SessionGav query(@Valid String sessionId){
        return jdbiDaoFactory.getJdbiDaoByOnDemand(SessionGavDao.class).query(sessionId);
    }
    
    public List<SessionGav> queryAll(){
        return jdbiDaoFactory.getJdbiDaoByOnDemand(SessionGavDao.class).queryAll();
    }

    public void delete(String sessionId) {
        jdbiDaoFactory.getJdbiDaoByOnDemand(SessionGavDao.class).delete(sessionId);
    }
}
