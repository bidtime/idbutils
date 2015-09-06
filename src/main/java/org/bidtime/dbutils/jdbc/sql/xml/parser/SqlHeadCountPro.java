package org.bidtime.dbutils.jdbc.sql.xml.parser;

public class SqlHeadCountPro {

	private String id;
	private String headId;
	private String type; // "insert", "delete", "update", "select", "call"

	private String jsonHeadId;
	private String sql;

	private String countSqlId;
	
	public String getCountSqlId() {
		return countSqlId;
	}

	public void setCountSqlId(String countSqlId) {
		this.countSqlId = countSqlId;
	}

	private String countSql;

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public SqlHeadCountPro() {
		initial();
	}

	public void initial() {
		id = "";
		headId = "";
		type = ""; // "insert", "delete", "update", "select", "call"

		jsonHeadId = "";
		sql = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJsonHeadId() {
		return jsonHeadId;
	}

	public void setJsonHeadId(String jsonHeadId) {
		this.jsonHeadId = jsonHeadId;
	}

}
