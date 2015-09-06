package org.bidtime.utils.basic;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CDateTimeComm {

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
				return StringToDate((String) o, sFmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Date yymmddToDate(String sDate) {
		return StringToDate(sDate, "yyyy-MM-dd");
	}

	public static Date yymmddIntToDate(String sDate) {
		return StringToDate(sDate, "yyyyMMdd");
	}

	public static Date StringToDate(String sDate, String fmt) {
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

	@Deprecated
	public static Date getAddDate(Date d) {
		return getAddDate(d, 1);
	}

	@Deprecated
	public static Date getAddDate(Date d, int n) {
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(Calendar.DATE, n);
		return c.getTime();
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

}
