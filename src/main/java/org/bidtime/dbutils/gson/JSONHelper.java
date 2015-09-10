package org.bidtime.dbutils.gson;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import org.bidtime.utils.comm.CaseInsensitiveHashMap;
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
	
	private static Date yyyyMMddHHmmssToDate(String sDate) {
		return StringToDate(sDate, "yyyy-MM-dd HH:mm:ss");
	}

	private static Date StringToDate(String sDate, String fmt) {
		java.text.DateFormat df2 = new java.text.SimpleDateFormat(fmt);
		try {
			Date date2 = df2.parse(sDate);
			return date2;
		} catch (ParseException e) {
			logger.error("StringToDate", e);
			return null;
		}
	}
	
	public static List<Map<String, Object>> jsonStrToListMap(String json) {
		return jsonStrToListMap(json, false);
	}
	
	public static List<Map<String, Object>> jsonStrToListMap(String json, boolean bNew) {
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
	 * @param object
	 * @return jsonObject
	 */
	@SuppressWarnings("rawtypes")
	public static Object objToJsonObj(Object objRaw, Map mapColPros) {
		Object objData = null;
		if (objRaw == null) {		//要转换成json.null
			objData = JSONObject.NULL;
		} else if (objRaw instanceof String || objRaw instanceof Number ||
			objRaw instanceof StringBuilder || objRaw instanceof StringBuffer ||
			objRaw instanceof Boolean || objRaw instanceof Appendable) {
			objData = objRaw;
		} else if (objRaw instanceof java.util.Date) {
			objData = dateToyyyyMMddHHmmss((Date) objRaw);
		} else if (objRaw instanceof JsonData) {
			objData = clazzToJson(objRaw, mapColPros);
		} else if (objRaw instanceof Set || objRaw instanceof Queue ||
			objRaw instanceof Character ||
			objRaw instanceof Math || objRaw instanceof Enum) {
			objData = objRaw;
		} else {
			objData = clazzToJson(objRaw, mapColPros);
		}
		return objData;
	}
	
	private static Object jsonObjToObj(Object jsonObject) {
		Object objData = null;
		if (jsonObject == JSONObject.NULL) {	//如果是json.null, 要转换成 (object)null
			objData = (Object)null;
		} else {
			objData = jsonObject;
		}
		return objData;
	}
	
	@SuppressWarnings("rawtypes")
	public static JSONArray listToJson(List list, Map mapColPros) {
		JSONArray jsonObject1 = new JSONArray();
		for (int i=0; i<list.size(); i++) {
			Object obj = list.get(i);
			jsonObject1.put(JSONHelper.objToJsonObj(obj, mapColPros));
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
//	private static List<String> jsonMapKeysList(Map<?, ?> map) {
//		List<String> jsonjsonList = new ArrayList<String>();
//		String columnStr = "column";
//		for (int i = 0; i < map.keySet().size(); i++) {
//			jsonjsonList.add(columnStr + (i + 1));
//		}
//		return jsonjsonList;
//	}

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<?> jsonToList(JSONArray jsonArr) throws JSONException {
		List jsonToMapList = new ArrayList();
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
	
	public static Map<String, Object> jsonStrToMap(String json) {
		return jsonStrToMap(json, false);
	}
	
	public static Map<String, Object> jsonStrToMap(String json, boolean bNew) {
		JSONObject jsonobj = new JSONObject(json);
		if (jsonobj.length() > 0 || bNew) {
			return jsonToMap(jsonobj);
		} else {
			return null;
		}
	}
	
    @SuppressWarnings("unchecked")
	private static void callSetter(Object target, PropertyDescriptor prop, Object value)
            throws Exception {
        Method setter = prop.getWriteMethod();
        if (setter == null) {
            return;
        }
        Class<?>[] params = setter.getParameterTypes();
        try {
            // convert types for some popular ones
            if (value instanceof String && params[0].isEnum()) {
                value = Enum.valueOf(params[0].asSubclass(Enum.class), (String) value);
            }
            // Don't call setter if the value object isn't the right type
            setter.invoke(target, new Object[]{value});
        } catch (Exception e) {
            throw new Exception(
                "Cannot set " + prop.getName() + ": " + e.getMessage());
        }
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<?> jsonToListClazz(JSONArray jsonArr) throws JSONException {
		List jsonToMapList = new ArrayList();
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
	
	protected static Object processColumn(JSONObject rs, String key, Class<?> propType)
			throws Exception {	
		if ( !propType.isPrimitive() && rs.isNull(key) ) {
			return (Object)null;
		}

		if (propType.equals(String.class)) {
			return rs.getString(key);
		} else if (propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
			return Integer.valueOf(rs.getInt(key));
		} else if (propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
			return Boolean.valueOf(rs.getBoolean(key));
		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return Long.valueOf(rs.getLong(key));
		} else if (propType.equals(Double.TYPE) || propType.equals(Double.class)) {
			return Double.valueOf(rs.getDouble(key));
		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return ((Double) rs.getDouble(key)).floatValue();
		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return ((Integer) rs.getInt(key)).shortValue();
		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return ((Integer) rs.getInt(key)).byteValue();
		} else if (propType.equals(Date.class)) {
			String s = rs.getString(key);
			return yyyyMMddHHmmssToDate(s);
		} else if (propType.equals(List.class) || propType.equals(Map.class) ) {
			Object oo = (Object)rs;
			return jsonToListClazz((JSONArray)oo);
		} else {
			return rs.get(key);
		}
	}

	public static <T> T jsonStrToClazz(String json, Class<T> type)
			throws Exception {
		return jsonStrToClazz(json, type, false);
	}

	public static <T> T jsonStrToClazz(String json, Class<T> type, boolean bNew)
			throws Exception {
		JSONObject jsonobj = new JSONObject(json);
		if (jsonobj.length() > 0 || bNew) {
			return jsonToClazz(jsonobj, type);
		} else {
			return null;
		}
	}
	
	public static <T> T jsonToClazz(JSONObject jsonObj, Class<T> type)
			throws Exception {
        T bean = type.newInstance();
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(type).
					getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("class to map error", e);
		}
		
        Map<String, Object> mColumns = new CaseInsensitiveHashMap();
        try {
			for (int i=0; i<props.length; i++) {
				PropertyDescriptor pro = props[i];
				Method setter = pro.getWriteMethod();
				String head = pro.getName();
				//Method setter = pro.getReadMethod();
				if (setter == null || setter.getName().equals("getClass")) {
					continue;
				}
				mColumns.put(head, i);
			}
			Iterator<String> jsonKeys = jsonObj.keys();
			while (jsonKeys.hasNext()) {
				String jsonKey = jsonKeys.next();
				if ( !mColumns.containsKey(jsonKey) ) {
					continue;
				}
				int nIdx = (Integer)mColumns.get(jsonKey);
				PropertyDescriptor pd = props[nIdx];
				Class<?> propType = pd.getPropertyType();
				//Object jsonValObj = jsonObj.get(jsonKey);
//				if (jsonValObj == JSONObject.NULL) {	//如果是json.null, 要转换成 (object)null
//					objReturn = (Object)null;
//				} else if (jsonValObj instanceof JSONArray) {
//					//objReturn = JSONHelper.jsonToList((JSONArray) jsonValObj);
//				} else if (jsonValObj instanceof JSONObject) {
//					//objReturn = JSONHelper.jsonToMap((JSONObject) jsonValObj);
//				} else {
//					//objReturn = JSONHelper.jsonObjToObj(jsonValObj);
//					objReturn = jsonValObj;
//				}
				
	            Object objValue = null;
	            if(propType != null) {
	            	objValue = processColumn(jsonObj, jsonKey, propType);
	            }
				callSetter(bean, pd, objValue);
			}
        } finally {
        	mColumns = null;
        }
		return bean;
	}

	/**
	 * 将传递近来的json对象转换为Map集合
	 * 
	 * @param jsonObj
	 * @return
	 * @throws JSONException
	 */
	private static Map<String, Object> jsonToMap(JSONObject jsonObj)
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
	private static JSONArray listToJsonArray(List list, Map mapColPros) {
		JSONArray jsonArray = new JSONArray();
		if (list != null && list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				Object objData = list.get(i);
				if (objData instanceof List) {
					jsonArray.put(listToJsonArray((List)objData, mapColPros));
				} else if (objData instanceof Map) {
					jsonArray.put(mapToJson((Map)objData));
				} else {
					jsonArray.put(JSONHelper.objToJsonObj(objData, mapColPros));
				}
			}
		}
		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static JSONObject mapToJson(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		for (Entry<String, Object> entry: map.entrySet()) {
			Object objData = entry.getValue();
			if (objData instanceof List) {
				jsonObject.put(entry.getKey(), listToJsonArray((List)objData, null));
			} else if (objData instanceof Map) {
				jsonObject.put(entry.getKey(), mapToJson((Map)objData));
			} else {
				jsonObject.put(entry.getKey(),
						JSONHelper.objToJsonObj(objData, null));
			}
		}
		return jsonObject;
	}
	
	@SuppressWarnings("rawtypes")
	public static JSONArray clazzToJsonArray(List list, Map mapColPros) {
		List<Map<String, Object>> listResult = new ArrayList<>();
		for (Object o:list) {
			Map<String, Object> map = clazzToMap(o, mapColPros);
			listResult.add(map);
		}
		return listMapToJsonArray(listResult);
	}
	
	public static JSONObject clazzToJson(Object object) {
		return clazzToJson(object, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JSONObject clazzToJson(Object object, Map mapHead) {
		JSONObject jsonObject = new JSONObject();
		PropertyDescriptor[] propDescripts = null;
		try {
			propDescripts = Introspector.getBeanInfo(object.getClass()).
					getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("class to map error", e);
		}

		for (int i=0; i<propDescripts.length; i++) {
			PropertyDescriptor pro = propDescripts[i];
			//Method setter = pd.getWriteMethod();
			String key = pro.getName();
			Method setter = pro.getReadMethod();

			if (setter == null || setter.getName().equals("getClass")) {
				continue;
			}

			if (mapHead != null && !mapHead.containsKey(key)) {
				continue;
			}

			Object retVal = null;	//通过反射把该类对象传递给invoke方法来调用对应的方法  
			try {
				retVal = setter.invoke(object);
			} catch (Exception e) {
				logger.error("clazzToJson", e);
			}
			if (retVal instanceof List) {
				jsonObject.put(key, listToJsonArray((List)retVal, mapHead));
			} else if (retVal instanceof Map) {
				jsonObject.put(key, mapToJson((Map)retVal));
			} else {
				jsonObject.put(key, JSONHelper.objToJsonObj(retVal, mapHead));
			}
		}
		return jsonObject;
	}
	
	public static Map<String, Object> clazzToMap(Object object) {
		return clazzToMap(object,  null);
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> clazzToMap(Object object, Map mapHead) {
		Map<String, Object> map = new HashMap<String, Object>();
		PropertyDescriptor[] propDescripts = null;
		try {
			propDescripts = Introspector.getBeanInfo(object.getClass()).
					getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("class to map error", e);
		}
		
		for (int i=0; i<propDescripts.length; i++) {
			PropertyDescriptor pd = propDescripts[i];
			//Method setter = pd.getWriteMethod();
			String key = pd.getName();
			Method setter = pd.getReadMethod();
			if (setter == null || setter.getName().equals("getClass")) {
				continue;
			}
			if (mapHead != null && !mapHead.containsKey(key)) {
				continue;
			}
			Object retVal = null;	//通过反射把该类对象传递给invoke方法来调用对应的方法  
			try {
				retVal = setter.invoke(object);
			} catch (Exception e) {
				logger.error("class to map", e);
			}
			map.put(key, retVal);
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