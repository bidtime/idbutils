package org.bidtime.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bidtime.utils.basic.ArrayComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jss
 * 
 *         提供对从Request中取出参数的功能
 *
 */
public class RequestUtils {

	// request 中的参数
	public final static String ID = "id";
	public final static String DATA = "data";
	public final static String DETL = "detl";
	public final static short PAGE_SIZE_10 = 10;
	public final static short PAGE_SIZE_100 = 100;
	public final static String COMMA = ",";

	private static final Logger logger = LoggerFactory
			.getLogger(RequestUtils.class);

	public static Short getRequestValidPageSize(Short pageSize) {
		return getRequestValidPageSize(pageSize, PAGE_SIZE_100);
	}

	public static Short getRequestValidPageSize(Short pageSize, Short nDefault) {
		if (pageSize != null) {
			if (pageSize > nDefault) {
				return nDefault;
			} else {
				return pageSize;
			}
		} else {
			return PAGE_SIZE_10;
		}
	}

	public static String getStringOfId(HttpServletRequest request) {
		return getString(request, ID, null);
	}

	public static String getStringOfData(HttpServletRequest request) {
		return getString(request, DATA, null);
	}

	public static String getStringOfDetl(HttpServletRequest request) {
		return getString(request, DETL, null);
	}

	public static String getString(HttpServletRequest request, String sParam) {
		return getString(request, sParam, null);
	}

	public static Object[] getSplit(HttpServletRequest request, String sParam) {
		return getSplit(request, sParam, COMMA);
	}

	public static Object[] getIdSplit(HttpServletRequest request)
			throws Exception {
		return getSplit(request, ID, COMMA);
	}

	public static String[] getIdStrSplit(HttpServletRequest request)
			throws Exception {
		return getStrSplit(request, ID, COMMA);
	}

	public static Long[] getIdLongSplit(HttpServletRequest request)
			throws Exception {
		return getLongSplit(request, ID, COMMA);
	}

	public static Integer[] getIdIntegerSplit(HttpServletRequest request)
			throws Exception {
		return getIntegerSplit(request, ID, COMMA);
	}

	public static String[] getStrSplit(HttpServletRequest request, String sParam)
			throws Exception {
		return getStrSplit(request, sParam, COMMA);
	}

	public static Long[] getLongSplit(HttpServletRequest request, String sParam)
			throws Exception {
		return getLongSplit(request, sParam, COMMA);
	}

	public static Integer[] getIntegerSplit(HttpServletRequest request,
			String sParam) throws Exception {
		return getIntegerSplit(request, sParam, COMMA);
	}

	public static Set<Long> getSetLongSplit(HttpServletRequest request,
			String sParam) throws Exception {
		return getSetLongSplit(request, sParam, COMMA);
	}

	public static Set<Long> getSetLongSplit(HttpServletRequest request,
			String sParam, String split) throws Exception {
		Long[] strs = getLongSplit(request, sParam, split);
		Set<Long> setIds = new HashSet<Long>(Arrays.asList(strs));
		return setIds;
	}

	public static Set<Integer> getSetIntegerSplit(HttpServletRequest request,
			String sParam) throws Exception {
		return getSetIntegerSplit(request, sParam, COMMA);
	}

	public static Set<Integer> getSetIntegerSplit(HttpServletRequest request,
			String sParam, String split) throws Exception {
		Integer[] strs = getIntegerSplit(request, sParam, split);
		Set<Integer> setIds = new HashSet<Integer>(Arrays.asList(strs));
		return setIds;
	}

	public static Set<String> getSetStringSplit(HttpServletRequest request,
			String sParam) throws Exception {
		return getSetStringSplit(request, sParam, COMMA);
	}

	public static Set<String> getSetStringSplit(HttpServletRequest request,
			String sParam, String split) throws Exception {
		String[] strs = getStrSplit(request, sParam, split);
		Set<String> setIds = new HashSet<String>(Arrays.asList(strs));
		return setIds;
	}

	// public static ParserDataSet getPaserDataSetOfRequest(HttpServletRequest
	// request, String sParam) throws Exception {
	// String data = request.getParameter(sParam);
	// ParserDataSet g = ParserDataSet.jsonStrToObject(data);
	// if (g == null) {
	// throw new Exception("json data is null");
	// } else {
	// return g;
	// }
	// }

	// public static Object getPaserJsonOfRequest(HttpServletRequest request,
	// String sParam) throws Exception {
	// String data = request.getParameter(sParam);
	// JSONObject jsonObject = new JSONObject(data);
	// Object o = JSONHelper.jsonToMap(jsonObject);
	// if (o == null) {
	// throw new Exception("json data is null");
	// } else {
	// return o;
	// }
	// }

//	public static Map<String, Object> jsonStrToMap(HttpServletRequest request,
//			String sParam) throws Exception {
//		String jsonObject = RequestUtils.getString(request, sParam);
//		return JSONHelper.jsonStrToMap(jsonObject);
//	}
//
//	public static <T> T jsonStrToClazz(HttpServletRequest request,
//			String sParam, Class<T> type) throws Exception {
//		String json = RequestUtils.getString(request, sParam);
//		return JSONHelper.jsonStrToClazz(json, type);
//	}
//
//	@SuppressWarnings("rawtypes")
//	public static <T> T paramsMapToClazz(HttpServletRequest request,
//			Class<T> type) throws Exception {
//		Map map = getMapOfRequest(request);
//		if (map != null) {
//			return (T) JSONHelper.mapToClazz(map, type);
//			// BeanUtils.populate(t, request.getParameterMap());
//		} else {
//			return null;
//		}
//	}

	public static Object[] getSplit(HttpServletRequest request, String sParam,
			String splitChar) {
		String sId = getString(request, sParam);
		if (StringUtils.isEmpty(sId)) {
			return null;
		} else {
			return sId.split(splitChar);
		}
	}

	public static Object[] getSplit(String str, String strSplit) {
		if (StringUtils.isEmpty(str)) {
			return null;
		} else {
			return str.split(strSplit);
		}
	}

	public static String[] getStrSplit(HttpServletRequest request,
			String sParam, String splitChar) throws Exception {
		String sId = getString(request, sParam);
		if (StringUtils.isEmpty(sId)) {
			return null;
		} else {
			return sId.split(splitChar);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> asList(T a, HttpServletRequest request,
			String sParam, String splitChar) {
		Object[] ars = getSplit(request, sParam, splitChar);
		return Arrays.asList((T) ars);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> asList(T a, HttpServletRequest request,
			String sParam) {
		Object[] ars = getSplit(request, sParam, COMMA);
		return Arrays.asList((T) ars);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> asList(T a, String str, String strSplit) {
		Object[] ars = getSplit(str, strSplit);
		return Arrays.asList((T) ars);
	}

	public static Long[] getLongSplit(HttpServletRequest request,
			String sParam, String splitChar) throws Exception {
		String sId = getString(request, sParam);
		if (StringUtils.isEmpty(sId)) {
			throw new Exception("params is null");
		} else {
			return ArrayComm.StringsToLongArray(sId.split(splitChar));
		}
	}

	public static Integer[] getIntegerSplit(HttpServletRequest request,
			String sParam, String splitChar) throws Exception {
		String sId = getString(request, sParam);
		if (StringUtils.isEmpty(sId)) {
			throw new Exception("params is null");
		} else {
			return ArrayComm.StringsToIntegerArray(sId.split(splitChar));
		}
	}

	public static String getString(HttpServletRequest request, String sParam,
			String sDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (s == null)
			return sDefault;
		else
			return s;
	}

	public static Integer getInteger(HttpServletRequest request, String sParam) {
		return getInteger(request, sParam, null);
	}

	public static Integer getInteger(HttpServletRequest request, String sParam,
			Integer nDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + sParam + " -> " + s);
		}
		if (StringUtils.isEmpty(s)) {
			return nDefault;
		} else {
			try {
				return Integer.parseInt(s);
			} catch (Exception e) {
				logger.warn("getInt:" + sParam + " -> " + s, e);
				return nDefault;
			}
		}
	}

	public static Long getLong(HttpServletRequest request, String sParam) {
		return getLong(request, sParam, null);
	}

	public static Long getLong(HttpServletRequest request, String sParam,
			Long lDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (StringUtils.isEmpty(s)) {
			return lDefault;
		} else {
			try {
				return Long.parseLong(s);
			} catch (Exception e) {
				logger.warn("getLong:" + sParam + " -> " + s, e);
				return lDefault;
			}
		}
	}

	public static Short getShort(HttpServletRequest request, String sParam) {
		return getShort(request, sParam, null);
	}

	public static Short getShort(HttpServletRequest request, String sParam,
			Short nDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (StringUtils.isEmpty(s)) {
			return nDefault;
		} else {
			try {
				return Short.parseShort(s);
			} catch (Exception e) {
				logger.warn("getShort:" + sParam + " -> " + s, e);
				return nDefault;
			}
		}
	}

	public static Byte getByte(HttpServletRequest request, String sParam) {
		return getByte(request, sParam, null);
	}

	public static Byte getByte(HttpServletRequest request, String sParam,
			Byte nDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (StringUtils.isEmpty(s)) {
			return nDefault;
		} else {
			try {
				return Byte.parseByte(s);
			} catch (Exception e) {
				logger.warn("getByte:" + sParam + " -> " + s, e);
				return nDefault;
			}
		}
	}

	public static Double getDouble(HttpServletRequest request, String sParam) {
		return getDouble(request, sParam, null);
	}

	public static Double getDouble(HttpServletRequest request, String sParam,
			Double dDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (StringUtils.isEmpty(s)) {
			return dDefault;
		} else {
			try {
				return Double.parseDouble(s);
			} catch (Exception e) {
				logger.warn("getDouble:" + sParam + " -> " + s, e);
				return dDefault;
			}
		}
	}

	public static Float getFloat(HttpServletRequest request, String sParam) {
		return getFloat(request, sParam, null);
	}

	public static Float getFloat(HttpServletRequest request, String sParam,
			Float fDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (StringUtils.isEmpty(s)) {
			return fDefault;
		} else {
			try {
				return Float.parseFloat(s);
			} catch (Exception e) {
				logger.warn("getFloat:" + sParam + " -> " + s, e);
				return fDefault;
			}
		}
	}

	private static Date strToDate(String sDate, String fmt) {
		java.text.DateFormat df2 = new java.text.SimpleDateFormat(fmt);
		try {
			Date date2 = df2.parse(sDate);
			return date2;
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getDate(HttpServletRequest request, String sParam) {
		return getDate(request, sParam, null);
	}

	public static Date getDate(HttpServletRequest request, String sParam,
			Date tDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (s == null) {
			return tDefault;
		} else {
			return strToDate(s, "yyyy-MM-dd");
		}
	}

	public static Date getDateTime(HttpServletRequest request, String sParam) {
		return getDateTime(request, sParam, null);
	}

	public static Date getDateTime(HttpServletRequest request, String sParam,
			Date tDefault) {
		String s = request.getParameter(sParam);
		if (logger.isDebugEnabled()) {
			logger.debug(sParam + ":" + s);
		}
		if (s == null) {
			return tDefault;
		} else {
			return strToDate(s, "yyyy-MM-dd HH:mm:ss");
		}
	}

	private static String UrlEncodeUtf8(String s) {
		return UrlEncode(s, "UTF-8");
	}

	private static String UrlEncode(String s, String sEncode) {
		try {
			return URLEncoder.encode(s, sEncode);
		} catch (UnsupportedEncodingException e) {
			logger.error("UrlEncode:", e.getMessage() + s);
			return null;
		}
	}

	public static String getUtf8ParamsOfRequest(HttpServletRequest request) {
		return getParamsOfRequest(request, true);
	}

	@SuppressWarnings({ "rawtypes" })
	public static String getParamsOfRequest(HttpServletRequest request,
			boolean bHttpEncode) {
		Map map = request.getParameterMap();
		return getParamsOfMap(map, bHttpEncode);
	}

	@SuppressWarnings({ "rawtypes" })
	public static String getParamsOfMap(Map map, boolean bHttpEncode) {
		StringBuilder sb = new StringBuilder();
		Set keySet = map.entrySet();
		boolean bExistsKeyValue = false;
		for (Iterator itr = keySet.iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			Object objectKey = me.getKey();
			Object objectValue = me.getValue();
			String[] value = new String[1];
			if (objectValue instanceof String[]) {
				value = (String[]) objectValue;
			} else {
				value[0] = objectValue.toString();
			}

			for (int k = 0; k < value.length; k++) {
				String sValue = value[k];
				if (!StringUtils.isEmpty(sValue)) {
					if (bExistsKeyValue) {
						sb.append("&");
					}
					sb.append(objectKey);
					sb.append("=");
					if (bHttpEncode) {
						sb.append(UrlEncodeUtf8(sValue));
					} else {
						sb.append(sValue);
					}
					if (!bExistsKeyValue) {
						bExistsKeyValue = true;
					}
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMapOfRequest(HttpServletRequest request) {
		Map mapRtn = new HashMap();
		Map map = request.getParameterMap();
		Set keSet = map.entrySet();
		for (Iterator itr = keSet.iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			String key = (String) (entry.getKey());
			Object objectValue = entry.getValue();
			Object valRtn = null;
			if (objectValue instanceof Object[]) {
				valRtn = ((Object[]) objectValue)[0];
			} else {
				valRtn = objectValue;
			}
			mapRtn.put(key, valRtn);
		}
		return mapRtn;
	}
	
	public static String getBodyStr(HttpServletRequest request) throws IOException {
		return getBodyStr(request.getReader());
	}

	public static String getBodyStr(BufferedReader br) {
		StringBuilder sb = new StringBuilder();
		try {
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
		} catch (IOException e) {
			logger.error("getBodyStr", e);
		}
		return sb.toString();
	}

//	public static Map<String, Object> getBodyMap(HttpServletRequest request) throws IOException {
//		String bodyStr = getBodyStr(request);
//		return JSONHelper.jsonStrToMap(bodyStr, false);
//	}
	
}
