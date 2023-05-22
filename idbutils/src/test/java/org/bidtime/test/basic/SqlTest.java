package org.bidtime.test.basic;

import java.sql.SQLException;
import java.util.HashMap;

import org.bidtime.basicdata.duty.service.DutyService;
import org.bidtime.dbutils.data.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.rs.handle.GsonRowsHandler;
import org.bidtime.test.BasicTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bidtime on 2015/9/23.
 */
public class SqlTest extends BasicTest {

	@Autowired
	protected DutyService service;
  
  /**
   * @throws SQLException
   * @author riverbo
   * @since 2018.05.28
   */
  @Test
  public void openSql() throws SQLException {
    String sql = "select * from ap_duty";
    String tableName = GsonUtils.getTableName(sql);
    GsonRows rows = openSql(sql);
    String ss = GsonUtils.toInsertSql(rows, tableName, "insert into", true);
    super.print(ss);
  }

  private GsonRows openSql(String sql) throws SQLException {
    GsonRowsHandler r = new GsonRowsHandler();
    GsonRows rows = service.getDao().querySql(sql, r, new HashMap<>());
    print(rows);
    return rows;
  }

}
