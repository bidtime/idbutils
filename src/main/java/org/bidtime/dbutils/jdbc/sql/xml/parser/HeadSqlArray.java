package org.bidtime.dbutils.jdbc.sql.xml.parser;

import java.util.Map;

public class HeadSqlArray {
	private String sql;
	private String countSql;
	private String[] headFlds;
	private Map<String, String> mapColumnDescript;
	
	public Map<String, String> getMapColumnDescript() {
		return mapColumnDescript;
	}

	public void setMapColumnDescript(Map<String, String> mapColumnDescript) {
		this.mapColumnDescript = mapColumnDescript;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}
	
	public HeadSqlArray(String sql, String countSql) {
		this.sql = sql;
		this.countSql = countSql;
	}
	
	public HeadSqlArray(String sql, String[] headFlds, 
			String countSql, Map<String, String> mapColumnDescript) {
		this.sql = sql;
		this.headFlds = headFlds;
		this.countSql = countSql;
		this.mapColumnDescript = mapColumnDescript;
	}

	public String getSql() {
		return sql;
	}

//	public String getSqlOfPage() {
//		return sql  + CAutoFitSql.getSubSqlOfPage();
//	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String[] getHeadFlds() {
		return headFlds;
	}

	public void setHeadFlds(String[] headFlds) {
		this.headFlds = headFlds;
	}

}
