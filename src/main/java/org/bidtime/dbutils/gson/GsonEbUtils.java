package org.bidtime.dbutils.gson;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.sql.xml.parser.ColumnPro;
import org.bidtime.utils.comm.CaseInsensitiveHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonEbUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(GsonEbUtils.class);

	public static GsonRow mapToRow(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		String[] head = new String[map.size()];
		Object[] data = new Object[map.size()];
		int i = 0;
		for (Entry<String, Object> entry: map.entrySet()) {
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

	public static Map<String, Object> clazzToMap(Object object,
			Map<String, ColumnPro> mapProperty, boolean force) throws SQLException {
		Map<String, Object> map = new CaseInsensitiveHashMap();
		PropertyDescriptor[] propDescripts=null;
		try {
			propDescripts = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("clazzToMap", e);
			throw new SQLException("class to map error");
		}
		for (int i=0; i<propDescripts.length; i++) {
			PropertyDescriptor pro = propDescripts[i];
			//Method setter = pd.getWriteMethod();
			String head = pro.getName();
			Method setter = pro.getReadMethod();  

	        if (setter == null) {
	            continue;
	        }
	        if (setter.getName().equals("getClass")) {
	        	continue;
	        }
	        Object retVal = null;	//通过反射把该类对象传递给invoke方法来调用对应的方法  
			try {
				retVal = setter.invoke(object);
			} catch (Exception e) {
				logger.error("clazzToMap", e);
				throw new SQLException("class to map error");
			}
			if (mapProperty != null && !mapProperty.isEmpty()) {
				ColumnPro cp = mapProperty.get(head);
				if (cp != null) {
					map.put(cp.getColumn(), retVal);
				} else {
					map.put(head, retVal);
				}
			} else if (force) {
				map.put(head, retVal);
			}
		}
		return map;
	}

}
