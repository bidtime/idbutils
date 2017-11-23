/*
 * 打印执行的sql语句及其参数
 */
package org.bidtime.dbutils.jdbc.connection.log;

import java.util.Calendar;
import java.util.Date;

import org.bidtime.dbutils.jdbc.sql.SqlFmtter;
import org.bidtime.dbutils.params.StmtParams;
import org.slf4j.Logger;

public class LogSqlUtils {

//	public static void main(String[] args) {
//		String sql = "select id=?,hh=?,nn=?";
//		Object[] params = new Object[] { 1, "to", "as" };
//		String oo = getSqlParams(sql, params);
//		System.out.println(oo);
//	}

	private static void getSqlParams(String sql, Object[] params, StringBuilder sb) {
		sb.append("sql: ");
		if (params == null) {
			sb.append(sql);
			sb.append("\n\t");
			sb.append("params:is none");
		} else if (params.length == 0) {
			sb.append(sql);
			sb.append("\n\t");
			sb.append("params: 0");
		} else {
			if (!StmtParams.getInstance().getSqlOutParam()) {
				StringBuilder sbSql = new StringBuilder();
				try {
					int j = 0;
					for (int i = 0; i < sql.length(); i++) {
						char c = sql.charAt(i);
						if (c == '?') {
							if (j < params.length) {
								String val = objectToString(params[j], ',', true);
								sbSql.append(val);
								j++;
							} else {
								sbSql.append(c);
							}
						} else {
							sbSql.append(c);
						}
					}
					if (StmtParams.getInstance().getFormatSql()) {
						sb.append(SqlFmtter.fmt(sbSql.toString()));
						sb.append("\n\n    ");
					} else {
						sb.append(sbSql.toString());
						sb.append("\n\t");
					}
					sb.append("params: ");
					sb.append(params.length);
				} finally {
					sbSql.setLength(0);
					sbSql = null;
				}
			} else {
				if (StmtParams.getInstance().getFormatSql()) {
					sb.append(SqlFmtter.fmt(sql));
					sb.append("\n\n    ");
				} else {
					sb.append(sql);						
					sb.append("\n\t");
				}
				sb.append("params: ");
				sb.append(params.length);
				for (int i = 0; i < params.length; i++) {
					sb.append("\n\t");
					sb.append("[");
					sb.append(i);
					sb.append("]:");
					sb.append(objectToString(params[i], ',', true));
				}
			}
		}
	}

	private static String objectToString(Object obj, char split,
			boolean bAddQuot) {
		if (obj == null) {
			return "null";
		} else if (obj instanceof Object[]) {
			Object[] arObject = (Object[]) obj;
			switch (arObject.length) {
			case 0:
				return "null";
			case 1:
				return getFmtObject(arObject[0], false);
			default:
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < arObject.length; i++) {
					String tmp = objectToString(arObject[i], split, false);
					if (i == 0) {
						sb.append(objectToString(tmp, split, false));
					} else {
						sb.append(split);
						sb.append(objectToString(tmp, split, false));
					}
				}
				return sb.toString();
			}
		} else {
			return getFmtObject(obj, bAddQuot);
		}
	}

	private static String dateToyyyyMMddHHmmss(Date date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	private static String getFmtObject(Object obj, boolean addQuot) {
		if (obj == null) {
			return "null";
		} else if (obj instanceof String) {
			if (addQuot) {
				StringBuilder sb = new StringBuilder();
				sb.append("'");
				sb.append(obj);
				sb.append("'");
				return sb.toString();
			} else {
				return obj.toString();
			}
		} else if (obj instanceof Date) {
			if (addQuot) {
				StringBuilder sb = new StringBuilder();
				sb.append("'");
				sb.append(dateToyyyyMMddHHmmss((Date) obj));
				sb.append("'");
				return sb.toString();
			} else {
				return dateToyyyyMMddHHmmss((Date) obj);
			}
		} else {
			return obj.toString();
		}
	}

	public static void logFormatTimeNow(long startTime, String sql,
			Object[] params, int nResult, final Logger logger) {
		long endTime = System.currentTimeMillis();
		logFormatEndTimeNow(startTime, endTime, sql, params, nResult, logger);
	}

	public static void logFormatTimeNow(long startTime, String sql,
			Object[] params, final Logger logger) {
		long endTime = System.currentTimeMillis();
		logFormatEndTimeNow(startTime, endTime, sql, params, logger);
	}

	public static void logFormatEndTimeNow(long startTime, long endTime,
			String sql, Object[] params, int nResult, final Logger logger) {
		long betweenTotal = endTime - startTime;
		long tmTimeOut = StmtParams.getInstance().getSpanTimeOut();
		long betweenTimeout = betweenTotal - tmTimeOut;
		StringBuilder sb = null;
		try {
			if (betweenTimeout > 0 || logger.isDebugEnabled()) { 
				sb = new StringBuilder();
			}
			if (sb != null) {
				sb.append(endTime);
				sb.append("-");
				sb.append(startTime);
				sb.append("=");
				sb.append(betweenTotal);
				sb.append(". ");
				getSqlParams(sql, params, sb);
				sb.append("\n");
				getFmtDiffMillseconds(betweenTotal, betweenTimeout, nResult, sb);
			}
			if (betweenTimeout > 0) {
				logger.warn(sb.toString());
			} else if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
	}

	public static void logFormatEndTimeNow(long startTime, long endTime,
			String sql, Object[] params, final Logger logger) {
		long betweenTotal = endTime - startTime;
		long tmTimeOut = StmtParams.getInstance().getSpanTimeOut();
		long betweenTimeout = betweenTotal - tmTimeOut;
		StringBuilder sb = null;
		try {
			if (betweenTimeout > 0 || logger.isDebugEnabled()) { 
				sb = new StringBuilder();
			}
			if (sb != null) {
				sb.append(endTime);
				sb.append("-");
				sb.append(startTime);
				sb.append("=");
				sb.append(betweenTotal);
				sb.append(". ");
				getSqlParams(sql, params, sb);
				sb.append("\n");
				getFmtDiffMillseconds(betweenTotal, betweenTimeout, sb);
			}
			if (betweenTimeout > 0) {
				logger.warn(sb.toString());
			} else if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
	}
	
	private static void getFmtDiffMillseconds(long betweenTotal,
			long betweenTimeout, StringBuilder sb) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(betweenTotal);
		getFmtCalendar(c, betweenTimeout, sb);
	}

	private static void getFmtCalendar(Calendar c, long betweenTimeout, StringBuilder sb) {
		sb.append("\tspan:");
		sb.append(c.get(Calendar.MINUTE));
		sb.append("m:");
		sb.append(c.get(Calendar.SECOND));
		sb.append("s:");
		sb.append(c.get(Calendar.MILLISECOND));
		sb.append("ms");
		if (betweenTimeout > 0) {
			sb.append("(exceed ");
			sb.append(betweenTimeout);
			sb.append("ms)");
		}
		sb.append(".");
	}
	
	/*
		long betweenTotal = endTime - startTime;
		long tmTimeOut = StmtParams.getInstance().getSpanTimeOut();
		long betweenTimeout = betweenTotal - tmTimeOut;
	 */

	private static void getFmtDiffMillseconds(long betweenTotal,
			long betweenTimeout, int nResult, StringBuilder sb) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(betweenTotal);
		getFmtCalendar(c, betweenTimeout, nResult, sb);
	}

	private static void getFmtCalendar(Calendar c, long betweenTimeout,
			int nResult, StringBuilder sb) {
		// applies
		sb.append("\tapplies:");
		sb.append(nResult);
		sb.append(",");
		// span
		sb.append("span:");
		sb.append(c.get(Calendar.MINUTE));
		sb.append("m:");
		sb.append(c.get(Calendar.SECOND));
		sb.append("s:");
		sb.append(c.get(Calendar.MILLISECOND));
		sb.append("ms");
		if (betweenTimeout > 0) {
			sb.append("(exceed ");
			sb.append(betweenTimeout);
			sb.append("ms)");
		}
		sb.append(".");
	}

}
