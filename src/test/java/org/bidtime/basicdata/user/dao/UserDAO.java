package org.bidtime.basicdata.user.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.bidtime.dbutils.jdbc.dao.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends BasicDAO {
	
    public <T> T findIdByCode(ResultSetHandler<T> h, String code) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        return this.query("selectSqlOfId", h, params);
    }

    public List<Long> findIdByCode(String code) throws SQLException {
        ColumnListHandler<Long> h = new ColumnListHandler<>();
        List<Long> list = findIdByCode(h, code);
        return list;
    }

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