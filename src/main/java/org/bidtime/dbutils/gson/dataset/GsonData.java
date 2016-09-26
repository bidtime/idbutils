package org.bidtime.dbutils.gson.dataset;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bidtime.utils.basic.ArrayComm;

abstract public class GsonData {

	protected HashMap<String, Integer> mapHead = new HashMap<String, Integer>();

	protected String[] head;

	public String[] getHead() {
		return head;
	}

	public void setHead(List<String> heads) {
		setHead( ArrayComm.listToStringArray(heads) );
	}
	
	public void setHead(String[] heads) {
		this.head = heads;
		clearIndex();
	}
	
	abstract protected int getDataLen();
	
	public boolean isExistsData() {
		return getDataLen() > 0 ? true:false;
	}
	
//	public JSONObject toJson() {
//		JSONObject jsonObject = new JSONObject();
//		if (head != null && head.length>0) {
//			jsonObject.put("head", this.head);			
//		}
//		if (isExistsData()) {
//			jsonObject.put("data", dataToJson());
//		} else {
//			jsonObject.putOpt("data", JSONObject.NULL);
//		}
//		return jsonObject;
//	}
//	
//	abstract protected JSONArray dataToJson();
	
	protected void clearIndex() {
		mapHead.clear();
	}

	public void initIndex() {
		mapHead.clear();
		for (int i = 0; i < head.length; i++) {
			mapHead.put(head[i].toLowerCase(), i);
		}
	}

	public int getPosOfName(String headName) {
		if (mapHead.size() <= 0) {
			initIndex();
		}
		Integer n = mapHead.get(headName.toLowerCase());
		return (n != null) ? n : -1;
	}
	
	private List<Integer> findHead(String[] arHead) {
		List<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < arHead.length; j++) {
			int pos = getPosOfName(arHead[j]);
			if (pos != -1) {
				list.add(pos);
			}
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Integer[] getHeadPosArrayOfName(String[] arHead) throws SQLException {
		List<Integer> list = findHead(arHead);
		if (list.size() != arHead.length) {
			throw new SQLException("head is not compare.");
		}
		if ( list.size() > 1 ) {
			Comparator comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					Integer p1 = (Integer) o1;
					Integer p2 = (Integer) o2;
					if (p1 < p2)
						return 0;
					else
						return 1;
				}
			};
			Collections.sort(list, comp);
		}
		return (Integer[]) list.toArray(new Integer[list.size()]);
	}

}
