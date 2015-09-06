package org.bidtime.dbutils.gson;

import java.text.ParseException;
import java.util.Date;

public class JSONUtils {

	public static String dateToString(Date date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String s = df.format(date);
		return s;
	}

	public static Date stringToDate(String sDate) {
		// String s2 = "1996-02-45"; // yyyyMMdd
		java.text.DateFormat df2 = new java.text.SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		try {
			Date date2 = df2.parse(sDate);
			return date2;
		} catch (ParseException e) {
			return null;
		}
	}

	public static double stringToDouble(String s) {
		try {
			return Double.valueOf(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String doubleToString(double d) {
		if ((Double.isInfinite(d)) || (Double.isNaN(d))) {
			return "";
		}

		String s = Double.toString(d);
		if ((s.indexOf('.') > 0) && (s.indexOf('e') < 0)
				&& (s.indexOf('E') < 0)) {
			while (s.endsWith("0")) {
				s = s.substring(0, s.length() - 1);
			}
			if (s.endsWith(".")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		return s;
	}

	public static String intToString(int n) {
		String s = Integer.toString(n);
		return s;
	}

	public static int stringToInt(String s) {
		try {
			return Integer.valueOf(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String shortToString(short n) {
		String s = Short.toString(n);
		return s;
	}

	public static short stringToShort(String s) {
		try {
			return Short.valueOf(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String numberToString(Number n) {
		if (n == null) {
			return "";
		}

		String s = n.toString();
		if ((s.indexOf('.') > 0) && (s.indexOf('e') < 0)
				&& (s.indexOf('E') < 0)) {
			while (s.endsWith("0")) {
				s = s.substring(0, s.length() - 1);
			}
			if (s.endsWith(".")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		return s;
	}

	public static String strToString(String string) {
		// if (isFunction(string)) {
		// return string;
		// }
		if ((string == null) || (string.length() == 0)) {
			return "''";
		}

		char c = '\000';

		int len = string.length();
		StringBuilder sb = new StringBuilder(len * 2);

		char[] chars = string.toCharArray();
		char[] buffer = new char[1030];
		int bufferIndex = 0;
		sb.append("\"");
		for (int i = 0; i < len; i++) {
			if (bufferIndex > 1024) {
				sb.append(buffer, 0, bufferIndex);
				bufferIndex = 0;
			}
			char b = c;
			c = chars[i];
			switch (c) {
			case '"':
			case '\\':
				buffer[(bufferIndex++)] = '\\';
				buffer[(bufferIndex++)] = c;
				break;
			case '/':
				if (b == '<') {
					buffer[(bufferIndex++)] = '\\';
				}
				buffer[(bufferIndex++)] = c;
				break;
			default:
				if (c < ' ')
					switch (c) {
					case '\b':
						buffer[(bufferIndex++)] = '\\';
						buffer[(bufferIndex++)] = 'b';
						break;
					case '\t':
						buffer[(bufferIndex++)] = '\\';
						buffer[(bufferIndex++)] = 't';
						break;
					case '\n':
						buffer[(bufferIndex++)] = '\\';
						buffer[(bufferIndex++)] = 'n';
						break;
					case '\f':
						buffer[(bufferIndex++)] = '\\';
						buffer[(bufferIndex++)] = 'f';
						break;
					case '\r':
						buffer[(bufferIndex++)] = '\\';
						buffer[(bufferIndex++)] = 'r';
						break;
					case '\013':
					default:
						String t = "000" + Integer.toHexString(c);
						int tLength = t.length();
						buffer[(bufferIndex++)] = '\\';
						buffer[(bufferIndex++)] = 'u';
						buffer[(bufferIndex++)] = t.charAt(tLength - 4);
						buffer[(bufferIndex++)] = t.charAt(tLength - 3);
						buffer[(bufferIndex++)] = t.charAt(tLength - 2);
						buffer[(bufferIndex++)] = t.charAt(tLength - 1);
					}
				else {
					buffer[(bufferIndex++)] = c;
				}
			}
		}
		sb.append(buffer, 0, bufferIndex);
		sb.append("\"");
		return sb.toString();
	}

}
