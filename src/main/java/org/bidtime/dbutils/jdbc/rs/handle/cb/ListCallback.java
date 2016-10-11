package org.bidtime.dbutils.jdbc.rs.handle.cb;

import java.util.List;

/*
	CollectionCallback ccb = new CollectionCallback<Long>() {
		    @Override
		    public Collection<Long> callback() {
		    	return new HashSet<Long>();
		    }
		};
 */
public interface ListCallback<T> {

	public abstract List<T> callback();
	
}
