package org.bidtime.dbutils.jdbc.rs.handle.ext;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bidtime.dbutils.gson.ResultDTO;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetHandler类
 *
 */
public class ResultSetDTOHandler<T> extends ResultSetExHandler<ResultDTO<T>> {
	
	@Override
	public ResultDTO<T> handle(ResultSet rs) throws SQLException {
    	ResultDTO<T> t = new ResultDTO<T>();
    	//if ( rs.next() ) {
    		t.setData(doDTO(rs));
    	//}
    	return t;
	}
	
	@SuppressWarnings("unchecked")
	public T doDTO(ResultSet rs) throws SQLException {
		return rs.next() ? (T)convert.toBean(rs, type) : null;
	}

}
