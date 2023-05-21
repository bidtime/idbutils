package org.bidtime.dbutils.jdbc.sql.xml.parser;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.data.PropAdapt;
import org.bidtime.dbutils.data.dataset.GsonRow;
import org.bidtime.dbutils.jdbc.connection.DbConnection;
import org.bidtime.dbutils.jdbc.rs.BeanAdapt;
import org.bidtime.dbutils.jdbc.rs.DeleteAdapt;
import org.bidtime.dbutils.jdbc.rs.InsertAdapt;
import org.bidtime.dbutils.jdbc.rs.handle.MaxIdHandler;
import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetExHandler;
import org.bidtime.dbutils.jdbc.sql.SqlParser;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.dbutils.utils.comm.BeanMapUtils;

public class SqlExecute {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object execute(DataSource ds, SqlHeadCountPro p, Object[] args, Map<String, String> mapConvert) throws Exception {
		switch (p.getSqlCmdType()) {
		case SQLCmdType.INSERT: // insert
			//return insert(ds, p.getSql(), h, new HashMap<String, Object>(), null, null);
			break;
		case SQLCmdType.DELETE: // delete
			break;
		case SQLCmdType.UPDATE: // update
			break;
		case SQLCmdType.SELECT: // select
			ResultSetHandler h = (ResultSetHandler) args[0];
			if (h instanceof ResultSetExHandler) {
				((ResultSetExHandler)h).setMapBeanConvert(mapConvert);
			}
			if (args.length == 0) {
				throw new Exception("select args not null");
			} else if (args.length == 1) {
				return querySql(ds, p.getSql(), h, new HashMap<String, Object>(), null, null);
			} else {
				Map<String, Object> mapParam = null;
				Object objParam = args[1];
				if (objParam == null) {
					mapParam = new HashMap<String, Object>();
				} else {
					if (objParam instanceof Map) {
						mapParam = (Map) objParam;
					} else {
						mapParam = BeanMapUtils.beanToMap(objParam);
					}
				}
				//
				if (args.length == 2) {
					return querySql(ds, p.getSql(), h, mapParam, null, null);
				} else if (args.length == 4) {
					return querySql(ds, p.getSql(), h, mapParam, (Integer) args[2], (Integer) args[3]);
				} else {
					throw new Exception("select args is invalid");
				}
			}
		case SQLCmdType.CALL: // call
			break;
		default:
			throw new Exception("sql command not support");
		}
		return null;
	}

	// query

	private static <T> T querySql(DataSource ds, String sqlPrepare, ResultSetHandler<T> rsh, Map<String, ?> params,
			Integer pageIdx, Integer pageSize) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		String sql = SqlParser.parse(sqlPrepare, params, paramList);
		return DbConnection.query(ds, sql, paramList.toArray(), pageIdx, pageSize, rsh);
	}

	// insert

	public static int insert(DataSource ds, TTableProps tp, Object[] args_old) throws SQLException {
		if (args_old.length == 0) {
			throw new SQLException("insert could not be null");
		} else if (args_old.length == 1) {
			return insert(ds, tp, args_old[0], (PropAdapt) null);
		} else {
			Object par1 = args_old[0];
			Object par2 = args_old[1];
		    if (par2 == null) {
		    	return insert(ds, tp, par1, (PropAdapt) null);
			} if (par2 instanceof InsertAdapt) {
				return insert(ds, tp, par1, (PropAdapt)par2);
			} else if (par2 instanceof BeanAdapt) {				
				return insert(ds, tp, par1, (PropAdapt)par2);
			} else {
				throw new SQLException("insert the second param must be InsertAdapt or BeanAdapt");
			}
		}
	}

	private static int insert(DataSource ds, TTableProps tp, Object object, PropAdapt pa) throws SQLException {
		return insert(ds, tp, object, pa, new ISqlCallBack<GsonRow>() {

			@Override
			public String callback(GsonRow r) {
				return tp.getInsertSql(r, true);
			}

		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object insertForPK(DataSource ds, TTableProps tp, Object[] args_old) throws SQLException {
		if (args_old.length == 0) {
			throw new SQLException("insert par could not be null");
		} else if (args_old.length == 1) {
			throw new SQLException("insert par could not be one");
		} else if (args_old.length == 2) {
			Object par1 = args_old[0];
			Object par2 = args_old[1];
		    if (par2 == null) {
				throw new SQLException("insert par2 could not be null");
			} if (par2 instanceof ResultSetHandler) {
				return insertForPK(ds, tp, par1, (ResultSetHandler)par2);
			} else if (par2 instanceof Class) {
				Class clz = (Class)par2;
				if (clz.isAssignableFrom(Long.class)) {
					MaxIdHandler<Long> h = new MaxIdHandler<Long>(Long.class);
					return insertForPK(ds, tp, par1, h);
		          } else if (clz.isAssignableFrom(BigDecimal.class)) {
					MaxIdHandler<Long> h = new MaxIdHandler<Long>(Long.class);
					return insertForPK(ds, tp, par1, h);
		          } else if (clz.isAssignableFrom(Short.class)) {
					MaxIdHandler<Short> h = new MaxIdHandler<Short>(Short.class);
					return insertForPK(ds, tp, par1, h);
		          } else if (clz.isAssignableFrom(Byte.class)) {
					MaxIdHandler<Byte> h = new MaxIdHandler<Byte>(Byte.class);
					return insertForPK(ds, tp, par1, h);
		          } else {
					MaxIdHandler<Integer> h = new MaxIdHandler<Integer>(Integer.class);
					return insertForPK(ds, tp, par1, h);
		          }
//				ProcessMaxIdHandler<M> h = null;
//				if (clz.equals(Long.class)) {
//					h = new ProcessMaxIdHandler<Long>();
//				} else if (clz.equals(BigDecimal.class)) {
//					h = new ProcessMaxIdHandler<BigDecimal>();
//				} else if (clz.equals(Integer.class)) {
//					h = new ProcessMaxIdHandler<Integer>();
//				} else if (clz.equals(Short.class)) {
//					h = new ProcessMaxIdHandler<Short>();
//				} else {
//					h = new ProcessMaxIdHandler<Integer>();
//				}
			} else {
				throw new SQLException("insert the second param must be InsertAdapt or BeanAdapt");
			}
		} else {
//			Object par1 = args_old[0];
//			Object par2 = args_old[1];
//			Object par3 = args_old[2];
//		    if (par2 == null) {
//		    	return insertForPK(ds, tp, par1, (PropAdapt)null);
//			} if (par2 instanceof InsertAdapt) {
//				return insertForPK(ds, tp, par1, (PropAdapt)par2);
//			} else if (par2 instanceof BeanAdapt) {				
//				return insertForPK(ds, tp, par1, (PropAdapt)null);
//			} else {
				throw new SQLException("insert the second param must be InsertAdapt or BeanAdapt");
			//}
		}
	}

	private static <M> M insertForPK(DataSource ds, TTableProps tp, Object object, ResultSetHandler<M> h) throws SQLException {
		return insertForPK(ds, tp, object, h, new ISqlCallBack<GsonRow>() {

			@Override
			public String callback(GsonRow r) {
				return tp.getInsertSql(r, true);
			}

		});
	}

	public static int insertIgnore(DataSource ds, TTableProps tp, Object object, PropAdapt pa) throws SQLException {
		return insert(ds, tp, object, pa, new ISqlCallBack<GsonRow>() {
			@Override
			public String callback(GsonRow r) {
				String insSql = "insert ignore into"; //CAutoFitSql.getInsertIgnore(ds.g);
	        	return tp.getInsertSql(r, true, insSql);
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int insert(DataSource ds, TTableProps tp, Object object, PropAdapt pa, ISqlCallBack cb)
			throws SQLException {
		if (object == null) {
			return 0;
		}
		GsonRow g = null;
		try {
			if (object instanceof Map) {
				g = tp.mapToRow((Map) object, true);
			} else {
				g = tp.clazzToRow(object, true, pa);
			}
			String sql = cb.callback(g);
			return DbConnection.update(ds, sql, g.getData());
		} finally {
			g = null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <M> M insertForPK(DataSource ds, TTableProps tp, Object object, ResultSetHandler<M> h, ISqlCallBack cb) throws SQLException {
		if (object == null) {
			return (M)null;
		}
		GsonRow g = null;
		try {
			if (object instanceof Map) {
				g = tp.mapToRow((Map) object, true);
			} else {
				g = tp.clazzToRow(object, true, null);
			}
			String sql = cb.callback(g);
			return DbConnection.insert(ds, sql, h, g.getData());
		} finally {
			g = null;
		}
	}	

	// update

	public static int update(DataSource ds, TTableProps tp, Object[] args_old) throws SQLException {
		if (args_old.length == 1) {
			return update(ds, tp, args_old[0], (PropAdapt) null);
		} else {
			return update(ds, tp, args_old[0], (PropAdapt) args_old[1]);
		}
	}

	public static int update(DataSource ds, TTableProps tp, Object object, PropAdapt pa) throws SQLException {
		return update(ds, tp, object, tp.getFieldPK(), pa);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int update(DataSource ds, TTableProps tp, Object o, String[] heads, PropAdapt pa)
			throws SQLException {
		if (o == null) {
			return 0;
		}
		GsonRow g = null;
		if (o instanceof Map) {
			g = tp.mapToRow((Map) o);
		} else {
			g = tp.clazzToRow(o, pa);
		}
		g.moveToEnd(heads);
		String sql = tp.getUpdateSqlHead(tp.getTableName(), g.getHead(), heads);
		return DbConnection.update(ds, sql, g.getData());
	}

	// delete

	public static int delete(DataSource ds, TTableProps tp, Object[] args_old) throws SQLException {
		if (args_old.length == 1) {
			return delete(ds, tp, args_old[0], DeleteAdapt.PK);
		} else {
			return delete(ds, tp, args_old[0], (DeleteAdapt) args_old[1]);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private static int delete(DataSource ds, TTableProps tp, Object object, DeleteAdapt adapt) throws SQLException {
		if (object == null) {
			return 0;
		}
		int n = 0;
		if (isJavaClass(object.getClass())) {
			if (object instanceof Collection) {
				for (Object o : (Collection)object) {
					if (o==null) {
						continue;
					} else {
						if (o instanceof Number || o instanceof String) {
							n += deletePK(ds, tp, new Object[]{o});
						} else if (isJavaClass(o.getClass())) {
							throw new SQLException(o.getClass().getSimpleName() + "数据类型不支持删除操作");
						} else {
							n += deleteObject(ds, tp, o, adapt);
						}
					}
				}
			} else if (object instanceof Object[]) {
				for (Object o : (Object[])object) {
					if (o==null) {
						continue;
					} else {
						if (o instanceof Number || o instanceof String) {
							n += deletePK(ds, tp, new Object[]{o});
						} else if (isJavaClass(o.getClass())) {
							throw new SQLException(o.getClass().getSimpleName() + "数据类型不支持删除操作");
						} else {
							n += deleteObject(ds, tp, o, adapt);
						}
					}
				}
			} else {
				if (object instanceof Number || object instanceof String) {
					n = deletePK(ds, tp, new Object[]{object});
				} else {
					n = deleteObject(ds, tp, object, adapt);
				}
			}
		} else {
			n = deleteObject(ds, tp, object, adapt);
		}
		return n;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int deleteObject(DataSource ds, TTableProps tp, Object object, DeleteAdapt adapt) throws SQLException {
		GsonRow g = null;
		try {
			if (object instanceof Map) {
				g = tp.mapToRow((Map) object);
			} else {
				g = tp.clazzToRow(object, PropAdapt.NOTNULL);
			}
			return delete(ds, tp.getTableName(), g, tp.getFieldPK());
		} finally {
			g = null;
		}
	}
	
	private static int delete(DataSource ds, String tblName, GsonRow o, String[] heads) throws SQLException {
		if (o == null) {
			return 0;
		}
		GsonRow r = o.remain(heads);
		try {
			if (r == null || !r.isExistsData()) {
				return 0;
			}
			String sql = SqlUtils.getDeleteSql(tblName, r.getHead());
			return DbConnection.update(ds, sql, r.getData());
		} finally {
			r = null;
		}
	}

	private static int deletePK(DataSource ds, TTableProps tp, Object[] ids)
			throws SQLException {
		String sql = tp.getDeleteSql(tp.getTableName(), ids);
		return DbConnection.update(ds, sql, ids);
	}

	private static boolean isJavaClass(Class<?> clz) {
		return clz != null && clz.getClassLoader() == null;
	}

	//

	private interface ISqlCallBack<R> {
		String callback(R r);
	}

}
