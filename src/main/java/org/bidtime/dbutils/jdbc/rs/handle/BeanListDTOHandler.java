package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承AbstractGsonListHandler类
 *
 */

public class BeanListDTOHandler<T> extends AbstractListDTOHandler<T> {

	public BeanListDTOHandler(Class<T> type) {
		this(type, false);
	}

	public BeanListDTOHandler(Class<T> type, boolean countSql) {
		this(type, ROW_PROCESSOR, countSql);
	}

	public BeanListDTOHandler(Class<T> type, BeanProcessorEx convert,
			boolean countSql) {
		this(type, convert, countSql, true);
	}

	public BeanListDTOHandler(Class<T> type, boolean countSql, boolean addHead) {
		this(type, ROW_PROCESSOR, countSql, addHead);
	}

	public BeanListDTOHandler(Class<T> type, BeanProcessorEx convert,
			boolean countSql, boolean addHead) {
		super.setProp(type, convert, countSql, addHead);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T handleRow(ResultSet rs) throws SQLException {
		return (T) this.convert.toBean(rs, this.type);
	}

}
