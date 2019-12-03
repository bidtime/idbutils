/*
 * $Id:$
 * Copyright 2017 ecarpo.com All rights reserved.
 */
package org.bidtime.test.mapper.duty.mapper;

import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.mapper.BaseMapper;

/**
 * @author maguangzu
 * @since 2017年8月29日
 */
public interface DutyMapper extends BaseMapper {

//  <T> T selectByQuery(ResultSetHandler<T> h, Map<String, Object> params) throws SQLException;
//  
//  int insert(Object o);
//
//  int insert(Object o, BeanAdapt ba);
  <K> K selectByQueryCamel(ResultSetHandler<K> h, Object params) throws SQLException;

}
