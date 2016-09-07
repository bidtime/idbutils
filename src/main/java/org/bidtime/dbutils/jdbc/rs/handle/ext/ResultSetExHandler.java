package org.bidtime.dbutils.jdbc.rs.handle.ext;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetHandler类
 *
 */
@SuppressWarnings("serial")
public class ResultSetExHandler<T> implements ResultSetHandler<T>, Serializable {

	protected Class<T> type;

	protected BeanProcessorEx convert = null;

	//protected BeanProcessorEx ROW_PROCESSOR = new BeanProcessorEx();

	protected boolean countSql = false;

	public boolean isCountSql() {
		return countSql;
	}

	public void setCountSql(boolean countSql) {
		this.countSql = countSql;
	}

//	@SuppressWarnings({ "rawtypes" })
//	public void setProp(Class type, BeanProcessorEx convert, boolean countSql) {
//		setProp(type, convert, countSql, false);
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setProp(Class type, BeanProcessorEx convert, boolean countSql,
			BeanAdapt beanAdapt) {
		this.type = type;
		this.convert = convert;
		this.countSql = countSql;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setProp(Class type, BeanProcessorEx convert, boolean countSql) {
		this.type = type;
		this.convert = convert;
		this.countSql = countSql;
	}

//	@SuppressWarnings("rawtypes")
//	public void setProp(Class type, boolean countSql) {
//		setProp(type, new BeanProcessorEx(), countSql);
//	}
//
//	@SuppressWarnings("rawtypes")
//	public void setProp(Class type) {
//		setProp(type, false);
//	}

	@Override
	public T handle(ResultSet rs) throws SQLException {
		return rs.next() ? this.convert.toBean(rs, this.type) : null;
	}

}
