package org.bidtime.dbutils.jdbc.sql;

import java.util.List;

public class SqlHolder {

	private String sql;
	
	private List<Object> paramList;	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void addParam(Object value) {
		paramList.add(value);
	}
	
	public Object[] getObjectArray() {
		if (paramList == null || paramList.size()<1) {
			return null;
		}
		Object[] result = new Object[paramList.size()];
		for (int i = 0; i < paramList.size(); i++) {
			result[i] = paramList.get(i);
		}
		return result;
	}

	public List<Object> getParamList() {
		return paramList;
	}

	public void setParamList(List<Object> paramList) {
		this.paramList = paramList;
	}

}
