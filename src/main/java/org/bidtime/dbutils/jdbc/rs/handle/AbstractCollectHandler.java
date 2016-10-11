package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.jdbc.rs.handle.cb.CollectionCallback;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
public abstract class AbstractCollectHandler<T> implements ResultSetHandler<Collection<T>> {
	
	protected CollectionCallback<T> ccb;
	
	protected abstract Collection<T> newCollect();
	
    /**
     * Whole <code>ResultSet</code> handler. It produce <code>List</code> as
     * result. To convert individual rows into Java objects it uses
     * <code>handleRow(ResultSet)</code> method.
     *
     * @see #handleRow(ResultSet)
     * @param rs <code>ResultSet</code> to process.
     * @return a list of all rows in the result set
     * @throws SQLException error occurs
     */
    @Override
    public Collection<T> handle(ResultSet rs) throws SQLException {
		if (!rs.next()) {
			return null;
		} else {
			Collection<T> collect = null;
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
     * @param rs <code>ResultSet</code> to process.
     * @return row processing result
     * @throws SQLException error occurs
     */
    protected abstract T handleRow(ResultSet rs) throws SQLException;

}
