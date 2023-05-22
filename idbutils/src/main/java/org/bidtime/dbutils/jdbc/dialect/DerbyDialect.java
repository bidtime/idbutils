package org.bidtime.dbutils.jdbc.dialect;


public class DerbyDialect implements Dialect {

	private static final DerbyDialect INSTANCE = new DerbyDialect();

	private DerbyDialect() {
	}

	public static final DerbyDialect getInstance() {
		return INSTANCE;
	}

//	@Override
//	public String getIdSql() {
//		return "values IDENTITY_VAL_LOCAL()";
//	}

	@Override
	public String getSubSqlOfPage(String sql) {
		return sql + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
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
	// sql += " fetch first ? rows only";
	// holder.addParam(Types.INTEGER, pageInfo.getPageSize());
	// } else {
	// sql += " offset ? rows fetch first ? rows only";
	// holder.addParam(Types.INTEGER, pageInfo.getSkip());
	// holder.addParam(Types.INTEGER, pageInfo.getPageSize());
	// }
	// holder.setSql(sql);
	// return holder;
	// }
}
