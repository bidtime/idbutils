package org.bidtime.dbutils.jdbc.dialect;

public class SqlServerDialect implements Dialect {

	private static final SqlServerDialect INSTANCE = new SqlServerDialect();

	private SqlServerDialect() {
	}

	public static final SqlServerDialect getInstance() {
		return INSTANCE;
	}

//	@Override
//	public String getIdSql() {
//		return "select @@identity";
//	}

	@Override
	public String getSubSqlOfPage(String sql) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getInsertIgnore() {
		return "insert ignore into";
	}
	
	public String getReplace() {
		return "replace into";
	}

	// public SqlHolder buildPageQuerySql(SqlHolder holder, PageInfo pageInfo) {
	// throw new UnsupportedOperationException();
	// }

}
