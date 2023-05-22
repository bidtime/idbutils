package org.bidtime.dbutils.jdbc.dialect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CAutoFitSql {
	
	public static final String DERBY = "apache derby";
	public static final String SQLSRV = "Microsoft SQL Server";
	public static final String MYSQL = "mysql";
	public static final String ORACLE = "oracle";
	public static final String SQLITE = "SQLite";

	private final static Map<String, Dialect> mapDialect = new HashMap<String, Dialect>();

	public static Dialect getDialectOfConn(Connection conn) throws SQLException {
		String sDrvName = null;
		if (conn != null) {
			sDrvName = conn.getMetaData().getDatabaseProductName();
		} else {
			sDrvName = MYSQL;		//默认 mysql
		}
		Dialect dialect = mapDialect.get(sDrvName);
		if (dialect == null) {
			synchronized (mapDialect) {
				if (dialect == null) {
					if (StringUtils.equalsIgnoreCase(sDrvName, MYSQL)
							|| StringUtils.equalsIgnoreCase(sDrvName, SQLITE)) {
						dialect = MySQLDialect.getInstance();
					} else if (StringUtils.equalsIgnoreCase(sDrvName, DERBY)) {
						dialect = DerbyDialect.getInstance();
					} else if (StringUtils.equalsIgnoreCase(sDrvName, SQLSRV)) {
						dialect = SqlServerDialect.getInstance();
					} else if (StringUtils.equalsIgnoreCase(sDrvName, ORACLE)) {
						dialect = OracleDialect.getInstance();
					} else {
						dialect = null;
					}
					mapDialect.put(sDrvName, dialect);
				}
			}
		}
		return dialect;
	}

	public static String getSubSqlOfPage(Connection conn, String sql)
			throws SQLException {
		Dialect dialect = getDialectOfConn(conn);
		return dialect.getSubSqlOfPage(sql);
	}

	public static String getInsertIgnore(Connection conn) throws SQLException {
		Dialect dialect = getDialectOfConn(conn);
		return dialect.getInsertIgnore();
	}

  public static String getReplace(Connection conn) throws SQLException {
    Dialect dialect = getDialectOfConn(conn);
    return dialect.getReplace();
  }
	
//	public static String getLastIdSql(Connection conn) throws SQLException {
//		Dialect dialect = getDialectOfConn(conn);
//		return dialect.getIdSql();
//	}
	
}
