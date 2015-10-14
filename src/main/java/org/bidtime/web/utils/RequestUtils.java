package org.bidtime.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.JSONHelper;
import org.bidtime.utils.basic.ArrayComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jss
 * 
 * 提供对从Request中取出参数的功能
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

	public static Object[] getIdStrSplit(HttpServletRequest request) throws Exception {
		return getSplit(request, ID, COMMA);
	}

	public static Long[] getIdLongSplit(HttpServletRequest request) throws Exception {
		return getLongSplit(request, ID, COMMA);
	}

	public static Integer[] getIdIntegerSplit(HttpServletRequest request) throws Exception {
		return getIntegerSplit(request, ID, COMMA);
	}

	public static Object[] getStrSplit(HttpServletRequest request, String sParam) throws Exception {
		return getSplit(request, sParam, COMMA);
	}

	public static Long[] getIdLongSplit(HttpServletRequest request, String sParam) throws Exception {
		return getLongSplit(request, sParam, COMMA);
	}

	public static Integer[] getIdIntegerSplit(HttpServletRequest request, String sParam) throws Exception {
		return getIntegerSplit(request, sParam, COMMA);
	}
	
//	public static ParserDataSet getPaserDataSetOfRequest(HttpServletRequest request, String sParam) throws Exception {
//		String data = request.getParameter(sParam);
//		ParserDataSet g = ParserDataSet.jsonStrToObject(data);
//		if (g == null) {
//			throw new Exception("json data is null");
//		} else {
//			return g;
//		}
//	}
	
//	public static Object getPaserJsonOfRequest(HttpServletRequest request, String sParam) throws Exception {
//		String data = request.getParameter(sParam);
//		JSONObject jsonObject = new JSONObject(data);
//		Object o = JSONHelper.jsonToMap(jsonObject);
//		if (o == null) {
//			throw new Exception("json data is null");
//		} else {
//			return o;
//		}
//	}
//	
	public static Map<String, Object> jsonStrToMap(HttpServletRequest request, String sParam) throws Exception {
		String jsonObject = RequestUtils.getString(request, sParam);
		return JSONHelper.jsonStrToMap(jsonObject);
	}

	public static <T> T jsonStrToClazz(HttpServletRequest request, String sParam, Class<T> type) throws Exception {
		String json = RequestUtils.getString(request, sParam);
		return JSONHelper.jsonStrToClazz(json, type);
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T paramsMapToClazz(HttpServletRequest request, Class<T> type) throws Exception {
		Map map = getMapOfRequest(request);
		if (map != null) {
			return (T)JSONHelper.mapToClazz(map, type);
			//BeanUtils.populate(t, request.getParameterMap());
		} else {
			return null;
		}
	}

	public static Object[] getSplit(HttpServletRequest request, String sParam,
			String splitChar) {
		String sId = getString(request, sParam);
		if (sId != null) {
			return sId.split(splitChar);
		} else {
			return null;
		}
	}

	public static String[] getStrSplit(HttpServletRequest request, String sParam,
			String splitChar) throws Exception {
		String sId = getString(request, sParam);
		if (sId != null) {
			return sId.split(splitChar);
		} else {
			return null;
		}
	}

	public static Long[] getLongSplit(HttpServletRequest request, String sParam,
			String splitChar) throws Exception {
		String sId = getString(request, sParam);
		if (sId == null) {
			throw new Exception("params is null");
		} else {
			return ArrayComm.StringsToLongArray(sId.split(splitChar));
		}
	}

	public static Integer[] getIntegerSplit(HttpServletRequest request, String sParam,
			String splitChar) throws Exception {
		String sId = getString(request, sParam);
		if (sId == null) {
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
			logger.debug(sParam + ":" + s);
		}
		if (StringUtils.isEmpty(s)) {
			return nDefault;
		} else {
			try {
				return Integer.valueOf(s);
			} catch (Exception e) {
				logger.warn("getInt:", e);
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
				return Long.valueOf(s);
			} catch (Exception e) {
				logger.warn("getLong:", e);
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
				return Short.valueOf(s);
			} catch (Exception e) {
				logger.warn("getShort:", e);
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
				return Byte.valueOf(s);
			} catch (Exception e) {
				logger.warn("getByte:", e);
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
				return Double.valueOf(s);
			} catch (Exception e) {
				logger.warn("getDouble:", e);
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
				return Float.valueOf(s);
			} catch (Exception e) {
				logger.warn("getFloat:", e);
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
		StringBuilder sb = new StringBuilder();
		Map map = request.getParameterMap();
		Set keSet = map.entrySet();
		boolean bExistsKeyValue = false;
		for (Iterator itr = keSet.iterator(); itr.hasNext();) {
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
			String key = (String)(entry.getKey());
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

}
