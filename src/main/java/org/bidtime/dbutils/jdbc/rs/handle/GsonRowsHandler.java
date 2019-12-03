package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bidtime.dbutils.data.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetExHandler;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class GsonRowsHandler extends ResultSetExHandler<GsonRows> {

	public GsonRowsHandler() {
		super.setProp(GsonRows.class);
	}

//	public GsonRowsHandler(boolean countSql) {
//		super.setProp(GsonRows.class, countSql);
//	}
	
	@Override
	public GsonRows handle(ResultSet rs) throws SQLException {
		return rs.next() ? convert.toGsonRows(rs) : null;
	}

}
