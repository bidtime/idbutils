package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承AbstractListDTOHandler类
 *
 */

@SuppressWarnings("serial")
public class BeanListDTOHandler<T> extends AbstractListDTOHandler<T> {

	public BeanListDTOHandler(Class<T> type) {
		this(type, false);
	}

	public BeanListDTOHandler(Class<T> type, boolean countSql) {
		this(type, countSql, BeanAdapt.AUTO);
	}

	public BeanListDTOHandler(Class<T> type, BeanAdapt beanAdapt) {
		this(type, false, beanAdapt);
	}

//	public BeanListDTOHandler(Class<T> type, BeanProcessorEx convert,
//			boolean countSql) {
//		this(type, convert, countSql, BeanAdapt.AUTO);
//	}

	public BeanListDTOHandler(Class<T> type, boolean countSql, BeanAdapt beanAdapt) {
		this(type, new BeanProcessorEx(), countSql, beanAdapt);
	}

	public BeanListDTOHandler(Class<T> type, BeanAdapt beanAdapt, boolean countSql) {
		this(type, new BeanProcessorEx(), countSql, beanAdapt);
	}

	public BeanListDTOHandler(Class<T> type, BeanProcessorEx convert,
			boolean countSql, BeanAdapt beanAdapt) {
		super.setProp(type, convert, countSql, beanAdapt);
	}

	@Override
	protected void handleIt(ResultSet rs, Collection<T> c) throws SQLException {
		//this.convert.toBean(rs, this.type, this.mapBeanPropColumns);
		convert.toBeanList(rs, type, c, mapBeanPropColumns);
	}

}
