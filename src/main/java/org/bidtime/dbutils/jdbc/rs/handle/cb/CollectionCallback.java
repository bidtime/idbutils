package org.bidtime.dbutils.jdbc.rs.handle.cb;

import java.util.Collection;

/*
	CollectionCallback ccb = new CollectionCallback<Long>() {
		    @Override
		    public Collection<Long> callback() {
		    	return new HashSet<Long>();
		    }
		};
 */
public interface CollectionCallback<T> {

	public abstract Collection<T> callback();
	
}
