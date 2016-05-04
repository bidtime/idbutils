package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承AbstractListDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class MapListDTOHandler extends AbstractListDTOHandler<Map<String, Object>> {

	public MapListDTOHandler() {
		this(false);
	}

	public MapListDTOHandler(boolean countSql) {
		this(new BeanProcessorEx(), countSql);
	}

	public MapListDTOHandler(BeanProcessorEx convert,
			boolean countSql) {
		super.setProp(Map.class, convert, countSql);
	}
  
    @Override
    protected Map<String, Object> handleRow(ResultSet rs) throws SQLException {
        return this.convert.toMap(rs, this.mapBeanPropColumns);
    }

}
