package org.bidtime.utils.basic;

public class CharComm {
	
	public static String getLimitText(String text, int nLimit) {
		if (text != null) {
			if (text.length() > nLimit) {
				text = text.substring(0, nLimit);
			}
			return text;
		} else {
			return null;
		}
	}

	public static String repeat(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

}
