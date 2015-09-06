package org.bidtime.utils.basic;

public class CCharComm {
	
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
	
}
