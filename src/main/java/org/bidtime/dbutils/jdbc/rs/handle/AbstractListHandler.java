package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetExHandler;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractListHandler<T> extends ResultSetExHandler<Collection<T>> {
	
	protected Collection<T> c;
	
	protected Integer initialSize;

	@SuppressWarnings("rawtypes")
	protected Class<Collection> listType;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void setProp(Class type) {
		super.setProp(type);
		this.listType = null;
	}
	
	@SuppressWarnings("rawtypes")
	public void setProp(Class<T> type, Class<Collection> listType) {
		super.setProp(type);
		this.listType = listType;
		this.initialSize = 20;
	}
	
	public void setProp(Class<T> type, Integer initialSize) {
		super.setProp(type);
		this.listType = null;
		this.initialSize = initialSize;
	}
	
	@SuppressWarnings("rawtypes")
	public void setProp(Class<T> type, Class<Collection> listType, Integer initialSize) {
		super.setProp(type);
		this.listType = listType;
		this.initialSize = initialSize;
	}

	public void setProp(Class<T> type, Collection<T> c) {
		super.setProp(type);
		this.c = c;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Collection<T> newInstance(Class<Collection> c) throws SQLException {
        try {
            return (Collection<T>)c.newInstance();
        } catch (InstantiationException e) {
            throw new SQLException(
                "Cannot create " + c.getName() + ": " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new SQLException(
                "Cannot create " + c.getName() + ": " + e.getMessage());
        }
    }

	@Override
	public Collection<T> handle(ResultSet rs) throws SQLException {
		if (!rs.next()) {
			return null;
		} else {
			Collection<T> collect = null;
			if (c == null) {
				if (listType == null) {
					if (initialSize == null) {
						collect = new ArrayList<>();
					} else {
						collect = new ArrayList<>(initialSize);						
					}
				} else {
					collect = this.newInstance(listType);
				}
			} else {
				collect = c;
			}
			handleRow(rs, collect);
//			do {
//				collect.add(handleRow(rs));
//			} while (rs.next());
			return collect;
		}
	}
	
	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
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
	protected abstract void handleRow(ResultSet rs, Collection<T> c) throws SQLException;

}
