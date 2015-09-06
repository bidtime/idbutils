package org.bidtime.dbutils.jdbc.sql;

import java.util.List;

public class ArrayUtils {
	
	public static String[] listToStringArray(List<String> list) {
		if (list!=null) {
			return (String[])list.toArray(new String[list.size()]);
		} else return null;
	}

	public static Object[] mergeArrayObject(Object[] buf1, Object[] buf2) {
		Object[] bufret = null;
		int len1 = 0;
		int len2 = 0;

		if (buf1 != null)
			len1 = buf1.length;
		if (buf2 != null)
			len2 = buf2.length;
		if (len1 + len2 > 0)
			bufret = new Object[len1 + len2];
		if (len1 > 0)
			System.arraycopy(buf1, 0, bufret, 0, len1);
		if (len2 > 0)
			System.arraycopy(buf2, 0, bufret, len1, len2);
		return bufret;
	}

	public static String[] mergeArrayObject(String[] buf1, String[] buf2) {
		String[] bufret = null;
		int len1 = 0;
		int len2 = 0;

		if (buf1 != null)
			len1 = buf1.length;
		if (buf2 != null)
			len2 = buf2.length;
		if (len1 + len2 > 0)
			bufret = new String[len1 + len2];
		if (len1 > 0)
			System.arraycopy(buf1, 0, bufret, 0, len1);
		if (len2 > 0)
			System.arraycopy(buf2, 0, bufret, len1, len2);
		return bufret;
	}

	public static Object[] mergeArrayObject(Object[] buf1, Object[] buf2, Object[] buf3) {
		Object[] objReturn = mergeArrayObject(buf1, buf2);
		return mergeArrayObject(objReturn, buf3);
	}

}
