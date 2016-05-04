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
import org.bidtime.utils.spring.SpringContextUtils;

/**
 * @author jss
 * 
 *         提供对从dao.xml中取出sql,并执行sql
 * 
 */
public class BasicDAO {

	//protected Dialect dialect = null;

	// 单连接应用,每个Dao对象互相隔离
	// private final ThreadLocal<Connection> connLocal = new
	// ThreadLocal<Connection>();
	// public ThreadLocal<Connection> getConnLocal() {
	// return connLocal;
	// }

	protected String dsName;

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

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
					if (dsName == null) {
						setDataSource(SpringContextUtils.getDataSourceDefault());
					} else {
						setDataSource(SpringContextUtils.getDataSourceOfName(dsName));
					}
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

	public String getTableName() throws Exception {
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(this);
		return tp != null ? tp.getTableName() : null;
	}

	// insert

	public int insert(Object object, PropAdapt pa) throws SQLException {
		return insert(object, pa);
	}
	
	public int insert(Object object) throws SQLException {
		return SqlLoadUtils.insert(getCurrentDataSource(), this.getClass(),
				object);
	}

	@SuppressWarnings("rawtypes")
	public int insert(List list, PropAdapt pa) throws SQLException {
		return insert(list, pa);
	}
	
	@SuppressWarnings("rawtypes")
	public int insert(List list) throws SQLException {
		return SqlLoadUtils.insert(getCurrentDataSource(),
				this.getClass(), list);
	}

	public int insert(GsonRows g) throws SQLException {
		return SqlLoadUtils.insert(getCurrentDataSource(), this.getClass(), g);
	}

	public int insertForPK(GsonRow g, String pk) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), g, pk);
	}

	public <T> T insertForPK(GsonRow g) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), g);
	}

	public <T> T insertForPK(Object object, PropAdapt pa) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), object, pa);
	}
	
	public <T> T insertForPK(Object object) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), object);
	}

	@SuppressWarnings("rawtypes")
	public <T> T insertForPK(Object o, PKCallback cb) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), o, cb);
	}

	@SuppressWarnings("rawtypes")
	public <T> T insertForPK(Object o, PropAdapt pa, PKCallback cb) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), o, pa, cb);
	}

	public int insertForPK(GsonRows r, String pk) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), r, pk);
	}

	public <T> T insertForPK(GsonRows r) throws SQLException {
		return SqlLoadUtils.insertForPK(getCurrentDataSource(),
				this.getClass(), r);
	}

	public int insert(GsonRow g) throws SQLException {
		return SqlLoadUtils.insert(getCurrentDataSource(), this.getClass(), g);
	}
	
	// delete

	public int delete(Object[] ids) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(), this.getClass(),
				ids);
	}

	public int delete(GsonRow g) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g);
	}

	public int delete(GsonRow g, String[] heads) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g,
				heads);
	}

	public int delete(GsonRows g) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g);
	}

	public int delete(GsonRows g, String[] heads) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(), this.getClass(), g,
				heads);
	}

	public int delete(Object o) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(), this.getClass(),
				o);
	}

	public int delete(Object o, String[] heads) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(), this.getClass(),
				o, heads);
	}

	@SuppressWarnings("rawtypes")
	public int delete(List list) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(),
				this.getClass(), list);
	}

	@SuppressWarnings("rawtypes")
	public int delete(List list, String[] heads) throws SQLException {
		return SqlLoadUtils.delete(getCurrentDataSource(),
				this.getClass(), list, heads);
	}

	// update
	
	public int update(GsonRow g) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g);
	}

	public int update(GsonRow g, String[] arHead) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g,
				arHead);
	}

	public int update(Object o) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				o);
	}

	public int update(Object o, PropAdapt pa) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				o, pa);
	}

	public int update(Object o, String[] heads) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				o, heads);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list, PropAdapt pa) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list, pa);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list, String[] heads)
			throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list, heads);
	}

	@SuppressWarnings("rawtypes")
	public int update(List list, String[] heads, PropAdapt pa)
			throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(),
				this.getClass(), list, heads, pa);
	}

	public int update(GsonRows g) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g);
	}

	public int update(GsonRows g, String[] arHead) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(), g,
				arHead);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int update(String sqlId, Object[] params) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				sqlId, params);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, GsonRow row) throws Exception {
		return SqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, row);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, Object[][] params)
			throws SQLException {
		return SqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, params);
	}

	/**
	 * @param sql
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, List<Object[]> list)
			throws SQLException {
		return SqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, list);
	}

	/**
	 * @param sqlId
	 * @param rows
	 * @return
	 * @throws SQLException
	 */
	public int updateSqlBatch(String sql, GsonRows rows)
			throws SQLException {
		return SqlLoadUtils.updateSqlBatch(getCurrentDataSource(), sql, rows);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateBatch(String sqlId, Object[][] params) throws SQLException {
		return SqlLoadUtils.updateBatch(getCurrentDataSource(),
				this.getClass(), sqlId, params);
	}

	/**
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int update(String sqlId, Map<String, ?> params) throws SQLException {
		return SqlLoadUtils.update(getCurrentDataSource(), this.getClass(),
				sqlId, params);
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateSql(String sql, Object[] params) throws SQLException {
		return SqlLoadUtils.updateSql(getCurrentDataSource(), sql, params);
	}

	/**
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int updateSql(String sql) throws SQLException {
		return SqlLoadUtils.updateSql(getCurrentDataSource(), sql);
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
		return SqlLoadUtils.callSP(getCurrentDataSource(), this.getClass(),
				spId, in, out);
	}

	public boolean callSpIn(String spId, Object[] in) throws SQLException {
		return SqlLoadUtils.callSP(getCurrentDataSource(), this.getClass(),
				spId, in, null);
	}

	public boolean callSpOut(String spId, Object[] out) throws SQLException {
		return SqlLoadUtils.callSP(getCurrentDataSource(), this.getClass(),
				spId, null, out);
	}
	
	// query sql
	
	public <T> T querySql(String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params, Integer nPageIdx, Integer nPageSize)
			throws SQLException {
		return SqlLoadUtils.querySql(getCurrentDataSource(), sql, rsh, params,
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
		return SqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T query(String sqlId, ResultSetHandler<T> rsh, Map<String, ?> params)
			throws SQLException {
		return SqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T queryOne(String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return SqlLoadUtils.queryOne(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T query(String sqlId, String colId, ResultSetHandler<T> rsh, Map<String, ?> params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return SqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, colId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T query(String sqlId, String colId, ResultSetHandler<T> rsh, Map<String, ?> params)
			throws SQLException {
		return SqlLoadUtils.query(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	public <T> T queryOne(String sqlId, String colId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return SqlLoadUtils.queryOne(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	// queryByPK
	
	public <T> T queryByPK(String sqlId, ResultSetHandler<T> rsh, Object params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return SqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T queryByPK(String sqlId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return SqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T queryByPKOne(String sqlId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return SqlLoadUtils.queryByPKOne(getCurrentDataSource(), this.getClass(),
				sqlId, rsh, params);
	}
	
	public <T> T queryByPK(String sqlId, String colId, ResultSetHandler<T> rsh, Object params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return SqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, colId, params, nPageIdx, nPageSize, rsh);
	}
	
	public <T> T queryByPK(String sqlId, String colId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return SqlLoadUtils.queryByPK(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	public <T> T queryByPKOne(String sqlId, String colId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return SqlLoadUtils.queryByPKOne(getCurrentDataSource(), this.getClass(),
				sqlId, colId, rsh, params);
	}
	
	// queryEx
	
	@Deprecated
	public <T> T queryExOne(String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return queryOne(sqlId, rsh, params);
	}

	@Deprecated
	public <T> T queryExOneByPK(String sqlId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return queryByPKOne(sqlId, rsh, params);
	}	

	@Deprecated
	public <T> T queryEx(String sqlId, ResultSetHandler<T> rsh, Map<String, ?> params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return query(sqlId, rsh, params, nPageIdx, nPageSize);
	}

	@Deprecated
	public <T> T queryExByPK(String sqlId, ResultSetHandler<T> rsh, Object params, 
			Integer nPageIdx, Integer nPageSize) throws SQLException {
		return queryByPK(sqlId, rsh, params, nPageIdx, nPageSize);
	}

	@Deprecated
	public <T> T queryEx(String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return query(sqlId, rsh, params);
	}
	
	@Deprecated
	public <T> T queryExByPK(String sqlId, ResultSetHandler<T> rsh,
			Object params) throws SQLException {
		return queryByPK(sqlId, rsh, params);
	}
}
