package org.bidtime.dbutils.base.service;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.dao.BaseDAO;
import org.bidtime.dbutils.base.mapper.BaseMapper;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.DeleteAdapt;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseManager<D extends BaseDAO<M>, M extends BaseMapper> {

	@Autowired
	protected D dao;

	// select

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params) throws SQLException {
		return dao.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h) throws SQLException {
		return dao.selectByQuery(h);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Object params) throws SQLException {
		return dao.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params, Integer pageIdx, Integer pageSize) throws SQLException {
		return dao.selectByQuery(h, params, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Integer pageIdx, Integer pageSize) throws SQLException {
		return dao.selectByQuery(h, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Object params, Integer pageIdx, Integer pageSize) throws SQLException {
		return dao.selectByQuery(h, params, pageIdx, pageSize);
	}
	
	// insert
	
	public <K> int insert(K k) throws SQLException {
		return dao.insert(k);
	}
	
	public <K> int insert(K k, BeanAdapt ba) throws SQLException {
		return dao.insert(k, ba);
	}
	
	// update
	
	public <K> int update(K k) throws SQLException {
		return dao.update(k);
	}
	
	public <K> int update(K k, BeanAdapt ba) throws SQLException {
		return dao.update(k, ba);
	}
	
	// delete

	public <K> int delete(K k) throws SQLException {
		return dao.delete(k);
	}

	public <K> int delete(K k, DeleteAdapt adapt) throws SQLException {
		return dao.delete(k, adapt);
	}

	public int delete(Number id) throws SQLException {
		return dao.delete(id);
	}

	public int delete(String id) throws SQLException {
		return dao.delete(id);
	}

}
