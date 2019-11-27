package org.bidtime.test.mapper.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class Duty2DAO extends BaseDAO<DutyMapper> {

  public <K> K selectByQueryCamel(ResultSetHandler<K> h, Object params) throws SQLException {
    return mapper.selectByQueryCamel(h, params);
  }

}
