package org.bidtime.dbutils.jdbc.rs.handle.ext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.bidtime.dbutils.gson.ResultDTO;
import org.bidtime.utils.comm.CaseInsensitiveHashSet;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetHandler类
 *
 */
public class ResultSetDTOHandler<T> extends ResultSetExHandler<ResultDTO<T>> {
	
	protected Map<String, String> mapBeanPropColumns = null;
	protected Set<String> mapColumns = null;

	@Override
	public ResultDTO<T> handle(ResultSet rs) throws SQLException {
    	ResultDTO<T> t = new ResultDTO<T>();
   		if (this.isBeanAdapt()) {
   			mapColumns = new CaseInsensitiveHashSet();
   			t.setData(doDTO(rs));
   			t.setColMapProps(mapColumns);
   		} else {
   			t.setData(doDTO(rs));
   		}
    	return t;
	}
	
	@SuppressWarnings("unchecked")
	public T doDTO(ResultSet rs) throws SQLException {
		return rs.next() ? (T)convert.toBean(rs, type, mapBeanPropColumns, mapColumns) : null;
	}

//    protected String[] getResultSetCols(ResultSetMetaData rsmd) throws SQLException {
//        int cols = rsmd.getColumnCount();
//        String[] columnToProperty = new String[cols];
//        Arrays.fill(columnToProperty, null);
//        for (int col = 1; col <= cols; col++) {
//            String columnName = rsmd.getColumnLabel(col);
//            if (null == columnName || 0 == columnName.length()) {
//              columnName = rsmd.getColumnName(col);
//            }
//            columnToProperty[col - 1] = columnName;
//        }
//        return columnToProperty;
//    }

}
