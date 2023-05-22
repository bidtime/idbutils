package org.bidtime.dbutils.jdbc.dialect;

public class MySQLDialect implements Dialect {

	private static final MySQLDialect INSTANCE = new MySQLDialect();

	private MySQLDialect() {
	}

	public static final MySQLDialect getInstance() {
		return INSTANCE;
	}

	// @Override
	// public String getIdSql() {
	// return "select last_insert_id()";
	// }

	@Override
	public String getSubSqlOfPage(String sql) {
		return sql + " limit ?,? ";
	}

	@Override
	public String getInsertIgnore() {
		return "insert ignore into";
	}

	@Override
	public String getReplace() {
		return "replace into";
	}

	// public SqlHolder buildPageQuerySql(SqlHolder holder, PageInfo pageInfo) {
	// String sql = holder.getSql();
	// if (pageInfo.getSkip() == 0) {
	// sql += " limit ?";
	// holder.addParam(Types.INTEGER, pageInfo.getPageSize());
	// } else {
	// sql += " limit ?,?";
	// holder.addParam(Types.INTEGER, pageInfo.getSkip());
	// holder.addParam(Types.INTEGER, pageInfo.getPageSize());
	// }
	// holder.setSql(sql);
	// return holder;
	// }
}
