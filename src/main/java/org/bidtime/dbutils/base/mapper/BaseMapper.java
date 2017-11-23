/*
 * $Id:$
 * Copyright 2017 ecarpo.com All rights reserved.
 */
package org.bidtime.dbutils.base.mapper;

import java.sql.SQLException;
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

	<K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params) throws SQLException;

	<K> K selectByQuery(ResultSetHandler<K> h, Object params) throws SQLException;

	<K> K selectByQuery(ResultSetHandler<K> h) throws SQLException;

	<K> K selectByQuery(ResultSetHandler<K> h, Map<String, Object> params, Integer pageIdx, Integer pageSize) throws SQLException;

	<K> K selectByQuery(ResultSetHandler<K> h, Object params, Integer pageIdx, Integer pageSize) throws SQLException;

	<K> K selectByQuery(ResultSetHandler<K> h, Integer pageIdx, Integer pageSize) throws SQLException;

//	<K> ResultDTO<K> selectByQuery(ResultDTO<ResultSetHandler<K>> h, Map<String, Object> params) throws SQLException;

	<K> K selectById(ResultSetHandler<K> h, Number id) throws SQLException;

	<K> K selectById(ResultSetHandler<K> h, String id) throws SQLException;
	
	// insert
	
	<K> int insert(K k) throws SQLException;
	
	<K, M> M insert(K k, InsertAdapt ia) throws SQLException;

	<K> int insert(K k, BeanAdapt ba) throws SQLException;

	//<K, M> M insert(K k, InsertAdapt ia, BeanAdapt ba) throws SQLException;
	
	// update

	<K> int update(K k) throws SQLException;

	<K> int update(K k, BeanAdapt ba) throws SQLException;
	
	// delete

	<K> int delete(K k) throws SQLException;

	<K> int delete(K k, DeleteAdapt adapt) throws SQLException;

	int deleteById(Number id) throws SQLException;

	int deleteById(String id) throws SQLException;
	
}
