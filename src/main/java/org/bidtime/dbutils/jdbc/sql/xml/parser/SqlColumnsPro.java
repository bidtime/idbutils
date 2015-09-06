package org.bidtime.dbutils.jdbc.sql.xml.parser;

public class SqlColumnsPro {

	private String sql;
	private Object[][] params= null;
	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public SqlColumnsPro(String sql, Object[][] params) {
		this.sql = sql;
		this.params = params;
	}
	
	public Object[][] getParams() {
		return params;
	}
	
	public void setParams(Object[][] params) {
		this.params = params;
	}
}
