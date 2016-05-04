package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class GsonRowsDTOHandler extends ResultSetDTOHandler<GsonRows> {

	public GsonRowsDTOHandler() {
		this(false);
	}

	public GsonRowsDTOHandler(boolean countSql) {
		this(new BeanProcessorEx(), false);
	}

	public GsonRowsDTOHandler(BeanProcessorEx convert, boolean countSql) {
		super.setProp(Map.class, convert, countSql);
	}

	@Override
	public GsonRows doDTO(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return convert.toGsonRows(rs);
		} else {
			return null;
		}
	}

}
