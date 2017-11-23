package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承AbstractListDTOHandler类
 *
 */

@SuppressWarnings("serial")
public class BeanListHandler<T> extends AbstractListHandler<T> {

	public BeanListHandler(Class<T> type) {
		super.setProp(type);
	}
	
	public BeanListHandler(Class<T> type, int initilaSize) {
		super.setProp(type, initilaSize);
	}
	
	@SuppressWarnings("rawtypes")
	public BeanListHandler(Class<T> type, Class<Collection> listType) {
		super.setProp(type, listType);
	}
	
	@SuppressWarnings("rawtypes")
	public BeanListHandler(Class<T> type, Class<Collection> listType, int initilaSize) {
		super.setProp(type, listType, initilaSize);
	}
	
	public BeanListHandler(Class<T> type, Collection<T> c) {
		super.setProp(type, c);
	}

	@Override
	protected void handleRow(ResultSet rs, Collection<T> c) throws SQLException {
		//this.convert.toBean(rs, this.type, this.mapBeanPropColumns);
		convert.toBeanList(rs, this.type, c, this.mapBeanPropColumns);
	}

}
