package org.bidtime.dbutils.jdbc.sql.xml.parser;

public class SQLCmdType {

	public final static int INSERT = 1;
	public final static int DELETE = 2;
	public final static int UPDATE = 3;
	public final static int SELECT = 4;
	public final static int CALL = 5;
	public final static int UNKNOWN = 0;

	public static int getSqlCmdType(String sql) {
		String sSqlLower = sql.toLowerCase();
		if (sSqlLower.indexOf("delete ") == 0) {
			return DELETE;
		} else if (sSqlLower.indexOf("update ") == 0) {
			return UPDATE;
		} else if (sSqlLower.indexOf("insert ") == 0 || sSqlLower.indexOf("replace ") == 0) {
			return INSERT;
		} else if (sSqlLower.indexOf("call ") >= 0) {
			return CALL;
		} else if (sSqlLower.indexOf("if exists") == 0) {
			return SELECT;
		} else if (sSqlLower.indexOf("select ") == 0) {
			return SELECT;
		} else {
			return SELECT;
		}
	}

}
