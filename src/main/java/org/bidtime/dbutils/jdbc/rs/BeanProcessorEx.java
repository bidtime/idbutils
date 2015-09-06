package org.bidtime.dbutils.jdbc.rs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.BeanProcessor;
import org.bidtime.utils.comm.CaseInsensitiveHashMap;

/**
 * @author jss
 * 
 *         提供对从ResultSet中取出数据,封装成Bean的功能
 * 
 */
public class BeanProcessorEx extends BeanProcessor {
	
	public BeanProcessorEx() {
	}

	@Override
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
		return super.toBean(rs, type);
	}

	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
        Map<String, Object> result = new CaseInsensitiveHashMap();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        for (int i = 1; i <= cols; i++) {
            String columnName = rsmd.getColumnLabel(i);
            if (null == columnName || 0 == columnName.length()) {
              columnName = rsmd.getColumnName(i);
            }
            result.put(columnName, rs.getObject(i));
        }
        return result;
	}
}
