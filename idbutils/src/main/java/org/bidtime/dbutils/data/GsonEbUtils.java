package org.bidtime.dbutils.data;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bidtime.dbutils.data.dataset.GsonRow;
import org.bidtime.dbutils.data.dataset.GsonRows;
import org.bidtime.dbutils.utils.comm.CaseInsensitiveHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonEbUtils {

	private static final Logger logger = LoggerFactory.getLogger(GsonEbUtils.class);

	public static GsonRow mapToRow(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		String[] head = new String[map.size()];
		Object[] data = new Object[map.size()];
		int i = 0;
		for (Entry<String, Object> entry : map.entrySet()) {
			head[i] = entry.getKey();
			data[i] = entry.getValue();
			i++;
		}
		return new GsonRow(head, data);
	}

	public static GsonRows mapsToRows(List<Map<String, Object>> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		String[] head = new String[list.get(0).keySet().size()];
		Object[][] data = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			int j = 0;
			for (Entry<String, Object> entry : map.entrySet()) {
				if (i == 0) {
					head[j] = entry.getKey();
				}
				data[i][j] = entry.getValue();
				j++;
			}
		}
		return new GsonRows(head, data);
	}

	public static Map<String, Object> clazzToMap(Object o, String[] ar, PropAdapt pa) throws Exception {
		Set<String> set = new HashSet<String>(Arrays.asList(ar));
		return clazzToMap(o, set, pa);
	}
	
	public static Map<String, Object> clazzToMap(Object o, final Set<String> set, PropAdapt pa) throws Exception {
		return clazzToMap(o, new ClazzMapCallback<String, String>() {
			@Override
			public String getIt(String s) throws Exception {
				return (set != null && set.contains(s)) ? s : null;
			}
		}, pa);
	}

	public static Map<String, Object> clazzToMap(Object o, final Map<String, String> mapProperty, PropAdapt pa)
			throws Exception {
		return clazzToMap(o, new ClazzMapCallback<String, String>() {
			@Override
			public String getIt(String s) throws Exception {
				if (mapProperty != null && !mapProperty.isEmpty()) {
					String cp = mapProperty.get(s);
					if (cp != null) {
						return cp;
					} else {
						return null;
					}
				} else {
					return s;
				}
			}
		}, pa);
	}

	public static Map<String, Object> clazzToMap(Object o, ClazzMapCallback<String, String> cb, PropAdapt pa)
			throws Exception {
		return clazzToMap(o, cb, pa, false);
	}
	
	public static Map<String, Object> clazzToMap(Object o, PropAdapt pa) throws Exception {
		return clazzToMap(o, null, pa, true);
	}
	
	public static Map<String, Object> clazzToMap(Object o, ClazzMapCallback<String, String> cb, PropAdapt pa, boolean force)
			throws Exception {
		Map<String, Object> map = new CaseInsensitiveHashMap<Object>();
		clazzToMap(o, map, cb, pa, force);
		return map;
	}
	
	public static void clazzToMap(Object o, Map<String, Object> map, ClazzMapCallback<String, String> cb, PropAdapt pa, boolean force)
			throws Exception {
		PropertyDescriptor[] propDescripts = null;
		try {
			propDescripts = Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("clazzToMap", e);
			throw new SQLException("class to map error");
		}
		for (int i = 0; i < propDescripts.length; i++) {
			PropertyDescriptor pd = propDescripts[i];
			// Method setter = pd.getWriteMethod();
			Method setter = pd.getReadMethod();
			if (setter == null) {
				continue;
			}
			if (setter.getName().equals("getClass")) {
				continue;
			}
			Object retVal = null; // 通过反射把该类对象传递给invoke方法来调用对应的方法
			try {
				retVal = setter.invoke(o);
			} catch (Exception e) {
				logger.error("clazzToMap", e);
				throw new SQLException("class to map error");
			}
			// 判断是哪种prop adapt
			if (pa != null) {
				if (pa == PropAdapt.NOTNULL) {
					if (retVal == null) {
						continue;
					}
				} else if (pa == PropAdapt.NULL) {
					if (retVal != null) {
						continue;
					}
				}
			}

			String head = pd.getName();
			if (cb != null) {
				String strRet = cb.getIt(head);
				if (strRet != null) {
					map.put(strRet, retVal);
				} else if (force) {
					map.put(head, retVal);
				}
			} else {
				map.put(head, retVal);
			}
		}
	}

}
