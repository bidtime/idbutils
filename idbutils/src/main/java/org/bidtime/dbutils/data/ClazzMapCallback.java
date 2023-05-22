package org.bidtime.dbutils.data;

public abstract class ClazzMapCallback<R, T> {

	public ClazzMapCallback() {
	}

	public abstract R getIt(T t) throws Exception;
}
