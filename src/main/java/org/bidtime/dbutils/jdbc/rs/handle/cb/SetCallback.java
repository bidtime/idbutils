package org.bidtime.dbutils.jdbc.rs.handle.cb;

import java.util.Set;

/*
	CollectionCallback ccb = new CollectionCallback<Long>() {
		    @Override
		    public Collection<Long> callback() {
		    	return new HashSet<Long>();
		    }
		};
 */
public interface SetCallback<T> {

	public abstract Set<T> callback();
	
}
