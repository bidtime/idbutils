package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetHandler类
 *
 */
public class BeanDTOHandler<T> extends ResultSetDTOHandler<T> {

    public BeanDTOHandler(Class<T> type) {
    	this(type, false);
    }

    public BeanDTOHandler(Class<T> type, boolean countSql) {
    	this(type, countSql, true);
    }

    public BeanDTOHandler(Class<T> type, boolean countSql, boolean thumbsHead) {
    	this(type, new BeanProcessorEx(), countSql, thumbsHead);
    }
    
    public BeanDTOHandler(Class<T> type, BeanProcessorEx convert, boolean countSql) {
    	this(type, convert, countSql, true);
    }
    
    public BeanDTOHandler(Class<T> type, BeanProcessorEx convert, boolean countSql,
    		boolean thumbsHead) {
        setProp(type, convert, countSql, thumbsHead);
    }
    
	@Override
	public T doDTO(ResultSet rs) throws SQLException {
		return super.doDTO(rs);
	}

}
