package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bidtime.dbutils.jdbc.rs.handle.cb.ListCallback;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractListDTOHandler<T> extends
	ResultSetDTOHandler<List<T>> {
	
	protected ListCallback<T> ccb;

	protected List<T> newCollect() {
		return new ArrayList<>();
	}

	@Override
	public List<T> doDTO(ResultSet rs) throws SQLException {
		if (!rs.next()) {
			return null;
		} else {
			List<T> collect = null;
			if (ccb != null) {
				collect = ccb.callback();
			} else {
				collect = this.newCollect();
			}
			handleIt(rs, collect);
//			do {
//				collect.add(handleRow(rs));
//			} while (rs.next());
			return collect;
		}
	}

	/**
	 * Row handler. Method converts current row into some Java object.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> to process.
	 * @return row processing result
	 * @throws SQLException
	 *             error occurs
	 */
	protected abstract void handleIt(ResultSet rs, Collection<T> c) throws SQLException;

}
