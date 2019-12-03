package org.bidtime.dbutils.base.service;

import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.mapper.BaseMapper;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.DeleteAdapt;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseManager<M extends BaseMapper> {

	@Autowired
	protected M mapper;

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
	
	public <K> int insert(K k, BeanAdapt ba) {
		return mapper.insert(k, ba);
	}
	
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
