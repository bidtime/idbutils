package org.bidtime.dbutils.base.service;

import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.entity.DataEntity;
import org.bidtime.dbutils.base.mapper.BaseMapper;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.DeleteAdapt;
import org.bidtime.dbutils.jdbc.rs.InsertAdapt;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDataManager<M extends BaseMapper, D extends DataEntity> {

	@Autowired
	protected M mapper;

	@Autowired
	protected D data;

	// select

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params) {
		return mapper.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h) {
		return mapper.selectByQuery(h);
	}
	
	public <K> K selectByQuery(ResultSetHandler<K> h, Object params) {
		return mapper.selectByQuery(h, params);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params, Integer pageIdx, Integer pageSize) {
		return mapper.selectByQuery(h, params, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Integer pageIdx, Integer pageSize) {
		return mapper.selectByQuery(h, pageIdx, pageSize);
	}

	public <K> K selectByQuery(ResultSetHandler<K> h, Object params, Integer pageIdx, Integer pageSize) {
		return mapper.selectByQuery(h, params, pageIdx, pageSize);
	}
	
	// insert
	
	public <K> int insert(K k) {
		return mapper.insert(k);
	}
	
	public <K> int insert(K k, InsertAdapt ia) {
		return mapper.insert(k, ia);
	}
	
//	public <K> int insert(K k, BeanAdapt ba) {
//		return mapper.insert(k, ba);
//	}
	
	@SuppressWarnings({ "hiding" })
	public <K, M> M insertForPK(K k, ResultSetHandler<M> h) {
		return (M)mapper.insertForPK(k, h);
	}
	
//	@SuppressWarnings("hiding")
//	public <K, M> M insertForPK(K k, M m) {
//		return (M)mapper.insertForPK(k, m);
//	}
	
	@SuppressWarnings({ "hiding" })
	public <K, M> M insertForPK(K k, Class<M> clz) {
		return (M)mapper.insertForPK(k, clz);
	}
	
//	@SuppressWarnings({ "hiding" })
//	public <K, M> M insertForPK(K k, Class<M> clz, InsertAdapt ia) {
//		return (M)mapper.insertForPK(k, clz, ia);
//	}
	
//	public <K, M> M insertForPK(K k, BeanAdapt ba) {
//		return mapper.insert(k, ba);
//	}
	
	// update
	
	public <K> int update(K k) {
		return mapper.update(k);
	}
	
	public <K> int update(K k, BeanAdapt ba) {
		return mapper.update(k, ba);
	}
	
	// delete

	public <K> int delete(K k) {
		return mapper.delete(k);
	}

	public <K> int delete(K k, DeleteAdapt adapt) {
		return mapper.delete(k, adapt);
	}

	public int delete(Number id) {
		return mapper.delete(id);
	}

	public int delete(String id) {
		return mapper.delete(id);
	}

}
