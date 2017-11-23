package org.bidtime.dbutils.base.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.mapper.BaseMapper;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.DeleteAdapt;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAO<M extends BaseMapper> {

	@Autowired
	protected M mapper;

	//protected Class type;

	public BaseDAO() {
		//type = (Class) getParams(1); // 上面定义中的第二个参数
	}

//	private Type getParams(int idx) {
//		Type genType = getClass().getGenericSuperclass();
//		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//		return params[idx];
//	}
	
	// select

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params) throws SQLException {
		return mapper.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h) throws SQLException {
		return mapper.selectByQuery(h);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Object params) throws SQLException {
		return mapper.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params, Integer pageIdx, Integer pageSize) throws SQLException {
		return mapper.selectByQuery(h, params, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Integer pageIdx, Integer pageSize) throws SQLException {
		return mapper.selectByQuery(h, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Object params, Integer pageIdx, Integer pageSize) throws SQLException {
		return mapper.selectByQuery(h, params, pageIdx, pageSize);
	}
	
	// insert
	
	public <K> int insert(K k) throws SQLException {
		return mapper.insert(k);
	}
	
	public <K> int insert(K k, BeanAdapt ba) throws SQLException {
		return mapper.insert(k, ba);
	}
	
	// update
	
	public <K> int update(K k) throws SQLException {
		return mapper.update(k);
	}
	
	public <K> int update(K k, BeanAdapt ba) throws SQLException {
		return mapper.update(k, ba);
	}
	
	// delete

	public <K> int delete(K k) throws SQLException {
		return mapper.delete(k);
	}

	public <K> int delete(K k, DeleteAdapt adapt) throws SQLException {
		return mapper.delete(k, adapt);
	}

	public int delete(Number id) throws SQLException {
		return mapper.delete(id);
	}

	public int delete(String id) throws SQLException {
		return mapper.delete(id);
	}

}
