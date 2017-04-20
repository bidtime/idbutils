package org.bidtime.basicdata.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.basicdata.user.bean.User;
import org.bidtime.basicdata.user.dao.UserDAO;
import org.bidtime.dbutils.jdbc.dao.PKCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	//private static final Logger logger = Logger.getLogger(DutyService.class);

	@Autowired
	private UserDAO dao;

	public UserDAO getDao() {
		return dao;
	}
	
//	public void setDao(UserDAO dao) {
//		this.dao = dao;
//	}
	
	public Long insertForPK(User u) throws SQLException {
		Long id = dao.insertForPK(u, 
			new PKCallback<User, Long>(){  
        @Override  
        public User getIt(Long id) throws SQLException {
        	User u = new User();
        	u.setUserId(id);
        	u.setUserCode("D-" + id);
        	return u;
        }
		});
		return id;
	}
	
	public int insert(User u) throws SQLException {
		return dao.insert(u);
	}
	
	public int insertIgnore(User u) throws SQLException {
		return dao.insertIgnore(u);
	}
	
	public int update(User u) throws SQLException {
		return dao.update(u);
	}
	
	public int delete(User u) throws SQLException {
		return dao.delete(u);
	}
	
	public int delete(User u, String[] heads) throws SQLException {
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
	
	public List<Long> findIdByCode(String code) throws SQLException {
		return dao.findIdByCode(code);
	}
	
//	public void doIt() throws Exception {
//		ArrayListLen<User> listBeans = getGsonOfAll1(new GsonListBeanHandler<User>(Duty.class));
//		logger.info("ArrayListLen<User>:" + listBeans.toString());
//	}
	
}
