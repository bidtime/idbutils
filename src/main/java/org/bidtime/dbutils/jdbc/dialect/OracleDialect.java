package org.bidtime.dbutils.jdbc.dialect;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class OracleDialect implements Dialect {

	// 定义一个忽略大小写匹配模式
	private static final Pattern ORDER_SQL_PATTERN = Pattern.compile(
			"\\s*order\\s+by\\s*", Pattern.CASE_INSENSITIVE);

	private static final OracleDialect INSTANCE = new OracleDialect();

	private OracleDialect() {
	}

	public static final OracleDialect getInstance() {
		return INSTANCE;
	}

//	@Override
//	public String getIdSql() {
//		throw new UnsupportedOperationException();
//	}

	@Override
	public String getSubSqlOfPage(String sql) {
		return sql + " rowno ?,? ";
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
	// throw new UnsupportedOperationException();
	// }

	public static boolean hasOrderBy(String sql) {
		if (StringUtils.isEmpty(sql)) {
			return false;
		}

		// 去掉sql中 的常量值和双引号包含的内容
		sql = sql.replaceAll("('.*?')|(\".*?\")", "");

		// 模式匹配
		return ORDER_SQL_PATTERN.matcher(sql).find();
	}
}
