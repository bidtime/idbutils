package org.bidtime.test.mapper.duty.service;

import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.base.service.BaseManager;
import org.bidtime.test.mapper.duty.mapper.DutyMapper;
import org.springframework.stereotype.Repository;

@Repository
public class Duty2Manager extends BaseManager<DutyMapper> {

  public <K> K selectByQueryCamel(ResultSetHandler<K> h, Object params) throws SQLException {
    return mapper.selectByQueryCamel(h, params);
  }

}
