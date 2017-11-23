package org.bidtime.dbutils.jdbc.rs.handle;

import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class BeanDTOHandler<T> extends ResultSetDTOHandler<T> {

	public BeanDTOHandler(Class<T> type) {
    	this(type, false);
    }

    public BeanDTOHandler(Class<T> type, boolean countSql) {
    	super.setProp(type, countSql);
    }

}
