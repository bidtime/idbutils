package org.bidtime.dbutils.gson;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;

public class GsonEbUtils {
	
//	private static final Logger logger = LoggerFactory
//			.getLogger(GsonEbUtils.class);

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

}
