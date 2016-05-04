package org.bidtime.dbutils.gson.dataset;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

abstract public class GsonData {

	protected HashMap<String, Integer> mapHead = new HashMap<String, Integer>();

	protected String[] head;

	public String[] getHead() {
		return head;
	}

	public void setHead(String[] head) {
		this.head = head;
	}
	
	abstract protected int getDataLen();
	
	public boolean isExistsData() {
		return getDataLen() > 0 ? true:false;
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		if (head != null && head.length>0) {
			jsonObject.put("head", this.head);			
		}
		if (isExistsData()) {
			jsonObject.put("data", dataToJson());
		} else {
			jsonObject.putOpt("data", JSONObject.NULL);
		}
		return jsonObject;
	}
	
	abstract protected JSONArray dataToJson();
	
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
		int n = -1;
		try {
			if (mapHead.size() <= 0) {
				initIndex();
			}
			n = mapHead.get(headName.toLowerCase());
		} catch (Exception e) {
		}
		return n;
	}

}
