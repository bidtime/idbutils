package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.bidtime.dbutils.jdbc.rs.handle.cb.SetCallback;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractSetDTOHandler<T> extends
	ResultSetDTOHandler<Set<T>> {
	
	protected SetCallback<T> ccb;

	protected Set<T> newCollect() {
		return new HashSet<T>();
	}

	@Override
	public Set<T> doDTO(ResultSet rs) throws SQLException {
		if (!rs.next()) {
			return null;
		} else {
			Set<T> collect = null;
			if (ccb != null) {
				collect = ccb.callback();
			} else {
				collect = this.newCollect();
			}
			do {
				collect.add(handleRow(rs));
			} while (rs.next());
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
	protected abstract T handleRow(ResultSet rs) throws SQLException;

}
