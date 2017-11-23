package org.bidtime.dbutils.jdbc.dialect;


public interface Dialect {

	/**
	 * 获取查询最新Id的sql
	 * 
	 * @return
	 */
//	public String getIdSql();

	/*
	 * 构建分页查询
	 */
	public String getSubSqlOfPage(String sql);
  
  public String getInsertIgnore();
  
	public String getReplace();

	/**
	 * 构建分页查询
	 * 
	 * @param holder
	 *            SqlHolder
	 * @param pageInfo
	 *            分页信息
	 * @return 新的SqlHolder
	 */
	// public SqlHolder buildPageQuerySql(final SqlHolder holder,
	// final PageInfo pageInfo);
}
