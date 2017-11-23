package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * @author jss
 * 
 * 提供对从ResultSet进行预处理的功能,继承AbstractListDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class MapListHandler extends AbstractListHandler<Map<String, Object>> {

	public MapListHandler() {
		setProp(Map.class);
	}

//	public MapListHandler(boolean countSql) {
//		setProp(Map.class, countSql);
//	}

//	public MapListDTOHandler(BeanProcessorEx convert,
//			boolean countSql) {
//		super.setProp(Map.class, convert, countSql);
//	}
	
    @Override
    protected void handleRow(ResultSet rs, Collection<Map<String, Object>> c) throws SQLException {
        this.convert.toMap(rs, this.mapBeanPropColumns);
    }

}
