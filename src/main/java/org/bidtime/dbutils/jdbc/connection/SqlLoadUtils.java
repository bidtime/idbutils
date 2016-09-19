package org.bidtime.dbutils.jdbc.connection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.bidtime.dbutils.gson.PropAdapt;
import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.dao.PKCallback;
import org.bidtime.dbutils.jdbc.dao.SQLCallback;
import org.bidtime.dbutils.jdbc.dialect.CAutoFitSql;
import org.bidtime.dbutils.jdbc.sql.SqlParser;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.bidtime.dbutils.jdbc.sql.xml.parser.TTableProps;
import org.bidtime.utils.basic.ArrayComm;

public class SqlLoadUtils {

//	@SuppressWarnings("rawtypes")
//	private static String getSqlOfId(Class clazz, String id) throws SQLException {
//		return JsonFieldXmlsLoader.getSqlOfId(clazz, id);
//	}

	@SuppressWarnings("rawtypes")
	private static String getSqlOfId(Class clazz, String id, String colId) throws SQLException {
		return JsonFieldXmlsLoader.getSqlOfId(clazz, id, colId);
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
	public static int delete(DataSource ds, Class clazz, String fld, Object[] params)
			throws SQLException {
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String sql = tp.getDeleteSql(tp.getTableName(), new String[]{fld}, params);
		return DbConnection.update(ds, sql, params);
	}
	
	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz, String[] flds, Object[] params)
			throws SQLException {
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String sql = tp.getDeleteSql(tp.getTableName(), flds, params);
		return DbConnection.update(ds, sql, params);
	}
	
	public static int delete(DataSource ds, String tblName, String[] flds, Object[] params)
			throws SQLException {
		String sql = SqlUtils.getDeleteSql(tblName, flds, params);
		return DbConnection.update(ds, sql, params);
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz, GsonRow o) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		return delete(ds, tp.getTableName(), o, tp.getFieldPK());
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRow o, String[] heads) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		return delete(ds, tp.getTableName(), o, heads);
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRows o) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		return delete(ds, tp.getTableName(), o, tp.getFieldPK());
	}

	private static int delete(DataSource ds, String tblName,
			GsonRow o, String[] heads) throws SQLException {
		if (o == null) {
			return 0;
		}
		GsonRow r = o.remain(heads);
		try {
			if (r == null || !r.isExistsData()) {
				return 0;
			}
			String sql = SqlUtils.getDeleteSql(tblName, heads);
			return DbConnection.update(ds, sql, r.getData());
		} finally {
			r = null;
		}
	}

	private static int delete(DataSource ds, String tblName,
			GsonRows o, String[] heads) throws SQLException {
		if (o == null) {
			return 0;
		}
		GsonRows r = o.remain(heads);
		try {
			if (r == null || !r.isExistsData()) {
				return 0;
			}
			String sql = SqlUtils.getDeleteSql(tblName, heads);
			return DbConnection.updateBatch(ds, sql, r.getData());
		} finally {
			r = null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int delete(DataSource ds, Class clazz,
			GsonRows o, String[] heads) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		return delete(ds, tp.getTableName(), o, heads);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, Object object) throws SQLException {
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
			return delete(ds, tp.getTableName(), g, tp.getFieldPK());
		} finally {
			g = null;
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
			return delete(ds, tp.getTableName(), g, heads);
		} finally {
			g = null;
		}
	}
	
    private static boolean isJavaSimpleClazz(Class<?> type) {
        boolean result = false;
        if (type == null) {					//要转换成json.null
            result = false;
        } else if (type.equals(String.class) || type.equals(Number.class) ||
                type.equals(StringBuilder.class) || type.equals(StringBuffer.class) ||
                type.equals(Boolean.class) || type.equals(Appendable.class)) {
            result = true;
        } else if (type.equals(Long.class) || type.equals(Integer.class)
                || type.equals(Short.class) || type.equals(Number.class)
                || type.equals(BigInteger.class) || type.equals(BigDecimal.class)) {
            result = true;
        } else if (type.equals(java.util.Date.class)) {
            result = true;
        } else if (type.equals(Set.class) || type.equals(Queue.class) ||
                type.equals(Character.class) ||
                type.equals(Math.class) || type.equals(Enum.class)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int delete(DataSource ds, Class clazz, List list) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		Object o = list.get(0);
		if (o == null) {
			return 0;
		} else if (isJavaSimpleClazz(o.getClass())) {
			return delete(ds, clazz, list.toArray());
		}
		GsonRows g = null;
		List<String> listJsonPk = new ArrayList<String>();
		try {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (o instanceof Map) {
				g = tp.mapsToRows((List<Map<String, Object>>)list);
			} else {
				g = tp.clazzToRows(list, PropAdapt.NOTNULL);
			}
			return delete(ds, tp.getTableName(), g, tp.getFieldPK());
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
		Object o = list.get(0);
		if (o == null) {
			return 0;
		} else if (isJavaSimpleClazz(o.getClass())) {
			return delete(ds, clazz, heads, list.toArray());
		}
		GsonRows g = null;
		try {
			TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
			if (list.get(0) instanceof Map) {
				g = tp.mapsToRows((List<Map<String, Object>>)list);
			} else {
				g = tp.clazzToRows(list, PropAdapt.NOTNULL);
			}
			return delete(ds, tp.getTableName(), g, heads);
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

	@SuppressWarnings({ "rawtypes"})
	public static int insertIgnore(DataSource ds, Class clazz, Object object) throws SQLException {
		return insertIgnore(ds, clazz, object, PropAdapt.NOTNULL);
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
			String sql = tp.getInsertSql(g, true);
			return DbConnection.update(ds, sql, g.getData());
		} finally {
			g = null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int insertIgnore(DataSource ds, Class clazz, Object object, PropAdapt pa) throws SQLException {
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
			return DbConnection.update(ds, tp, g, 
					new SQLCallback<TTableProps, GsonRow, Connection>(){
	            @Override  
	            public String getSql(TTableProps tp, GsonRow g, Connection c) throws SQLException {
	            	String insSql = CAutoFitSql.getInsertIgnore(c);
		        	return tp.getInsertSql(g, true, insSql);
		        }  
			});
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
		int n = 0;
		for (Object o : list) {
			GsonRow g = null;
			try {
				if (o instanceof Map) {
					g = tp.mapToRow((Map)o, true);
				} else {
					g = tp.clazzToRow(o, true, pa);
				}
				String sql = tp.getInsertSql(g, true);
				n += DbConnection.update(ds, sql, g.getData());
			} finally {
				g = null;
			}
		}
		return n;
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int insertIgnore(DataSource ds, Class clazz, List list, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		int n = 0;
		for (Object o : list) {
			GsonRow g = null;
			try {
				if (o instanceof Map) {
					g = tp.mapToRow((Map)o, true);
				} else {
					g = tp.clazzToRow(o, true, pa);
				}
				n += DbConnection.update(ds, tp, g, 
						new SQLCallback<TTableProps, GsonRow, Connection>(){
		            @Override  
		            public String getSql(TTableProps tp, GsonRow g, Connection c) throws SQLException {
		            	String insSql = CAutoFitSql.getInsertIgnore(c);
			        	return tp.getInsertSql(g, true, insSql);
			        }  
				});
			} finally {
				g = null;
			}
		}
		return n;
	}

	@SuppressWarnings({ "rawtypes" })
	public static int insertBatch(DataSource ds, Class clazz, List list) throws SQLException {
		return insertBatch(ds, clazz, list, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int insertBatch(DataSource ds, Class clazz, List list, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		int n = 0;
		GsonRows rows = null;
		try {
			if (list.get(0) instanceof Map) {
				rows = tp.mapsToRows((List<Map<String, Object>>)list, true);
			} else {
				rows = tp.clazzToRows(list, true, pa);
			}
			String sql = tp.getInsertSql(rows, true);
			n = DbConnection.updateBatch(ds, sql, rows.getData());
		} finally {
			rows = null;
		}
		return n;
	}

	@SuppressWarnings({ "rawtypes" })
	public static int updateBatch(DataSource ds, Class clazz, List list) throws SQLException {
		return updateBatch(ds, clazz, list, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static int updateBatch(DataSource ds, Class clazz, List list, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		int n = 0;
		GsonRows rows = null;
		try {
			String[] pks = tp.getFieldPK();
			if (list.get(0) instanceof Map) {
				rows = tp.mapsToRows((List<Map<String, Object>>)list, false);
			} else {
				rows = tp.clazzToRows(list, false, pa);
			}
			rows.moveToEnd(pks);
			String sql = tp.getUpdateSqlHead(tp.getTableName(), rows.getHead(), pks);
			n = DbConnection.updateBatch(ds, sql, rows.getData());
		} finally {
			rows = null;
		}
		return n;
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
			return (T) DbConnection.insert(ds, sql, new ScalarHandler<T>()
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
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static <T> T insertForPK(DataSource ds, Class clazz,
//			List list, PropAdapt pa) throws SQLException {
//		if (list == null || list.isEmpty()) {
//			return null;
//		}
//		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
//		GsonRows g = null;
//		try {
//			if (list.get(0) instanceof Map) {
//				g = tp.mapsToRows((List<Map<String, Object>>)list, true);
//			} else {
//				g = tp.clazzToRows(list, true, pa);
//			}
//			String sql = tp.getInsertSql(g, true);
//			return (T) DbConnection.insert(ds, sql, new ScalarHandler<Object>()
//					, g.getData());
//		} finally {
//			g = null;
//		}
//	}

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
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String[] heads = tp.getFieldPK();
		return update(ds, clazz, g, heads);
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
	public static int update(DataSource ds, Class clazz, Object o) throws SQLException {
		return update(ds, clazz, o, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			Object o, PropAdapt pa) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String[] heads = tp.getFieldPK();
		return update(ds, tp, o, heads, pa);
	}

	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			Object o, String[] heads) throws SQLException {
		return update(ds, clazz, o, heads, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			Object o, String[] heads, PropAdapt pa) throws SQLException {
		if (o == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		return update(ds, tp, o, heads, pa);
	}

	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			List list) throws SQLException {
		return update(ds, clazz, list, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			List list, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String[] heads = tp.getFieldPK();
		int n = 0;
		for (Object o : list) {
			n += update(ds, tp, o, heads, pa);
		}
		return n;
	}

	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			List list, String[] heads) throws SQLException {
		return update(ds, clazz, list, heads, PropAdapt.NOTNULL);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static int update(DataSource ds, Class clazz,
			List list, String[] heads, PropAdapt pa) throws SQLException {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		int n = 0;
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		for (Object o : list) {
			n += update(ds, tp, o, heads, pa);
		}
		return n;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int update(DataSource ds, TTableProps tp,
			Object o, String[] heads, PropAdapt pa) throws SQLException {
		if (o == null) {
			return 0;
		}
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

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz, GsonRow g) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String heads[] = tp.getFieldPK();
		return update(ds, tp, g, heads, PropAdapt.NOTNULL);
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz,
			GsonRow g, String[] heads) throws SQLException {
		if (g == null) {
			return 0;
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		return update(ds, tp, g, heads, PropAdapt.NOTNULL);
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz, String sqlId,
			Object[] params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId, null);
		return DbConnection.update(ds, sql, params);
	}

	@SuppressWarnings("rawtypes")
	public static int update(DataSource ds, Class clazz, String sqlId,
			Map<String, ?> params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId, null);
		List<Object> paramList = new ArrayList<Object>();
		String finalSql = SqlParser.parse(sql, params, paramList);
		return DbConnection.update(ds, finalSql, paramList.toArray());
	}

	//updateBatch
	@SuppressWarnings("rawtypes")
	public static int updateBatch(DataSource ds, Class clazz, String sqlId,
			Object[][] params) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId, null);
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
		String sProc = getSqlOfId(clazz, sqlId, null);
		return DbConnection.callSP(ds, sProc, in, out);
	}

	@SuppressWarnings("rawtypes")
	public static boolean callSpIn(DataSource ds, Class clazz, String sqlId,
			Object[] in) throws SQLException {
		String sProc = getSqlOfId(clazz, sqlId, null);
		return DbConnection.callSP(ds, sProc, in, null);
	}

	@SuppressWarnings("rawtypes")
	public static boolean callSpOut(DataSource ds, Class clazz,
			String sqlId, Object[] out) throws SQLException {
		String sProc = getSqlOfId(clazz, sqlId, null);
		return DbConnection.callSP(ds, sProc, null, out);
	}

	//query
	
	public static <T> T querySql(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return querySql(ds, sql, rsh, params, null, null);
	}

	public static <T> T querySql(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params, Integer pageIdx, Integer pageSize) throws SQLException {
		List<Object> paramList = new ArrayList<Object>();
		sql = SqlParser.parse(sql, params, paramList);
		return DbConnection.query(ds, sql, paramList.toArray(),
				pageIdx, pageSize, rsh);
	}
	
	public static <T> T querySqlOne(DataSource ds, String sql, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return querySql(ds, sql, rsh, params, 0, 1);
	}
	
	// query

	@SuppressWarnings("rawtypes")
	public static <T> T query(DataSource ds, Class clazz, String sqlId, String colId,
			Map<String, ?> params, Integer pageIdx, Integer pageSize, 
			ResultSetHandler<T> rsh) throws SQLException {
		String sql = getSqlOfId(clazz, sqlId, colId);
		return querySql(ds, sql, rsh, params, pageIdx, pageSize);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T query(DataSource ds, Class clazz, String sqlId, String colId,
			ResultSetHandler<T> rsh, Map<String, ?> params) throws SQLException {
		return query(ds, clazz, sqlId, colId, params, null, null, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryOne(DataSource ds, Class clazz, String sqlId, String colId,
			ResultSetHandler<T> rsh, Map<String, ?> params) throws SQLException {
		return query(ds, clazz, sqlId, colId, params, 0, 1, rsh);
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T query(DataSource ds, Class clazz, String sqlId, 
			Map<String, ?> params, Integer pageIdx, Integer pageSize, 
			ResultSetHandler<T> rsh) throws SQLException {
		return query(ds, clazz, sqlId, null, params, pageIdx, pageSize, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T query(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return query(ds, clazz, sqlId, params, null, null, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryOne(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Map<String, ?> params) throws SQLException {
		return query(ds, clazz, sqlId, params, 0, 1, rsh);
	}
	
	@SuppressWarnings("rawtypes")
	private static boolean isJavaSimpleClazz(Object o) throws SQLException {
		boolean result = false;
		if (o instanceof Map) {
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

	// queryByPK
	
	@SuppressWarnings({ "rawtypes" })
	public static <T> T queryByPK(DataSource ds, Class clazz, String sqlId, String colId,
			Object o, Integer pageIdx, Integer pageSize,
			ResultSetHandler<T> rsh) throws SQLException {
		if ( o == null ) {
			return null;
		} else if ( !isJavaSimpleClazz(o) ) {
			throw new SQLException("primary key type is error");
		}
		TTableProps tp = JsonFieldXmlsLoader.getTableProps(clazz);
		String sql = getSqlOfId(clazz, sqlId, colId);
		Map<String, Object> params = SqlParser.getMapOfFieldPK(sql, tp.getSetPk());
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
		return querySql(ds, sql, rsh, params, pageIdx, pageSize);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryByPK(DataSource ds, Class clazz, String sqlId, String colId, ResultSetHandler<T> rsh,
			Object o) throws SQLException {
		return queryByPK(ds, clazz, sqlId, colId, o, null, null, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryByPKOne(DataSource ds, Class clazz, String sqlId, String colId, ResultSetHandler<T> rsh,
			Object o) throws SQLException {
		return queryByPK(ds, clazz, sqlId, colId, o, 0, 1, rsh);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static <T> T queryByPK(DataSource ds, Class clazz, String sqlId,
			Object o, Integer pageIdx, Integer pageSize,
			ResultSetHandler<T> rsh) throws SQLException {
		return queryByPK(ds, clazz, sqlId, null, o, pageIdx, pageSize, rsh);
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T queryByPK(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Object o) throws SQLException {
		return queryByPK(ds, clazz, sqlId, o, null, null, rsh);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T queryByPKOne(DataSource ds, Class clazz, String sqlId, ResultSetHandler<T> rsh,
			Object o) throws SQLException {
		return queryByPK(ds, clazz, sqlId, o, 0, 1, rsh);
	}
	
}