package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承GsonBeanHandler类
 *
 */
public class MapDTOHandler extends ResultSetDTOHandler<Map<String, Object>> {
	
	public MapDTOHandler() {
		this(false);
	}

	public MapDTOHandler(boolean countSql) {
		this(countSql, false);
	}

	public MapDTOHandler(boolean countSql, boolean thumbsHead) {
		this(new BeanProcessorEx(), countSql, thumbsHead);
	}

	public MapDTOHandler(BeanProcessorEx convert,
			boolean countSql) {
		this(convert, countSql, false);
	}
	
	public MapDTOHandler(BeanProcessorEx convert,
			boolean countSql, boolean thumbsHead) {
		super.setProp(Map.class, convert, countSql, thumbsHead);
	}
 
    @Override
    public Map<String, Object> doDTO(ResultSet rs) throws SQLException {
       return rs.next() ? this.convert.toMap(rs, this.mapBeanPropColumns) : null;
    }

}
