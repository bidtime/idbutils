package org.bidtime.utils.basic;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeComm {
	
	public static boolean isSameTime(Date date1, Date date2) {
		if (date1 == null) {
			return (date2 == null) ? true : false;
		} else {
			return (date2 == null) ? false : date1.getTime() == date2.getTime();
		}
	}

	public static Date objectToDate(Object o) {
		if (o != null) {
			if (o instanceof Date) {
				return ((Date) o);
			} else if (o instanceof String) {
				return yyyyMMddHHmmssToDate((String) o);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Date objectToDate(Object o, String sFmt) {
		if (o != null) {
			if (o instanceof Date) {
				return ((Date) o);
			} else if (o instanceof String) {
				return stringToDate((String) o, sFmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Date yymmddToDate(String sDate) {
		return stringToDate(sDate, "yyyy-MM-dd");
	}

	public static Date yymmddIntToDate(String sDate) {
		return stringToDate(sDate, "yyyyMMdd");
	}

	public static Date stringToDate(String sDate, String fmt) {
		java.text.DateFormat df2 = new java.text.SimpleDateFormat(fmt);
		try {
			Date date2 = df2.parse(sDate);
			return date2;
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToyyyymmdd(Date date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	public static String dateToyyyymmddInt(Date date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}

	public static String dateToyyyyMMddHHmmss(Date date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public static String dateToString(Date date, String fmt) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(fmt);
		return df.format(date);
	}

	public static Date yyyyMMddHHmmssToDate(String sDate) {
		java.text.DateFormat df2 = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date date2 = df2.parse(sDate);
			return date2;
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static long getDateMS() {
		//处理时间，建议用Calendar
		Calendar c = Calendar.getInstance();
		//设置当前时刻的时钟为0
		c.set(Calendar.HOUR_OF_DAY, 0);
		//设置当前时刻的分钟为0
		c.set(Calendar.MINUTE, 0);
		//设置当前时刻的秒钟为0
		c.set(Calendar.SECOND, 0);
		//设置当前的毫秒钟为0
		c.set(Calendar.MILLISECOND, 0);
		//获取当前时刻的时间戳
		return c.getTimeInMillis();
	}
	
	@Deprecated
	public static Date getDate() {
		return getDay();
	}
	
	public static Date getDay() {
		return new Date(getDateMS());
	}

	public static Date getAddYear(Date d) {
		return getAddYear(d, 1);
	}

	public static Date getAddYear(Date d, int n) {
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(Calendar.YEAR, n);
		return c.getTime();
	}

	public static Date getAddMonth(Date d) {
		return getAddMonth(d, 1);
	}

	public static Date getAddMonth(Date d, int n) {
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(Calendar.MONTH, n);
		return c.getTime();
	}

	public static Date getAddDay(Date d) {
		return getAddDay(d, 1);
	}

	public static Date getAddDay(Date d, int n) {
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(Calendar.DATE, n);
		return c.getTime();
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		final int firstDay = 1;
		calendar.set(Calendar.DAY_OF_MONTH, firstDay);
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		final int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		return calendar.getTime();
	}

	public static long getDiffDay(Date dateBegin, Date dateEnd) {
		return getDiffDay(dateEnd.getTime(), dateBegin.getTime());
	}

	public static long getDiffDay(long dateBegin, long dateEnd) {
		long lDiff = dateEnd - dateBegin;
		return lDiff / (1000 * 60 * 60 * 24);
	}

	public static String getFormatSpanTimeNow(long date) {
		long endTime = System.currentTimeMillis();
		return getFormatSpanTime(date, endTime);
	}

	public static String getFormatSpanTime(long startTime, long endTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(endTime - startTime);
		return "span: " + c.get(Calendar.MINUTE) + "m:"
				+ c.get(Calendar.SECOND) + "s:" + c.get(Calendar.MILLISECOND)
				+ "ms";
	}
	
//	public static void logFormatEndTime(long startTime, long endTime, final Logger logger) {
//		long spanSeconds = endTime - startTime;
//		if (logger.isDebugEnabled()) {
//			logger.debug(getFmtDiffMillseconds(spanSeconds));
//		}
//	}
//
//	public static String getFmtDiffMillseconds(long spanSeconds) {
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(spanSeconds);
//		return getFmtCalendar(c);
//	}
//	
//	private static String getFmtCalendar(Calendar c) {
//		StringBuilder sb = new StringBuilder("\tspan:");
//		sb.append(c.get(Calendar.MINUTE));
//		sb.append("m:");
//		sb.append(c.get(Calendar.SECOND));
//		sb.append("s:");
//		sb.append(c.get(Calendar.MILLISECOND));
//		sb.append("ms");
//		sb.append(".");
//		return sb.toString();
//	}

	public static String getDirectoryOfDate(String type) {
		return type + dateToString(new Date(), "yyyy/MM/dd/");
	}
	
	public static String getDirectoryOfDate() {
	    return dateToString(new Date(), "yyyy/MM/dd/");
	}
}
