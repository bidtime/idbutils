package org.bidtime.dbutils.gson;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.bidtime.dbutils.gson.dataset.JsonData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONHelper {
	
	private static final Logger logger = LoggerFactory
			.getLogger(JSONHelper.class);
	
	private static String dateToyyyyMMddHHmmss(Date date) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	public static List<Map<String, Object>> parserToListMap(String json) {
		return parserToListMap(json, false);
	}
	
	public static List<Map<String, Object>> parserToListMap(String json, boolean bNew) {
		JSONArray jsonobj = new JSONArray(json);
		if (jsonobj.length() > 0 || bNew) {
			return jsonArrayToListMap(jsonobj);
		} else {
			return null;
		}
	}
	
	public static JSONArray listMapToJsonArray(List<Map<String, Object>> data) {
		JSONArray jsonArray = new JSONArray();
		if (data != null && data.size()>0) {
			for (int i = 0; i < data.size(); i++) {
				//map to json
				JSONObject jsonObject = mapToJson(data.get(i));
				//add to json array
				jsonArray.put(jsonObject);
			}
		}
		return jsonArray;
	}
	
	public static List<Map<String, Object>> jsonArrayToListMap(JSONArray dataArray) {
		if (dataArray != null && dataArray.length() > 0 ) {
			List<Map<String, Object>> list = new ArrayList
					<Map<String, Object>>(dataArray.length());
			for (int i = 0; i < dataArray.length(); i++) {
				//json to map
				Map<String, Object> map = JSONHelper.jsonToMap(
						(JSONObject)dataArray.get(i));
				//add to list map
				list.add(map);
			}
			return list;
		} else {
			return null;
		}
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public static Object objToJsonObj(Object objRaw) {
		Object objData = null;
		if (objRaw == null) {		//要转换成json.null
			objData = JSONObject.NULL;
		} else if (objRaw instanceof String) {
			objData = String.valueOf(objRaw);
		} else if (objRaw instanceof java.util.Date) {
			objData = dateToyyyyMMddHHmmss((Date) objRaw);
		} else if (objRaw instanceof java.sql.Date) {
			java.sql.Date dt = (java.sql.Date)objRaw;
			objData = dateToyyyyMMddHHmmss(new java.util.Date(dt.getTime()));
		} else if (objRaw instanceof java.sql.Timestamp) {
			java.sql.Timestamp dt = (java.sql.Timestamp)objRaw;
			objData = dateToyyyyMMddHHmmss(new java.util.Date(dt.getTime()));
		} else if (objRaw instanceof java.sql.Time) {
			java.sql.Time dt = (java.sql.Time)objRaw;
			objData = dateToyyyyMMddHHmmss(new java.util.Date(dt.getTime()));
//		} else if (objRaw instanceof GsonRows) {
//			objData = ((GsonRows)objRaw).toJson();
		} else if (objRaw instanceof JsonData) {
			objData = clazzToJson(objRaw);
		} else if (objRaw instanceof Set) {
			objData = objRaw;
		} else if (objRaw instanceof Queue) {
			objData = objRaw;
		} else {
			objData = objRaw;
		}
		return objData;
	}
	
	public static Object jsonObjToObj(Object jsonObject) {
		Object objData = null;
		if (jsonObject == JSONObject.NULL) {	//如果是json.null, 要转换成 (object)null
			objData = (Object)null;
//		} else if (jsonObject instanceof GsonRows) {
//			objData = new GsonRows((JSONObject)jsonObject);
		} else {
			objData = jsonObject;
		}
		return objData;
	}
	
	@SuppressWarnings("rawtypes")
	public static JSONArray listToJson(List list) {
		JSONArray jsonObject1 = new JSONArray();
		for (int i=0; i<list.size(); i++) {
			Object obj = list.get(i);
			jsonObject1.put(JSONHelper.objToJsonObj(obj));
		}
		return jsonObject1;
	}

	/**
	 * 将json字符串转换为List集合
	 * 
	 * @param jsonArrStr
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public static List<Map<String, Object>> jsonObjList(String jsonArrStr) {
//		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
//		JSONArray jsonArr = null;
//		try {
//			jsonArr = new JSONArray(jsonArrStr);
//			jsonList = (List<Map<String, Object>>) JSONHelper
//					.jsonToList(jsonArr);
//		} catch (JSONException e) {
//			logger.error(e.getMessage());
//		}
//		return jsonList;
//	}

	/**
	 * 将json对象的键值存放在集合，映射table的column
	 * 
	 * @param map
	 * @return
	 */
	public static List<String> jsonMapKeysList(Map<?, ?> map) {
		List<String> jsonjsonList = new ArrayList<String>();
		String columnStr = "column";
		for (int i = 0; i < map.keySet().size(); i++) {
			jsonjsonList.add(columnStr + (i + 1));
		}
		return jsonjsonList;
	}

//	public void visitMap(Map<String, Object> map) {
//		for (Entry<String, Object> entry: map.entrySet()) {
//			entry.getKey();
//			entry.getValue();
//		}
//	}

	/**
	 * 将传递近来的json数组转换为List集合
	 * 
	 * @param jsonArr
	 * @return
	 * @throws JSONException
	 */
	public static List<?> jsonToList(JSONArray jsonArr) throws JSONException {
		List<Object> jsonToMapList = new ArrayList<Object>();
		for (int i = 0; i < jsonArr.length(); i++) {
			Object object = jsonArr.get(i);
			if (object instanceof JSONArray) {
				jsonToMapList.add(JSONHelper.jsonToList((JSONArray) object));
			} else if (object instanceof JSONObject) {
				jsonToMapList.add(JSONHelper.jsonToMap((JSONObject) object));
			} else {
				jsonToMapList.add(object);
			}
		}
		return jsonToMapList;
	}
	
	public static Map<String, Object> parserToMap(String json) {
		return parserToMap(json, false);
	}
	
	public static Map<String, Object> parserToMap(String json, boolean bNew) {
		JSONObject jsonobj = new JSONObject(json);
		if (jsonobj.length() > 0 || bNew) {
			return jsonToMap(jsonobj);
		} else {
			return null;
		}
	}

	/**
	 * 将传递近来的json对象转换为Map集合
	 * 
	 * @param jsonObj
	 * @return
	 * @throws JSONException
	 */
	public static Map<String, Object> jsonToMap(JSONObject jsonObj)
			throws JSONException {
		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		Iterator<String> jsonKeys = jsonObj.keys();
		while (jsonKeys.hasNext()) {
			String jsonKey = jsonKeys.next();
			Object jsonValObj = jsonObj.get(jsonKey);
			if (jsonValObj instanceof JSONArray) {
				jsonMap.put(jsonKey,
						JSONHelper.jsonToList((JSONArray) jsonValObj));
			} else if (jsonValObj instanceof JSONObject) {
				jsonMap.put(jsonKey,
						JSONHelper.jsonToMap((JSONObject) jsonValObj));
			} else {
				jsonMap.put(jsonKey, JSONHelper.jsonObjToObj(jsonValObj));
			}
		}
		return jsonMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JSONArray listToJsonArray(List list) {
		JSONArray jsonArray = new JSONArray();
		if (list != null && list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				Object objData = list.get(i);
				if (objData instanceof List) {
					jsonArray.put(listToJsonArray((List)objData));
				} else if (objData instanceof Map) {
					jsonArray.put(mapToJson((Map)objData));
				} else {
					jsonArray.put(JSONHelper.objToJsonObj(objData));
				}
			}
		}
		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONObject mapToJson(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		for (Entry<String, Object> entry: map.entrySet()) {
			Object objData = entry.getValue();
			if (objData instanceof List) {
				jsonObject.put(entry.getKey(), listToJsonArray((List)objData));
			} else if (objData instanceof Map) {
				jsonObject.put(entry.getKey(), mapToJson((Map)objData));
			} else {
				jsonObject.put(entry.getKey(),
						JSONHelper.objToJsonObj(objData));
			}
		}
		return jsonObject;
	}
	
	@SuppressWarnings("rawtypes")
	public static JSONArray clazzToJsonArray(List list) {
		List<Map<String, Object>> listResult = new ArrayList<>();
		for (Object o:list) {
			Map<String, Object> map = clazzToMap(o);
			listResult.add(map);
		}
		return listMapToJsonArray(listResult);
	}
	
	public static JSONObject clazzToJson(Object object) {
		Map<String, Object> map = clazzToMap(object);
		return mapToJson(map);
	}
	
	public static Map<String, Object> clazzToMap(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		PropertyDescriptor[] propDescripts = null;
		try {
			propDescripts = Introspector.getBeanInfo(object.getClass()).
					getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("class to map error", e.getMessage());
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
				logger.error("class to map", e);
			}
			map.put(head, retVal);
		}
		return map;
	}

	// public static void main(String[] args) {
	// String head[] = new String[]{"http://www.baidu.com?id=01"};
	// Object data[] = new Object[]{"http://www.baidu.com?id=02"};
	// GsonEbRst rst = GsonEbUtils.toGsonEbRst(head, data);
	// String ss = rst.objectToJsonStr();
	// System.out.println(ss);
	// }

}