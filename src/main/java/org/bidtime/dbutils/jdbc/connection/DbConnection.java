package org.bidtime.dbutils.jdbc.connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.QueryRunnerEx;
import org.bidtime.dbutils.gson.ResultDTO;
import org.bidtime.dbutils.jdbc.connection.ds.DataSourceTransactionHolder;
import org.bidtime.dbutils.jdbc.connection.log.LogInsertSql;
import org.bidtime.dbutils.jdbc.connection.log.LogSeleteSql;
import org.bidtime.dbutils.jdbc.connection.log.LogSpSql;
import org.bidtime.dbutils.jdbc.connection.log.LogUpdateSql;
import org.bidtime.dbutils.jdbc.dialect.CAutoFitSql;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.dbutils.jdbc.sql.xml.parser.HeadSqlArray;
import org.bidtime.utils.basic.ArrayComm;
import org.bidtime.utils.basic.ObjectComm;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author jss
 *
 */
public class DbConnection {

	// 使用 ThreadLocal 保存 DataSourceTransactionHolder 变量
	private volatile static ThreadLocal<DataSourceTransactionHolder> dataSourceTransThreadLocal = new ThreadLocal<>();
	
	private static Connection getConnOfSpringCtx(DataSource ds) throws SQLException {
		ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager
				.getResource(ds);
		if (conHolder == null) {
			return null;
		} else {
			return conHolder.getConnection();
		}
	}
	
	public static void beginTrans(DataSource ds) {
		DataSourceTransactionHolder h = dataSourceTransThreadLocal.get();
		if (h == null) {
			h = new DataSourceTransactionHolder(ds);
			dataSourceTransThreadLocal.set(h);
		} else {
			h.getPut(ds);
		}
	}
	
	public static void beginTrans(DataSource ds, int level) {
		DataSourceTransactionHolder h = dataSourceTransThreadLocal.get();
		if (h == null) {
			h = new DataSourceTransactionHolder(ds, level);
			dataSourceTransThreadLocal.set(h);
		} else {
			h.getPut(ds, level);
		}
	}
	
	public static void beginTrans(DataSource ds, DefaultTransactionDefinition def) {
		DataSourceTransactionHolder h = dataSourceTransThreadLocal.get();
		if (h == null) {
			h = new DataSourceTransactionHolder(ds, def);
			dataSourceTransThreadLocal.set(h);
		} else {
			h.getPut(ds, def);
		}
	}
	
	// 提交事务
	public static void commit(DataSource ds) {
		DataSourceTransactionHolder h = dataSourceTransThreadLocal.get();
		if (h != null) {
			if (h.commit(ds, true)) {
				dataSourceTransThreadLocal.remove();
			}
		}
	}

	// 回滚事务
	public static void rollback(DataSource ds) {
		DataSourceTransactionHolder h = dataSourceTransThreadLocal.get();
		if (h != null) {
			if (h.rollback(ds, true)) {
				dataSourceTransThreadLocal.remove();
			}
		}
	}

	public static <T> T insert(DataSource ds, String sql, ResultSetHandler<T> rsh, Object[] params)
			throws SQLException {
		Connection conn = getConnOfSpringCtx(ds);
		if (conn == null) {
			conn = DataSourceUtils.getConnection(ds);
			try {
				return insertConn(conn, sql, rsh, params);
			} finally {
				DataSourceUtils.releaseConnection(conn, ds);
				conn = null;
			}
		} else {
			return insertConn(conn, sql, rsh, params);
		}
	}
	
	private static <T> T insertConn(Connection conn, String sql, ResultSetHandler<T> rsh, Object[] params)
			throws SQLException {
		T generatedKeys = null;
		long startTime = System.currentTimeMillis();
		QueryRunnerEx q = new QueryRunnerEx();
		try {
			generatedKeys = q.insert(conn, sql, rsh, params);
		} finally {
			q = null;
		}
		if (LogInsertSql.logInfoOrDebug()) {
			LogInsertSql.logFormatTimeNow(startTime, sql, params);				
		}
		return generatedKeys;
	}

	public static int update(DataSource ds, String sql, Object[] params)
			throws SQLException {
		Connection conn = getConnOfSpringCtx(ds);
		if (conn == null) {
			conn = DataSourceUtils.getConnection(ds);
			try {
				return updateConn(conn, sql, params);
			} finally {
				DataSourceUtils.releaseConnection(conn, ds);
				conn = null;
			}
		} else {
			return updateConn(conn, sql, params);
		}
	}
	
	private static int updateConn(Connection conn, String sql, Object[] params)
			throws SQLException {
		long startTime = System.currentTimeMillis();
		QueryRunnerEx q = new QueryRunnerEx();
		int nResult = 0;
		try {
			nResult = q.update(conn, sql, params);
		} finally {
			q = null;
		}
		if (LogUpdateSql.logInfoOrDebug()) {
			LogUpdateSql.logFormatTimeNow(startTime, sql, params, nResult);
		}
		return nResult;
	}

	public static <T> T insertBatch(DataSource ds, String sql, ResultSetHandler<T> rsh, Object[][] params)
			throws SQLException {
		Connection conn = getConnOfSpringCtx(ds);
		if (conn == null) {
			conn = DataSourceUtils.getConnection(ds);
			try {
				return insertBatchConn(conn, sql,  rsh, params);
			} finally {
				DataSourceUtils.releaseConnection(conn, ds);
				conn = null;
			}
		} else {
			return insertBatchConn(conn, sql,  rsh, params);
		}
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	private static <T> T insertBatchConn(Connection conn, String sql, ResultSetHandler<T> rsh, Object[][] params)
			throws SQLException {
		T generatedKeys = null;
		long startTime = System.currentTimeMillis();
		QueryRunnerEx q = new QueryRunnerEx();
		try {
			generatedKeys = q.insertBatch(conn, sql, rsh, params);
		} finally {
			q = null;
		}
		if (LogInsertSql.logInfoOrDebug()) {
			LogInsertSql.logFormatTimeNow(startTime, sql, params);				
		}
		return generatedKeys;
	}

	public static int updateBatch(DataSource ds, String sql, Object[][] params)
			throws SQLException {
		Connection conn = getConnOfSpringCtx(ds);
		if (conn == null) {
			conn = DataSourceUtils.getConnection(ds);
			try {
				return updateBatchConn(conn, sql, params);
			} finally {
				DataSourceUtils.releaseConnection(conn, ds);
				conn = null;
			}
		} else {
			return updateBatchConn(conn, sql, params);
		}
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	private static int updateBatchConn(Connection conn, String sql, Object[][] params)
			throws SQLException {
		int nResult = 0;
		long startTime = System.currentTimeMillis();
		QueryRunnerEx q = new QueryRunnerEx();
		int[] affectedRows = null;
		try {
			affectedRows = q.batch(conn, sql, params);
		} finally {
			q = null;
			if (affectedRows != null) {
				if (affectedRows.length < params.length) {
					nResult = params.length - affectedRows.length;
				} else if (affectedRows.length == params.length) {
					nResult = affectedRows.length;
				} else {
					nResult = affectedRows.length;
				}
			}
		}
		if (LogUpdateSql.logInfoOrDebug()) {
			LogUpdateSql.logFormatTimeNow(startTime, sql, params,
					(affectedRows != null  ? affectedRows.length : 0));
		}
		return nResult;
	}
	
	/**
	 * @param ds
	 * @param sql
	 * @param in
	 * @param out
	 * @return
	 * @throws SQLException
	 */
	public static boolean callSP(DataSource ds, String sql, Object[] in,
			Object[] out) throws SQLException {
		Connection conn = getConnOfSpringCtx(ds);
		if (conn == null) {
			conn = DataSourceUtils.getConnection(ds);
			try {
				return callSPConn(conn, sql, in, out);
			} finally {
				DataSourceUtils.releaseConnection(conn, ds);
				conn = null;
			}
		} else {
			return callSPConn(conn, sql, in, out);
		}
	}
	
	private static boolean callSPConn(Connection conn, String sql, Object[] in,
			Object[] out) throws SQLException {
		long startTime = System.currentTimeMillis();
		boolean bReturn = true;
		Object[] params = ArrayComm.mergeArray(in, out);
		try {
			CallableStatement cstmt = conn.prepareCall(sql);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					if (in != null && i < in.length) {
						cstmt.setObject(i + 1, params[i]);
					} else {
						cstmt.registerOutParameter(i + 1,
								SqlUtils.getObjectType(params[i]));
					}
				}
			}
			cstmt.execute();
			if (in != null) {
				if (params != null && out != null) {
					for (int j = 0, i = in.length; i < params.length; i++, j++) {
						out[j] = cstmt.getObject(i + 1);
					}
				}
			} else {
				if (params != null && out != null) {
					for (int i = 0; i < params.length; i++) {
						out[i] = cstmt.getObject(i + 1);
					}
				}
			}
		} finally {
			if (LogSpSql.logInfoOrDebug()) {
				LogSpSql.logFormatTimeNow(startTime, sql, params,
					out != null ? out.length : 0);
			}
		}
		return bReturn;
	}
	
	public static <T> T query(DataSource ds, HeadSqlArray ha,
			Object[] params, Integer nPageIdx, Integer nPageSize,
		ResultSetHandler<T> rsh) throws SQLException {
		Connection conn = getConnOfSpringCtx(ds);
		if (conn == null) {
			conn = DataSourceUtils.getConnection(ds);
			try {
				return queryConn(conn, ha, params, nPageIdx, nPageSize, rsh);
			} finally {
				DataSourceUtils.releaseConnection(conn, ds);
				conn = null;
			}
		} else {
			return queryConn(conn, ha, params, nPageIdx, nPageSize, rsh);
		}
	}
	
	private static <T> T queryConn(Connection conn, HeadSqlArray ha,
			Object[] params, Integer nPageIdx, Integer nPageSize,
				ResultSetHandler<T> rsh) throws SQLException {
		return queryConn(conn, ha.getSql(), ha.getCountSql(), params, 
				nPageIdx, nPageSize, rsh);
	}
	
	/**
	 * @param conn
	 * @param ha
	 * @param params
	 * @param nPageIdx
	 * @param nPageSize
	 * @param rsh
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	private static <T> T queryConn(Connection conn, String s, String sqlCount,
			Object[] params, Integer nPageIdx, Integer nPageSize,
				ResultSetHandler<T> rsh) throws SQLException {
		boolean bCountSql = false;
		String sql = null;
		Long nTotalRows = null;
		T t = null;
		Long startTime_count = null;
		Long endTime_count = null;
		long startTime = 0;
		long endTime = 0;
		Object[] paramAll = null;
		QueryRunnerEx qr = new QueryRunnerEx();
		try {
			if (nPageSize == null) {
				sql = s;
				paramAll = params;
			} else {
				if (nPageIdx != null) {
					paramAll = ArrayComm.mergeArray(params, nPageIdx * nPageSize,
						nPageSize);
				} else {
					paramAll = ArrayComm.mergeArray(params, 0 * nPageSize,
							nPageSize);					
				}
			}
			startTime = System.currentTimeMillis();
			if (nPageSize != null && nPageSize > 0) {
				sql = CAutoFitSql.getSubSqlOfPage(conn, s);
			}
			t = qr.query(conn, sql, rsh, paramAll);
			endTime = System.currentTimeMillis();
			if (StringUtils.isNotEmpty(sqlCount)) { // total rows
				//if (rsh instanceof GsonBeanHandler
					// &&((GsonBeanHandler<T>) rsh).getEnableCountSql()) {
					bCountSql = true;
					startTime_count = System.currentTimeMillis();
					nTotalRows = ObjectComm.objectToLong(qr.query(conn,
							sqlCount, new ScalarHandler<Long>(1),
							params));
					endTime_count = System.currentTimeMillis();
				//}
			}
		} finally {
			qr = null;
			if (bCountSql) {
				if (LogSeleteSql.logInfoOrDebug()) {
					LogSeleteSql.logFormatEndTimeNow(startTime_count, endTime_count,
							sqlCount, params);
				}
			}
			if (LogSeleteSql.logInfoOrDebug()) {
				LogSeleteSql.logFormatEndTimeNow(startTime, endTime, sql, paramAll);				
			}
			if (t != null) {
				if (t instanceof ResultDTO && bCountSql) {
					((ResultDTO) t).setLen(nTotalRows);
				}
			}
		}
		return t;
	}

}
