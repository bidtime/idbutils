package org.bidtime.dbutils.jdbc.rs.handle;

import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetExHandler;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class BeanHandler<T> extends ResultSetExHandler<T> {

	public BeanHandler(Class<T> type) {
		super.setProp(type);
    }

//    public BeanHandler(Class<T> type, boolean countSql) {
//    	super.setProp(type, countSql);
//    }

}
