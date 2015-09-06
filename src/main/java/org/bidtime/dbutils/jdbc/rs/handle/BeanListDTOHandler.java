package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承AbstractGsonListHandler类
 *
 */
public class BeanListDTOHandler<T> extends AbstractListDTOHandler<T> {
	
	public BeanListDTOHandler(Class<T> type) {
		setProp(type);
	}

	public BeanListDTOHandler(Class<T> type, boolean countSql) {
		setProp(type, countSql);
	}

	public BeanListDTOHandler(Class<T> type, BeanProcessorEx convert,
			boolean countSql) {
		setProp(type, convert, countSql);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T handleRow(ResultSet rs) throws SQLException {
		return (T) this.convert.toBean(rs, this.type);
	}

}
