package org.bidtime.dbutils.jdbc.connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;
import org.bidtime.dbutils.jdbc.sql.ArrayUtils;
import org.bidtime.dbutils.jdbc.sql.SqlHolder;
import org.bidtime.dbutils.jdbc.sql.SqlParser;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.bidtime.dbutils.jdbc.sql.xml.parser.HeadSqlArray;
import org.bidtime.dbutils.jdbc.sql.xml.parser.TTableProps;
import org.bidtime.utils.basic.CArrayComm;

public class SqlLoadUtils {

	@SuppressWarnings("rawtypes")
	private static String getSqlOfId(Class clazz, String id) throws SQLException {
		return JsonFieldXmlsLoader.getSqlOfId(clazz, id);
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz, Object[] params)
			throws SQLException {
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String sql = tp.getDeleteSql(tp.getTableName(), params);
		return DbConnection.update(ds, sql, params);
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRow o) throws SQLException {
		if (o != null) {
			int nReturn = 0;
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			List<String> listJsonPk = new ArrayList<String>();
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
					o.getHead(), listJsonPk);
			GsonRow r = o.remain(CArrayComm.listToStringArray(listJsonPk));
			try {
				nReturn = DbConnection.update(ds, sql, r.getData());
			} finally {
				r = null;
				listJsonPk.clear();
				listJsonPk = null;
			}
			return nReturn;
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRow o, String[] heads) throws SQLException {
		if (o != null) {
			int nReturn = 0;
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRow r = o.remain(heads);
			try {
				String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
						r.getHead());
				nReturn = DbConnection.update(ds, sql, r.getData());
			} finally {
				r = null;
			}
			return nReturn;
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRows o) throws SQLException {
		if (o != null) {
			int nReturn = 0;
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			List<String> listJsonPk = new ArrayList<String>();
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
					o.getHead(), listJsonPk);
			GsonRows r = o.remain(CArrayComm.listToStringArray(listJsonPk));
			try {
				nReturn = DbConnection.updateBatch(ds, sql, r.getData());
			} finally {
				r = null;
				listJsonPk.clear();
				listJsonPk = null;
			}
			return nReturn;
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRows o, String[] heads) throws SQLException {
		if (o != null) {
			int nReturn = 0;
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRows r = o.remain(heads);
			try {
				String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
						r.getHead());
				nReturn = DbConnection.updateBatch(ds, sql, r.getData());
			} finally {
				r = null;
			}
			return nReturn;
		} else {
			return 0;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, Object object) throws SQLException {
		if (object != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			List<String> listJsonPk = new ArrayList<String>();
			GsonRow g = null;
			if (object instanceof Map) {
				g = tp.mapToRow((Map)object);
			} else {
				g = tp.clazzToRow(object);
			}
			try {
				String sql = tp.getDeleteSqlOfHead(tp.getTableName(), g.getHead(), listJsonPk);
				g.remain(CArrayComm.listToStringArray(listJsonPk));
				return DbConnection.update(ds, sql, g.getData());
			} catch (Exception e) {
				throw new SQLException("delete:" + e.getMessage());
			} finally {
				g = null;
				listJsonPk.clear();
				listJsonPk = null;
			}
		} else {
			return 0;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, Object object, String[] heads) throws SQLException {
		if (object != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRow g = null;
			try {
				if (object instanceof Map) {
					g = tp.mapToRow((Map)object);
				} else {
					g = tp.clazzToRow(object);
				}
				g.remain(heads);
				String sql = tp.getDeleteSqlOfHead(tp.getTableName(), heads);
				return DbConnection.update(ds, sql, g.getData());
			} catch (Exception e) {
				throw new SQLException("delete:" + e.getMessage());
			} finally {
				g = null;
			}
		} else {
			return 0;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, List list) throws SQLException {
		if (list != null && !list.isEmpty()) {
			GsonRows g = null;
			try {
				TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
				List<String> listJsonPk = new ArrayList<String>();
				try {
					if (list.get(0) instanceof Map) {
						g = tp.mapsToRows((List<Map<String, Object>>)list);
					} else {
						g = tp.clazzToRows(list);
					}
					String sql = tp.getDeleteSqlOfHead(tp.getTableName(), g.getHead(), listJsonPk);
					g.remain(CArrayComm.listToStringArray(listJsonPk));
					return DbConnection.updateBatch(ds, sql, g.getData());
				} finally {
					listJsonPk.clear();
					listJsonPk = null;
				}
			} catch (Exception e) {
				throw new SQLException("delete:" + e.getMessage());
			} finally {
				g = null;
			}
		} else {
			return 0;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, List list, String[] heads) throws SQLException {
		if (list != null && !list.isEmpty()) {
			GsonRows g = null;
			try {
				TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
				if (list.get(0) instanceof Map) {
					g = tp.mapsToRows((List<Map<String, Object>>)list);
				} else {
					g = tp.clazzToRows(list);
				}
				g.remain(tp.getFieldPK());
				String sql = tp.getDeleteSqlOfHead(tp.getTableName(), g.getHead());
				return DbConnection.updateBatch(ds, sql, g.getData());
			} catch (Exception e) {
				throw new SQLException("delete:" + e.getMessage());
			} finally {
				g = null;
			}
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRows g) throws SQLException {
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			List<String> listPkJson = new ArrayList<String>();
			String sql = tp.getUpdateSqlPk(tp.getTableName(), g.getHead(), listPkJson);
			try {
				if (!listPkJson.isEmpty()) {
					g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
				}
			} catch (Exception e) {
				throw new SQLException("update rows:" + e.getMessage());
			}
			return DbConnection.updateBatch(ds, sql, g.getData());
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRows g, String[] heads) throws SQLException {
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			try {
				g.moveToEnd(heads);
			} catch (Exception e) {
				throw new SQLException("update rows:" + e.getMessage());
			}
			String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
			return DbConnection.updateBatch(ds, sql, g.getData());
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int insert(DataSource ds, Class clazz,
			GsonRow g) throws SQLException {
		int nReturn = 0;
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			nReturn = DbConnection.update(ds, sql, g.getData());
		}
		return nReturn;
	}

	@SuppressWarnings("rawtypes")
	public static int insert(DataSource ds, Class clazz,
			GsonRows g) throws SQLException {
		if (g != null) {
			int nReturn = 0;
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			nReturn = DbConnection.updateBatch(ds, sql, g.getData());
			return nReturn;
		} else {
			return 0;
		}
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int insert(DataSource ds, Class clazz, Object object) throws SQLException {
		if (object != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRow g = null;
			try {
				if (object instanceof Map) {
					g = tp.mapToRow((Map)object, true);
				} else {
					g = tp.clazzToRow(object, true);
				}
				if (!tp.isNonePkInc()) {
					g.delHead(tp.getFieldPK());
				}
				String sql = tp.getInsertSql(g, true);
				return DbConnection.update(ds, sql, g.getData());
			} catch (Exception e) {
				throw new SQLException("insert:", e);
			} finally {
				g = null;
			}
		} else {
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int insert(DataSource ds, Class clazz, List list) throws SQLException {
		if (list != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRows g = null;
			try {
				if (list.get(0) instanceof Map) {
					g = tp.mapsToRows((List<Map<String, Object>>)list, true);
				} else {
					g = tp.clazzToRows(list, true);
				}
				if (!tp.isNonePkInc()) {
					g.delHead(tp.getFieldPK());
				}
				String sql = tp.getInsertSql(g, true);
				return DbConnection.updateBatch(ds, sql, g.getData());
			} catch (Exception e) {
				throw new SQLException("insert:", e);
			} finally {
				g = null;
			}
		} else {
			return 0;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int insertForPK(DataSource ds, Class clazz,
			GsonRow g, String pk) throws SQLException {
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			ResultSetHandler rsh = new ScalarHandler<Object>();
			Object objReturn = DbConnection.insert(ds, sql, rsh, g.getData());
			g.autoAddHeadData(pk, objReturn);
			return (objReturn != null) ? 1 : 0;
		} else {
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			GsonRow g) throws SQLException {
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			ResultSetHandler rsh = new ScalarHandler<Object>();
			return (T)DbConnection.insert(ds, sql, rsh, g.getData());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			Object o) throws SQLException {
		if (o != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRow g = null;
			try {
				if (o instanceof Map) {
					g = tp.mapToRow((Map)o, true);
				} else {
					g = tp.clazzToRow(o, true);
				}
				String sql = tp.getInsertSql(g, true);;
				return (T) DbConnection.insert(ds, sql, new ScalarHandler<Object>()
						, g.getData());
			} catch (Exception e) {
				throw new SQLException("update:" + e.getMessage());
			} finally {
				g = null;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			List list) throws SQLException {
		if (list != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRows g = null;
			try {
				if (list instanceof Map) {
					g = tp.mapsToRows((List<Map<String, Object>>)list, true);
				} else {
					g = tp.clazzToRows(list, true);
				}
				String sql = tp.getInsertSql(g, true);
				return (T) DbConnection.insert(ds, sql, new ScalarHandler<Object>()
						, g.getData());
			} catch (Exception e) {
				throw new SQLException("update:" + e.getMessage());
			} finally {
				g = null;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static int insertForPK(DataSource ds, Class clazz,
			GsonRows g, String pk) throws SQLException {
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			Object generatedKeys = DbConnection.insertBatch(ds, sql,
					new ScalarHandler<Object>(), g.getData());
			g.autoAddHeadData(pk, generatedKeys);
			return (generatedKeys != null) ?  1 : 0;
		} else {
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			GsonRows g) throws SQLException {
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			return (T) DbConnection.insertBatch(ds, sql,
					new ScalarHandler<Object>(), g.getData());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int update(DataSource ds, Class clazz,
			Object o) throws SQLException {
		if (o != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRow g = null;
			try {
				if (o instanceof Map) {
					g = tp.mapToRow((Map)o);
				} else {
					g = tp.clazzToRow(o);
				}
				List<String> listPkJson = new ArrayList<String>();
				try {
					String sql = tp.getUpdateSqlPk(tp.getTableName(), g.getHead(), listPkJson);
					if (!listPkJson.isEmpty()) {
						g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
					}
					return DbConnection.update(ds, sql, g.getData());
				} finally {
					listPkJson = null;
				}
			} catch (Exception e) {
				throw new SQLException("update:", e);
			} finally {
				g = null;
			}
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			Object o, String[] heads) throws SQLException {
		if (o != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			GsonRow g = null;
			try {
				g = tp.clazzToRow(o);
				g.moveToEnd(heads);
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
			String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
			return DbConnection.update(ds, sql, g.getData());
		} else {
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int update(DataSource ds, Class clazz,
			List list) throws SQLException {
		if (list != null && !list.isEmpty()) {
			GsonRows g = null;
			try {
				TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
				if (list.get(0) instanceof Map) {
					g = tp.mapsToRows((List<Map<String, Object>>)list);
				} else {
					g = tp.clazzToRows(list);
				}
				List<String> listPkJson = new ArrayList<String>();
				String sql = tp.getUpdateSqlPk(tp.getTableName(), g.getHead(), listPkJson);
				if (!listPkJson.isEmpty()) {
					g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
				}
				return DbConnection.updateBatch(ds, sql, g.getData());
			} catch (Exception e) {
				throw new SQLException("update:" + e.getMessage());
			} finally {
				g = null;
			}
		} else {
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int update(DataSource ds, Class clazz,
			List list, String[] heads) throws SQLException {
		if (list != null && !list.isEmpty()) {
			GsonRows g = null;
			try {
				TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
				if (list.get(0) instanceof Map) {
					g = tp.mapsToRows((List<Map<String, Object>>)list);
				} else {
					g = tp.clazzToRows(list);
				}
				g.moveToEnd(heads);
				String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
				return DbConnection.updateBatch(ds, sql, g.getData());
			} catch (Exception e) {
				throw new SQLException("update:" + e.getMessage());
			}
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRow g) throws SQLException {
		if (g != null) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			List<String> listPkJson = new ArrayList<String>();
			String sql = tp.getUpdateSqlPk(tp.getTableName(), g.getHead(), listPkJson);
			try {
				if (!listPkJson.isEmpty()) {
					g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
				}
			} catch (Exception e) {
				throw new SQLException("update:" + e.getMessage());
			}
			return DbConnection.update(ds, sql, g.getData());
		} else {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRow g, String[] heads) throws SQLException {
		if (g != null && heads != null && heads.length > 0) {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			try {
				g.moveToEnd(heads);
			} catch (Exception e) {
				throw new SQLException("update:" + e.getMessage());
			}
			String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
			return DbConnection.update(ds, sql, g.getData());
		} else {
			return 0;
		}
	}
	
	// --------------------------------------------------------------------------------

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz, String sqlId,
			Object[] params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId);
		return DbConnection.update(ds, sql, params);
	}

	@SuppressWarnings("rawtypes")
	public static int updateBatch(DataSource ds, Class clazz, String sqlId,
			Object[][] params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId);
		return DbConnection.updateBatch(ds, sql, params);
	}

	public static int updateSqlBatch(DataSource ds, String sql,
			Object[][] params) throws SQLException {
		return DbConnection.updateBatch(ds, sql, params);
	}

	public static int updateSqlBatch(DataSource ds, String sql,
			List<Object[]> list) throws SQLException {
		return DbConnection.updateBatch(ds, sql, CArrayComm.listToObjectArray(list));
	}

	public static int updateSqlBatch(DataSource ds, String sql,
			GsonRows rows) throws SQLException {
		return DbConnection.updateBatch(ds, sql, rows.getData());
	}

	public static int updateSqlBatch(DataSource ds, String sql,
			GsonRow row) throws SQLException {
		return DbConnection.update(ds, sql, row.getData());
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz, String sqlId,
			Map<String, ?> params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId);
		SqlHolder holder = SqlParser.parse(sql, params);
		return DbConnection.update(ds, holder.getSql(),
				holder.getObjectArray());
	}

	public static int updateSql(DataSource ds, String sql, Object[] params) throws SQLException {
		return DbConnection.update(ds, sql, params);
	}

	public static int updateSql(DataSource ds, String sql) throws SQLException {
		return DbConnection.update(ds, sql, (Object[])null);
	}
	
	//call sp

	@SuppressWarnings("rawtypes")
	public static boolean callSP(DataSource ds, Class clazz, String sqlId,
			Object[] in, Object[] out) throws SQLException {
		String sProc = getSqlOfId(clazz, sqlId);
		return DbConnection.callSP(ds, sProc, in, out);
	}

	@SuppressWarnings("rawtypes")
	public static boolean callSpIn(DataSource ds, Class clazz, String sqlId,
			Object[] in) throws SQLException {
		String sProc = getSqlOfId(clazz, sqlId);
		return DbConnection.callSP(ds, sProc, in, null);
	}

	@SuppressWarnings("rawtypes")
	public static boolean callSpOut(DataSource ds, Class clazz,
			String sqlId, Object[] out) throws SQLException {
		String sProc = getSqlOfId(clazz, sqlId);
		return DbConnection.callSP(ds, sProc, null, out);
	}
	
	@SuppressWarnings("rawtypes")
	private static boolean isCountSql(ResultSetHandler rsh) {
		if (rsh instanceof ResultSetDTOHandler 
				&& ((ResultSetDTOHandler) rsh).isCountSql()) {
			return true;
		} else {
			return false;
		}
	}

	//query
	public static <T> T queryOne(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return query(ds, sql, rsh, params, 0, 1);
	}
	
	public static <T> T query(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return query(ds, sql, rsh, params, null, null);
	}

	public static <T> T query(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params, Integer nPageIdx, Integer nPageSize) throws SQLException {
		SqlHolder holder = SqlParser.parse(sql, params);
		String countSql = null;
		if (isCountSql(rsh)) {
			SqlHolder holderCount = SqlParser
					.parse(SqlUtils.getCountSql(sql), params);
			countSql = holderCount.getSql();
		}
		HeadSqlArray a = new HeadSqlArray(holder.getSql(), countSql);
		return DbConnection.query(ds, a, holder.getObjectArray(),
				nPageIdx, nPageSize, rsh);
	}
	
	// queryEx

	@SuppressWarnings("rawtypes")
	public static <T> T queryEx(DataSource ds, Class clazz, String sqlId,
			Map<String, ?> params, Integer nPageIdx, Integer nPageSize,
			ResultSetHandler<T> rsh) throws SQLException {
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		HeadSqlArray h = tp.getHeadSqlArrayOfId(sqlId);
		SqlHolder holder = SqlParser.parse(h.getSql(), params);
		h.setSql(holder.getSql());
		if (isCountSql(rsh)) {
			String countSql = null;
			if (StringUtils.isNotEmpty(h.getCountSql()) ) {
				SqlHolder holderCount = SqlParser
					.parse(h.getCountSql(), params);
				countSql = holderCount.getSql();
			} else {
				countSql = SqlUtils.getCountSql(holder.getSql());
			}
			h.setCountSql(countSql);
		}
		return DbConnection.query(ds, h, holder.getObjectArray(),
				nPageIdx, nPageSize, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryEx(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return queryEx(ds, clazz, sqlId, params, null, null, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryExOne(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return queryEx(ds, clazz, sqlId, params, 0, 1, rsh);
	}
	
}