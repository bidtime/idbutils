/*
 * $Id:$
 * Copyright 2017 ecarpo.com All rights reserved.
 */
package org.bidtime.dbutils.base.mapper;

import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.DeleteAdapt;
import org.bidtime.dbutils.jdbc.rs.InsertAdapt;

/**
 * @author maguangzu
 * @since 2017年8月29日
 */
public interface BaseMapper {

	<K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params);

	<K> K selectByQuery(ResultSetHandler<K> h, Object params);

	<K> K selectByQuery(ResultSetHandler<K> h);

	<K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params, Integer pageIdx, Integer pageSize);

	<K> K selectByQuery(ResultSetHandler<K> h, Object params, Integer pageIdx, Integer pageSize);

	<K> K selectByQuery(ResultSetHandler<K> h, Integer pageIdx, Integer pageSize);

//	<K> ResultDTO<K> selectByQuery(ResultDTO<ResultSetHandler<K>> h, Map<String, Object> params);

	<K> K selectById(ResultSetHandler<K> h, Number id);

	<K> K selectById(ResultSetHandler<K> h, String id);
	
	// insert
	
	<K> int insert(K k);
	
	<K, M> M insert(K k, InsertAdapt ia);

	<K> int insert(K k, BeanAdapt ba);

	//<K, M> M insert(K k, InsertAdapt ia, BeanAdapt ba);
	
	// update

	<K> int update(K k);

	<K> int update(K k, BeanAdapt ba);
	
	// delete

	<K> int delete(K k);

	<K> int delete(K k, DeleteAdapt adapt);

	int deleteById(Number id);

	int deleteById(String id);
	
}
