package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承AbstractGsonListHandler类
 *
 */
public class MapListDTOHandler extends AbstractListDTOHandler<Map<String, Object>> {

	public MapListDTOHandler() {
		this(false);
	}

	public MapListDTOHandler(boolean countSql) {
		this(countSql, false);
	}

	public MapListDTOHandler(boolean countSql, boolean thumbsHead) {
		this(new BeanProcessorEx(), countSql, thumbsHead);
	}

	public MapListDTOHandler(BeanProcessorEx convert,
			boolean countSql) {
		this(convert, countSql, false);
	}

	public MapListDTOHandler(BeanProcessorEx convert,
			boolean countSql, boolean thumbsHead) {
		super.setProp(Map.class, convert, countSql, thumbsHead);
	}
  
    @Override
    protected Map<String, Object> handleRow(ResultSet rs) throws SQLException {
        return this.convert.toMap(rs, this.mapBeanPropColumns);
    }

}
