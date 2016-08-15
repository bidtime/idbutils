package org.bidtime.basicdata.duty.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.jdbc.dao.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository
public class DutyDAO_ extends BasicDAO {
	
//	@Resource(name = "dataSource2")
//	@Override
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}

//	public GsonEbRst getGsonOfAll() throws SQLException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		return getGsonOfParams("selectSqlOfAll", params, false);
//	}
//
//	public GsonMaps getGsonOfAll2() throws SQLException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		return this.getGsonMapsOfParams("selectSqlOfAll", params);
//	}
//
//	public GsonRstMaps getGsonOfAll4() throws SQLException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		return this.getGsonRstMapsOfParams("selectSqlOfAll", params);
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public <T> T getGsonOfAll1(ResultSetHandler h, Integer nPageIdx, Short nPageSize) throws SQLException {
//		Map<String, Object> params = new HashMap<String, Object>();
//		return (T) this.queryEx("selectSqlOfAll", h, params, nPageIdx, nPageSize);
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T list(ResultSetHandler h) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		return (T) this.query("selectSqlOfAll", h, params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T info(ResultSetHandler h, Object o) throws SQLException {
		return (T) this.queryByPK("selectSqlOfAll", h, o);
	}

}