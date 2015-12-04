package org.bidtime.dbutils.jdbc.connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.PropAdapt;
import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.dao.PKCallback;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetDTOHandler;
import org.bidtime.dbutils.jdbc.sql.ArrayUtils;
import org.bidtime.dbutils.jdbc.sql.SqlParser;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.bidtime.dbutils.jdbc.sql.xml.parser.HeadSqlArray;
import org.bidtime.dbutils.jdbc.sql.xml.parser.TTableProps;
import org.bidtime.utils.basic.ArrayComm;

public class SqlLoadUtils {

	@SuppressWarnings("rawtypes")
	private static String getSqlOfId(Class clazz, String id) throws SQLException {
		return JsonFieldXmlsLoader.getSqlOfId(clazz, id);
	}

	//delete
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
		if (o == null) {
			return 0;
		}
		GsonRow r = null;
		List<String> listJsonPk = new ArrayList<String>();
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		try {
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
					o.getHead(), listJsonPk);
			r = o.remain(ArrayComm.listToStringArray(listJsonPk));
			return DbConnection.update(ds, sql, r.getData());
		} finally {
			r = null;
			listJsonPk.clear();
			listJsonPk = null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRow o, String[] heads) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRow r = o.remain(heads);
		try {
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
					r.getHead());
			return DbConnection.update(ds, sql, r.getData());
		} finally {
			r = null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRows o) throws SQLException {
		if (o == null) {
			return 0;
		}
		GsonRows r = null;
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		List<String> listJsonPk = new ArrayList<String>();
		try {
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
					o.getHead(), listJsonPk);
			r = o.remain(ArrayComm.listToStringArray(listJsonPk));
			return DbConnection.updateBatch(ds, sql, r.getData());
		} finally {
			r = null;
			listJsonPk.clear();
			listJsonPk = null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRows o, String[] heads) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRows r = o.remain(heads);
		try {
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(),
					r.getHead());
			return DbConnection.updateBatch(ds, sql, r.getData());
		} finally {
			r = null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, Object object) throws SQLException {
		if (object == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		List<String> listJsonPk = new ArrayList<String>();
		GsonRow g = null;
		try {
			if (object instanceof Map) {
				g = tp.mapToRow((Map)object);
			} else {
				g = tp.clazzToRow(object, PropAdapt.NOTNULL);
			}
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(), g.getHead(), listJsonPk);
			g.remain(ArrayComm.listToStringArray(listJsonPk));
			return DbConnection.update(ds, sql, g.getData());
		} finally {
			g = null;
			listJsonPk.clear();
			listJsonPk = null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, Object object, String[] heads) throws SQLException {
		if (object == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRow g = null;
		try {
			if (object instanceof Map) {
				g = tp.mapToRow((Map)object);
			} else {
				g = tp.clazzToRow(object, PropAdapt.NOTNULL);
			}
			g.remain(heads);
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(), heads);
			return DbConnection.update(ds, sql, g.getData());
		} finally {
			g = null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, List list) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		GsonRows g = null;
		List<String> listJsonPk = new ArrayList<String>();
		try {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (list.get(0) instanceof Map) {
				g = tp.mapsToRows((List<Map<String, Object>>)list);
			} else {
				g = tp.clazzToRows(list, PropAdapt.NOTNULL);
			}
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(), g.getHead(), listJsonPk);
			g.remain(ArrayComm.listToStringArray(listJsonPk));
			return DbConnection.updateBatch(ds, sql, g.getData());
		} finally {
			g = null;
			listJsonPk.clear();
			listJsonPk = null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, List list, String[] heads) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		GsonRows g = null;
		try {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (list.get(0) instanceof Map) {
				g = tp.mapsToRows((List<Map<String, Object>>)list);
			} else {
				g = tp.clazzToRows(list, PropAdapt.NOTNULL);
			}
			g.remain(tp.getFieldPK());
			String sql = tp.getDeleteSqlOfHead(tp.getTableName(), g.getHead());
			return DbConnection.updateBatch(ds, sql, g.getData());
		} finally {
			g = null;
		}
	}
	
	//insert
	@SuppressWarnings("rawtypes")
	public static int insert(DataSource ds, Class clazz,
			GsonRow g) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		if (!tp.isNonePkInc()) {
			g.delHead(tp.getFieldPK());
		}
		String sql = tp.getInsertSql(g, true);
		return DbConnection.update(ds, sql, g.getData());
	}

	@SuppressWarnings("rawtypes")
	public static int insert(DataSource ds, Class clazz,
			GsonRows g) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		if (!tp.isNonePkInc()) {
			g.delHead(tp.getFieldPK());
		}
		String sql = tp.getInsertSql(g, true);
		return DbConnection.updateBatch(ds, sql, g.getData());
	}
	
	@SuppressWarnings({ "rawtypes"})
	public static int insert(DataSource ds, Class clazz, Object object) throws SQLException {
		return insert(ds, clazz, object, PropAdapt.NOTNULL);
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int insert(DataSource ds, Class clazz, Object object, PropAdapt pa) throws SQLException {
		if (object == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRow g = null;
		try {
			if (object instanceof Map) {
				g = tp.mapToRow((Map)object, true);
			} else {
				g = tp.clazzToRow(object, true, pa);
			}
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			return DbConnection.update(ds, sql, g.getData());
		} finally {
			g = null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static int insert(DataSource ds, Class clazz, List list) throws SQLException {
		return insert(ds, clazz, list, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int insert(DataSource ds, Class clazz, List list, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRows g = null;
		try {
			if (list.get(0) instanceof Map) {
				g = tp.mapsToRows((List<Map<String, Object>>)list, true);
			} else {
				g = tp.clazzToRows(list, true, pa);
			}
			if (!tp.isNonePkInc()) {
				g.delHead(tp.getFieldPK());
			}
			String sql = tp.getInsertSql(g, true);
			return DbConnection.updateBatch(ds, sql, g.getData());
		} finally {
			g = null;
		}
	}
	
	//insertForPK
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int insertForPK(DataSource ds, Class clazz,
			GsonRow g, String pk) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		if (!tp.isNonePkInc()) {
			g.delHead(tp.getFieldPK());
		}
		String sql = tp.getInsertSql(g, true);
		ResultSetHandler rsh = new ScalarHandler<Object>();
		Object objReturn = DbConnection.insert(ds, sql, rsh, g.getData());
		g.autoAddHeadData(pk, objReturn);
		return (objReturn != null) ? 1 : 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			GsonRow g) throws SQLException {
		if (g == null) {
			return null;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		if (!tp.isNonePkInc()) {
			g.delHead(tp.getFieldPK());
		}
		String sql = tp.getInsertSql(g, true);
		ResultSetHandler rsh = new ScalarHandler<Object>();
		return (T)DbConnection.insert(ds, sql, rsh, g.getData());
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			Object o) throws SQLException {
		return insertForPK(ds, clazz, o, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			Object o, PropAdapt pa) throws SQLException {
		if (o == null) {
			return null;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRow g = null;
		try {
			if (o instanceof Map) {
				g = tp.mapToRow((Map)o, true);
			} else {
				g = tp.clazzToRow(o, true, pa);
			}
			String sql = tp.getInsertSql(g, true);;
			return (T) DbConnection.insert(ds, sql, new ScalarHandler<Object>()
					, g.getData());
		} finally {
			g = null;
		}
	}	

	@SuppressWarnings({ "rawtypes" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			Object o, PKCallback cb) throws SQLException {
		return insertForPK(ds, clazz, o, PropAdapt.NOTNULL, cb);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			Object o, PropAdapt pa, PKCallback cb) throws SQLException {
		T t = insertForPK(ds, clazz, o, pa);
		if (t != null) {
			Object u = cb.getIt(t);
			int n = SqlLoadUtils.update(ds, clazz, u);
			cb.setResult(n);
		}
		return t;
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			List list) throws SQLException {
		return insertForPK(ds, clazz, list, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			List list, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return null;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRows g = null;
		try {
			if (list.get(0) instanceof Map) {
				g = tp.mapsToRows((List<Map<String, Object>>)list, true);
			} else {
				g = tp.clazzToRows(list, true, pa);
			}
			String sql = tp.getInsertSql(g, true);
			return (T) DbConnection.insert(ds, sql, new ScalarHandler<Object>()
					, g.getData());
		} finally {
			g = null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static int insertForPK(DataSource ds, Class clazz,
			GsonRows g, String pk) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		if (!tp.isNonePkInc()) {
			g.delHead(tp.getFieldPK());
		}
		String sql = tp.getInsertSql(g, true);
		Object generatedKeys = DbConnection.insertBatch(ds, sql,
				new ScalarHandler<Object>(), g.getData());
		g.autoAddHeadData(pk, generatedKeys);
		return (generatedKeys != null) ?  1 : 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T insertForPK(DataSource ds, Class clazz,
			GsonRows g) throws SQLException {
		if (g == null) {
			return null;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		if (!tp.isNonePkInc()) {
			g.delHead(tp.getFieldPK());
		}
		String sql = tp.getInsertSql(g, true);
		return (T) DbConnection.insertBatch(ds, sql,
				new ScalarHandler<Object>(), g.getData());
	}
	
	// update
	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRows g) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		List<String> listPkJson = new ArrayList<String>();
		String sql = tp.getUpdateSqlOfHead(tp.getTableName(), g.getHead(), listPkJson);
		if (!listPkJson.isEmpty()) {
			g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
		}
		return DbConnection.updateBatch(ds, sql, g.getData());
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRows g, String[] heads) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		g.moveToEnd(heads);
		String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
		return DbConnection.updateBatch(ds, sql, g.getData());
	}

	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			Object o) throws SQLException {
		return update(ds, clazz, o, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int update(DataSource ds, Class clazz,
			Object o, PropAdapt pa) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		List<String> listPkJson = new ArrayList<String>();
		GsonRow g = null;
		try {
			if (o instanceof Map) {
				g = tp.mapToRow((Map)o);
			} else {
				g = tp.clazzToRow(o, pa);
			}
			String sql = tp.getUpdateSqlOfHead(tp.getTableName(), g.getHead(), listPkJson);
			if (!listPkJson.isEmpty()) {
				g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
			}
			return DbConnection.update(ds, sql, g.getData());
		} finally {
			g = null;
			listPkJson.clear();
			listPkJson = null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			Object o, String[] heads) throws SQLException {
		return update(ds, clazz, o, heads, PropAdapt.FULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int update(DataSource ds, Class clazz,
			Object o, String[] heads, PropAdapt pa) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		GsonRow g = null;
		if (o instanceof Map) {
			g = tp.mapToRow((Map)o);
		} else {
			g = tp.clazzToRow(o, pa);
		}
		g.moveToEnd(heads);
		String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
		return DbConnection.update(ds, sql, g.getData());
	}

	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			List list) throws SQLException {
		return update(ds, clazz, list, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int update(DataSource ds, Class clazz,
			List list, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		GsonRows g = null;
		try {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (list.get(0) instanceof Map) {
				g = tp.mapsToRows((List<Map<String, Object>>)list);
			} else {
				g = tp.clazzToRows(list, pa);
			}
			List<String> listPkJson = new ArrayList<String>();
			String sql = tp.getUpdateSqlOfHead(tp.getTableName(), g.getHead(), listPkJson);
			if (!listPkJson.isEmpty()) {
				g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
			}
			return DbConnection.updateBatch(ds, sql, g.getData());
		} finally {
			g = null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			List list, String[] heads) throws SQLException {
		return update(ds, clazz, list, heads, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int update(DataSource ds, Class clazz,
			List list, String[] heads, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		GsonRows g = null;
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		if (list.get(0) instanceof Map) {
			g = tp.mapsToRows((List<Map<String, Object>>)list);
		} else {
			g = tp.clazzToRows(list, pa);
		}
		g.moveToEnd(heads);
		String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
		return DbConnection.updateBatch(ds, sql, g.getData());
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRow g) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		List<String> listPkJson = new ArrayList<String>();
		String sql = tp.getUpdateSqlOfHead(tp.getTableName(), g.getHead(), listPkJson);
		if (!listPkJson.isEmpty()) {
			g.moveToEnd(ArrayUtils.listToStringArray(listPkJson));
		}
		return DbConnection.update(ds, sql, g.getData());
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRow g, String[] heads) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		g.moveToEnd(heads);
		String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
		return DbConnection.update(ds, sql, g.getData());
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz, String sqlId,
			Object[] params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId);
		return DbConnection.update(ds, sql, params);
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz, String sqlId,
			Map<String, ?> params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId);
		List<Object> paramList = new ArrayList<Object>();
		String finalSql = SqlParser.parse(sql, params, paramList);
		return DbConnection.update(ds, finalSql,
				paramList.toArray());
	}

	//updateBatch
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
		return DbConnection.updateBatch(ds, sql, ArrayComm.listToObjectArray(list));
	}

	public static int updateSqlBatch(DataSource ds, String sql,
			GsonRows rows) throws SQLException {
		return DbConnection.updateBatch(ds, sql, rows.getData());
	}

	public static int updateSqlBatch(DataSource ds, String sql,
			GsonRow row) throws SQLException {
		return DbConnection.update(ds, sql, row.getData());
	}

	//update sql
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

	//query

	@SuppressWarnings("rawtypes")
	private static <T> T query(DataSource ds, HeadSqlArray h,
			Map<String, ?> params, Integer nPageIdx, Integer nPageSize,
			ResultSetHandler<T> rsh) throws SQLException {
		List<Object> paramList = new ArrayList<Object>();
		h.setSql(SqlParser.parse(h.getSql(), params, paramList));
		if (rsh instanceof ResultSetDTOHandler 
				&& ((ResultSetDTOHandler) rsh).isCountSql()) {
			String countSql = null;
			if (StringUtils.isNotEmpty(h.getCountSql()) ) {
				countSql = SqlParser
					.parseCount(h.getCountSql(), params);
			} else {
				countSql = SqlUtils.getCountSql(h.getSql());
			}
			h.setCountSql(countSql);
		}
		return DbConnection.query(ds, h, paramList.toArray(),
				nPageIdx, nPageSize, rsh);
	}
	
	public static <T> T query(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return query(ds, sql, rsh, params, null, null);
	}

	public static <T> T query(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params, Integer nPageIdx, Integer nPageSize) throws SQLException {
		HeadSqlArray ha = new HeadSqlArray(sql, null);
		return query(ds, ha, params, nPageIdx, nPageSize, rsh);
	}
	
	public static <T> T queryOne(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return query(ds, sql, rsh, params, 0, 1);
	}
	
	// queryEx

	@SuppressWarnings("rawtypes")
	public static <T> T queryEx(DataSource ds, Class clazz, String sqlId,
			Map<String, ?> params, Integer nPageIdx, Integer nPageSize,
			ResultSetHandler<T> rsh) throws SQLException {
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		HeadSqlArray h = tp.getHeadSqlArrayOfId(sqlId);
		return query(ds, h, params, nPageIdx, nPageSize, rsh);
	}
	
	@SuppressWarnings("rawtypes")
	private static boolean isJavaSimpleClazz(Object o) {
		boolean result = false;
		if (o == null) {		//要转换成json.null
			result = true;
		} else if (o instanceof Map) {
			result = false;
		} else if (o instanceof List && !((List)(o)).isEmpty()) {
			Object m = ((List)o).get(0);
			if (m != null && m instanceof Map) {
				result = false;
			} else {
				result = true;
			}
		} else if (o instanceof String || o instanceof Number ||
			o instanceof StringBuilder || o instanceof StringBuffer ||
			o instanceof Boolean || o instanceof Appendable) {
			result = true;
		} else if (o instanceof Object[]) {
			return true;
		} else if (o instanceof java.util.Date) {
			result = true;
		} else if (o instanceof Set || o instanceof Queue || o instanceof List ||
			o instanceof Character ||
			o instanceof Math || o instanceof Enum) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> T queryExByPK(DataSource ds, Class clazz, String sqlId,
			Object o, Integer nPageIdx, Integer nPageSize,
			ResultSetHandler<T> rsh) throws SQLException {
		if ( !isJavaSimpleClazz(o) ) {
			throw new SQLException("primary key type is error");
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		HeadSqlArray h = tp.getHeadSqlArrayOfId(sqlId);
		Map<String, Object> params = SqlParser.getMapOfFieldPK(h.getSql(), tp.getSetPk());
		if (params == null || params.isEmpty()) {
			throw new SQLException("primary key error");
		} else if (params.size() == 1) {
			for (Map.Entry<String, Object> entry:params.entrySet()) {
				entry.setValue(o);
			}
		} else {
			if (o instanceof Object[]) {
				if (params.size() != ((Object[])o).length) {
					throw new SQLException("pk params error: length is not equal");
				}
				int i = 0;
				for (Map.Entry<String, Object> entry:params.entrySet()) {
					entry.setValue(((Object[])o)[i]);
					i ++;
				}
			} else if (o instanceof Iterable) {
//				if (params.size() != ((Iterable)o).) {
					throw new SQLException("pk params error: type not support.");
//				}
//				int i = 0;
//				for (Map.Entry<String, Object> entry:params.entrySet()) {
//					entry.setValue(((Object[])o)[i]);
//					i ++;
//				}
			} else if (o instanceof List) {
				if (params.size() != ((List)o).size()) {
					throw new SQLException("pk params error: size is not equal");
				}
				int i = 0;
				for (Map.Entry<String, Object> entry:params.entrySet()) {
					entry.setValue(((List)o).get(i));
					i ++;
				}
			} else {
				throw new SQLException("pk params error: object[]."
					+ o.getClass().getSimpleName());
			}
		}
		return query(ds, h, params, nPageIdx, nPageSize, rsh);
	}
	
//	@SuppressWarnings("rawtypes")
//	public static <T> T queryEx(DataSource ds, Class clazz,
//			Map<String, ?> params, Integer nPageIdx, Integer nPageSize,
//			ResultSetHandler<T> rsh) throws SQLException {
//		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
//		String sql = tp.getSelectSql();
//		return query(ds, sql, rsh, params, nPageIdx, nPageSize);
//	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryEx(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return queryEx(ds, clazz, sqlId, params, null, null, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryExByPK(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Object o) throws SQLException {
		return queryExByPK(ds, clazz, sqlId, o, null, null, rsh);
	}

//	@SuppressWarnings("rawtypes")
//	public static <T> T queryEx(DataSource ds, Class clazz, ResultSetHandler<T> rsh,
//			Map<String, ?> params) throws SQLException {
//		return queryEx(ds, clazz, params, null, null, rsh);
//	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryExOne(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return queryEx(ds, clazz, sqlId, params, 0, 1, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryExOneByPK(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Object o) throws SQLException {
		return queryExByPK(ds, clazz, sqlId, o, 0, 1, rsh);
	}

//	@SuppressWarnings("rawtypes")
//	public static <T> T queryExOne(DataSource ds, Class clazz, ResultSetHandler<T> rsh,
//			Map<String, ?> params) throws SQLException {
//		return queryEx(ds, clazz, params, 0, 1, rsh);
//	}
	
}