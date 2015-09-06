package org.bidtime.utils.comm;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class PropComm {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void propTpMap(Properties pro, Map map)
			throws Exception {
		Enumeration<?> en = pro.propertyNames();
		String key = null;
		String value = null;
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			value = (String) pro.get(key);
			if (key != null && key.trim().length() > 0) {
				map.put(key, value);
			}
		}
	}
}
