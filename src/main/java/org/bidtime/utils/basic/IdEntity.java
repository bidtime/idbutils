package org.bidtime.utils.basic;

import java.util.UUID;

public class IdEntity {
	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID(boolean bDelMinus) {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		if (bDelMinus) {
			StringBuilder sb = new StringBuilder();
			sb.append(s.substring(0, 8));
			sb.append(s.substring(9, 13));
			sb.append(s.substring(14, 18));
			sb.append(s.substring(19, 23));
			sb.append(s.substring(24));
			return sb.toString();
		} else {
			return s;
		}
	}

}
