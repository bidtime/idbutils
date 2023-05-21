package org.bidtime.dbutils.base.service;

import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.mapper.BaseMapper;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.DeleteAdapt;
import org.bidtime.dbutils.jdbc.rs.InsertAdapt;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<G extends BaseManager<M>, M extends BaseMapper> {
  
	@Autowired
	protected G manager;

	// select

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params) {
		return manager.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h) {
		return manager.selectByQuery(h);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Object params) {
		return manager.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params, Integer pageIdx, Integer pageSize) {
		return manager.selectByQuery(h, params, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Integer pageIdx, Integer pageSize) {
		return manager.selectByQuery(h, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Object params, Integer pageIdx, Integer pageSize) {
		return manager.selectByQuery(h, params, pageIdx, pageSize);
	}
	
	// insert
	
	public <K> int insert(K k) {
		return manager.insert(k);
	}
	
	public <K> int insert(K k, InsertAdapt ia) {
		return manager.insert(k, ia);
	}
	
//	public <K> int insert(K k, BeanAdapt ba) {
//		return manager.insert(k, ba);
//	}
	
	@SuppressWarnings({ "hiding" })
	public <K, M> M insertForPK(K k, ResultSetHandler<M> h) {
		return (M)manager.insertForPK(k, h);
	}
	
//	@SuppressWarnings("hiding")
//	public <K, M> M insertForPK(K k, M m) {
//		return (M)manager.insertForPK(k, m);
//	}
	
	@SuppressWarnings({ "hiding" })
	public <K, M> M insertForPK(K k, Class<M> clz) {
		return (M)manager.insertForPK(k, clz);
	}

//	@SuppressWarnings({ "hiding" })
//	public <K, M> M insertForPK(K k, Class<M> clz, InsertAdapt ia) {
//		return (M)manager.insertForPK(k, clz, ia);
//	}

	// update
	
	public <K> int update(K k) {
		return manager.update(k);
	}
	
	public <K> int update(K k, BeanAdapt ba) {
		return manager.update(k, ba);
	}
	
	// delete

	public <K> int delete(K k) {
		return manager.delete(k);
	}

	public <K> int delete(K k, DeleteAdapt adapt) {
		return manager.delete(k, adapt);
	}

	public int delete(Number id) {
		return manager.delete(id);
	}

	public int delete(String id) {
		return manager.delete(id);
	}

}
