package org.bidtime.basicdata.duty.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.basicdata.duty.bean.Duty;
import org.bidtime.basicdata.duty.dao.DutyDAO;
import org.bidtime.dbutils.jdbc.dao.PKCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DutyService {

	//private static final Logger logger = Logger.getLogger(DutyService.class);

	@Autowired
	private DutyDAO dao;

	public DutyDAO getDao() {
		return dao;
	}

	public void setDao(DutyDAO dao) {
		this.dao = dao;
	}
	
	public Long insertForPK(Duty u) throws SQLException {
		Long id = dao.insertForPK(u, 
			new PKCallback<Duty, Long>(){  
        @Override  
        public Duty getIt(Long id) throws SQLException {
        	Duty duty = new Duty();
        	duty.setId(id);
        	duty.setCode("D-" + id);
        	return duty;
        }
		});
		return id;
	}
	
	public int insert(Duty u) throws SQLException {
		return dao.insert(u);
	}
	
	public int insertIgnore(Duty u) throws SQLException {
		return dao.insertIgnore(u);
	}
	
	public int update(Duty u) throws SQLException {
		return dao.update(u);
	}
	
	public int delete(Duty u) throws SQLException {
		return dao.delete(u);
	}
	
	public int delete(Duty u, String[] heads) throws SQLException {
		return dao.delete(u, heads);
	}
	
	public int delete(Map<String, Object> m) throws SQLException {
		return dao.delete(m);
	}
	
	public int delete(Map<String, Object> m, String[] heads) throws SQLException {
		return dao.delete(m, heads);
	}
	
	public int delete(Object[] ids) throws SQLException {
		return dao.delete(ids);
	}
	
	@SuppressWarnings("rawtypes")
	public int delete(List ids) throws SQLException {
		return dao.delete(ids);
	}
	
	@SuppressWarnings("rawtypes")
	public int insert(List list) throws SQLException {
		return dao.insert(list);
	}
	
	@SuppressWarnings("rawtypes")
	public int insertIgnore(List list) throws SQLException {
		return dao.insertIgnore(list);
	}
	
	@SuppressWarnings("rawtypes")
	public int update(List list) throws SQLException {
		return dao.update(list);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> T list(ResultSetHandler<T> h) throws SQLException {
		return dao.list(h);
	}
	
	public <T> T list(ResultSetHandler<T> h, Integer nPageIdx, Integer nPageSize) throws SQLException {
		return dao.list(h);
	}

	public <T> T info(ResultSetHandler<T> h, Object o) throws SQLException {
		return dao.info(h, o);
	}
	
//	public void doIt() throws Exception {
//		ArrayListLen<Duty> listBeans = getGsonOfAll1(new GsonListBeanHandler<Duty>(Duty.class));
//		logger.info("ArrayListLen<Duty>:" + listBeans.toString());
//	}
	
}
