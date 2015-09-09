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
		setProp(null);
	}

	public MapListDTOHandler(boolean countSql) {
		setProp(null, countSql);
	}

	public MapListDTOHandler(BeanProcessorEx convert,
			boolean countSql) {
		setProp(null, convert, countSql);
	}
  
    @Override
    protected Map<String, Object> handleRow(ResultSet rs) throws SQLException {
        return this.convert.toMap(rs);
    }

}
