package org.bidtime.dbutils.jdbc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.gson.PropAdapt;
import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.connection.DbConnection;
import org.bidtime.dbutils.jdbc.connection.SqlLoadUtils;
import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.bidtime.dbutils.jdbc.sql.xml.parser.TTableProps;

/**
 * @author jss
 * 
 *         提供对从dao.xml中取出sql,并执行sql
 * 
 */
public class BasicDAO {
	
	protected SqlLoadUtils sqlLoadUtils = new SqlLoadUtils();
	
	// 单连接应用,每个Dao对象互相隔离
	// private final ThreadLocal<Connection> connLocal = new
	// ThreadLocal<Connection>();
	// public ThreadLocal<Connection> getConnLocal() {
	// return connLocal;
	// }

	protected DataSource dataSource = null;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getCurrentDataSource() {
		if (dataSource == null) {
			synchronized (BasicDAO.class) {
				if (dataSource == null) {
					setDataSource(JsonFieldXmlsLoader.getInstance().getDataSource());
				}
			}
		}
		return dataSource;
	}

	public final void beginTrans() {
		DbConnection.beginTrans(getCurrentDataSource());
	}

	public final void beginTrans(int level) {
		DbConnection.beginTrans(getCurrentDataSource(), level);
	}

	public final void commit() {
		DbConnection.commit(getCurrentDataSource());
	}

	public final void rollback() {
		DbConnection.rollback(getCurrentDataSource());
	}

	@SuppressWarnings("static-access")
	public String getTableName() throws Exception {
		TTableProps tp = JsonFieldXmlsLoader.getInstance().getTableProps(this);
		return tp != null ? tp.getTableName() : null;
	}

	// insert ignore

	public int insertIgnore(Object object, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.insertIgnore(getCurrentDataSource(), this.getClass(), pa);
	}
	
	public int insertIgnore(Object object) throws SQLException {
		return sqlLoadUtils.insertIgnore(getCurrentDataSource(), this.getClass(),
				object);
	}

	@SuppressWarnings("rawtypes")
	public int insertIgnore(List list, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.insertIgnore(getCurrentDataSource(),
				this.getClass(), list, pa);
	}
	
	@SuppressWarnings("rawtypes")
	public int insertIgnore(List list) throws SQLException {
		return sqlLoadUtils.insertIgnore(getCurrentDataSource(),
				this.getClass(), list);
	}

	// replace

	public int replace(Object object, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.replace(getCurrentDataSource(), this.getClass(), pa);
	}
	
	public int replace(Object object) throws SQLException {
		return sqlLoadUtils.replace(getCurrentDataSource(), this.getClass(),
				object);
	}

	@SuppressWarnings("rawtypes")
	public int replace(List list, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.replace(getCurrentDataSource(),
				this.getClass(), list, pa);
	}
	
	@SuppressWarnings("rawtypes")
	public int replace(List list) throws SQLException {
		return sqlLoadUtils.replace(getCurrentDataSource(),
				this.getClass(), list);
	}

	// insert
	
	public int insert(Object object, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.insert(getCurrentDataSource(), this.getClass(), pa);
	}
	
	public int insert(Object object) throws SQLException {
		return sqlLoadUtils.insert(getCurrentDataSource(), this.getClass(),
				object);
	}

	@SuppressWarnings("rawtypes")
	public int insert(List list, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.insert(getCurrentDataSource(),
				this.getClass(), list, pa);
	}
	
	@SuppressWarnings("rawtypes")
	public int insert(List list) throws SQLException {
		return sqlLoadUtils.insert(getCurrentDataSource(),
				this.getClass(), list);
	}

	public int insert(GsonRows g) throws SQLException {
		return sqlLoadUtils.insert(getCurrentDataSource(), this.getClass(), g);
	}

	public int insertForPK(GsonRow g, String pk) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), g, pk);
	}

	public <T> T insertForPK(GsonRow g) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), g);
	}

	public <T> T insertForPK(Object object, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), object, pa);
	}
	
	public <T> T insertForPK(Object object) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), object);
	}

	@SuppressWarnings("rawtypes")
	public <T> T insertForPK(Object o, PKCallback cb) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), o, cb);
	}

	@SuppressWarnings("rawtypes")
	public <T> T insertForPK(Object o, PropAdapt pa, PKCallback cb) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), o, pa, cb);
	}

	public int insertForPK(GsonRows r, String pk) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), r, pk);
	}

	public <T> T insertForPK(GsonRows r) throws SQLException {
		return sqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), r);
	}

	public int insert(GsonRow g) throws SQLException {
		return sqlLoadUtils.insert(getCurrentDataSource(), this.getClass(), g);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int insertBatch(List list) throws SQLException {
		return sqlLoadUtils.insertBatch(getCurrentDataSource(), this.getClass(), list);		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int insertBatch(List list, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.insertBatch(getCurrentDataSource(), this.getClass(), list, pa);				
	}
	
	public int insertIgnoreBatch(GsonRows g, String tblName) throws SQLException {
	return sqlLoadUtils.insertIgnoreBatch(getCurrentDataSource(), g, tblName);       
	}
	  
	public int replaceIgnoreBatch(GsonRows g, String tblName) throws SQLException {
	return sqlLoadUtils.repalceIgnoreBatch(getCurrentDataSource(), g, tblName);       
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int insertIgnoreBatch(List list) throws SQLException {
		return sqlLoadUtils.insertIgnoreBatch(getCurrentDataSource(), this.getClass(), list);
	}
	
	@SuppressWarnings("rawtypes")
    public int insertIgnoreBatch(List list, String tblName) throws SQLException {
	  return sqlLoadUtils.insertIgnoreBatch(getCurrentDataSource(), list, tblName);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int insertIgnoreBatch(List list, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.insertIgnoreBatch(getCurrentDataSource(), this.getClass(), list);				
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int updateBatch(List list) throws SQLException {
		return sqlLoadUtils.updateBatch(getCurrentDataSource(), this.getClass(), list);		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public int updateBatch(List list, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.updateBatch(getCurrentDataSource(), this.getClass(), list);				
	}
	
	// delete

	public int delete(Object[] ids) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(),
				ids);
	}

	public int delete(String fld, Object[] ids) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(),
				fld, ids);
	}

	public int delete(GsonRow g) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g);
	}

	public int delete(GsonRow g, String[] heads) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g,
				heads);
	}

	public int delete(GsonRows g) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g);
	}

	public int delete(GsonRows g, String[] heads) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g,
				heads);
	}

	public int delete(Object o) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(),
				o);
	}

	public int delete(Object o, String[] heads) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(), this.getClass(),
				o, heads);
	}

	@SuppressWarnings("rawtypes")
	public int delete(List list) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(),
				this.getClass(), list);
	}

	@SuppressWarnings("rawtypes")
	public int delete(List list, String[] heads) throws SQLException {
		return sqlLoadUtils.delete(getCurrentDataSource(),
				this.getClass(), list, heads);
	}

	// update
	
	public int update(GsonRow g) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g);
	}

	public int update(GsonRow g, String[] arHead) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g,
				arHead);
	}

	public int update(Object o) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				o);
	}

	public int update(Object o, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				o, pa);
	}

	public int update(Object o, String[] heads) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				o, heads);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list, PropAdapt pa) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list, pa);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list, String[] heads)
			throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list, heads);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list, String[] heads, PropAdapt pa)
			throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list, heads, pa);
	}

	public int update(GsonRows g) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g);
	}

	public int update(GsonRows g, String[] arHead) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g,
				arHead);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int update(String sqlId, Object[] params) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				sqlId, params);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, GsonRow row) throws Exception {
		return sqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, row);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, Object[][] params)
			throws SQLException {
		return sqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, params);
	}

	/**
	 * @param sql
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, List<Object[]> list)
			throws SQLException {
		return sqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, list);
	}

	/**
	 * @param sqlId
	 * @param rows
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, GsonRows rows)
			throws SQLException {
		return sqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, rows);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateBatch(String sqlId, Object[][] params) throws SQLException {
		return sqlLoadUtils.updateBatch(getCurrentDataSource(),
				this.getClass(), sqlId, params);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int update(String sqlId, Map<String, ?> params) throws SQLException {
		return sqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				sqlId, params);
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateSql(String sql, Object[] params) throws SQLException {
		return sqlLoadUtils.updateSql(getCurrentDataSource(), sql, params);
	}

	/**
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int updateSql(String sql) throws SQLException {
		return sqlLoadUtils.updateSql(getCurrentDataSource(), sql);
	}

	/**
	 * @param sProc
	 * @param in
	 * @param out
	 * @return
	 * @throws SQLException
	 */
	public boolean callSP(String spId, Object[] in, Object[] out)
			throws SQLException {
		return sqlLoadUtils.callSP(getCurrentDataSource(), this.getClass(),
				spId, in, out);
	}

	public boolean callSpIn(String spId, Object[] in) throws SQLException {
		return sqlLoadUtils.callSP(getCurrentDataSource(), this.getClass(),
				spId, in, null);
	}

	public boolean callSpOut(String spId, Object[] out) throws SQLException {
		return sqlLoadUtils.callSP(getCurrentDataSource(), this.getClass(),
				spId, null, out);
	}
	
	// query sql
	
	public <T> T querySql(String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params, Integer nPageIdx, Integer nPageSize)
			throws SQLException {
		return sqlLoadUtils.querySql(getCurrentDataSource(), sql, rsh, params,
				nPageIdx, nPageSize);
	}

	public <T> T querySql(String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return querySql(sql, rsh, params, null, null);
	}

	public <T> T querySqlOne(String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return querySql(sql, rsh, params, 0, 1);
	}
	
	// query
	
	public <T> T query(String sqlId, ResultSetHandler<T> rsh, Map<String, ?> params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return sqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T query(String sqlId, ResultSetHandler<T> rsh, Map<String, ?> params)
			throws SQLException {
		return sqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T queryOne(String sqlId, ResultSetHandler<T> rsh, Map<String, ?> params)
			throws SQLException {
		return sqlLoadUtils.queryOne(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T query(String sqlId, String colId, ResultSetHandler<T> rsh, Map<String, ?> params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return sqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, colId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T query(String sqlId, String colId, ResultSetHandler<T> rsh, Map<String, ?> params)
			throws SQLException {
		return sqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	public <T> T queryOne(String sqlId, String colId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return sqlLoadUtils.queryOne(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	// queryByPK
	
	public <T> T queryByPK(String sqlId, ResultSetHandler<T> rsh, Object params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return sqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T queryByPK(String sqlId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return sqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T queryByPKOne(String sqlId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return sqlLoadUtils.queryByPKOne(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T queryByPK(String sqlId, String colId, ResultSetHandler<T> rsh, Object params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return sqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, colId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T queryByPK(String sqlId, String colId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return sqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	public <T> T queryByPKOne(String sqlId, String colId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return sqlLoadUtils.queryByPKOne(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	//
//	public <T> T getListOfParms(ResultSetHandler<T> h, Map<String, Object> params,
//			Integer pageIdx, Integer pageSize) throws SQLException {
//		return this.query("getFullSql", h, params, pageIdx, pageSize);
//	}
//	
//	public <T> T getListOfParms(ResultSetHandler<T> h, Map<String, Object> params) throws SQLException {
//		return this.query("getFullSql", h, params);
//	}
//	
//	public <T> T getInfoById(ResultSetHandler<T> h, Object id) throws SQLException {
//		return this.queryByPK("getFullSql", h, id);
//	}
//	
//	public <T> T getInfoByParam(ResultSetHandler<T> h, Map<String, Object> params) throws SQLException {
//		return this.queryOne("getFullSql", h, params);
//	}
	
}
