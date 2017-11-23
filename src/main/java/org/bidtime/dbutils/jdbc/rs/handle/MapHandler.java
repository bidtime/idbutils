package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetExHandler;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class MapHandler extends ResultSetExHandler<Map<String, Object>> {

	public MapHandler() {
		setProp(Map.class);
	}

//	public MapHandler(boolean countSql) {
//		setProp(Map.class, countSql);
//	}

//	public MapDTOHandler(BeanProcessorEx convert, boolean countSql) {
//		super.setProp(Map.class, convert, countSql);
//	}

	@Override
	public Map<String, Object> handle(ResultSet rs) throws SQLException {
		return rs.next() ? this.convert.toMap(rs, this.mapBeanPropColumns) : null;
	}

}
