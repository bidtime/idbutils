package org.bidtime.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.JSONHelper;
import org.bidtime.dbutils.gson.dataset.ParserDataSet;
import org.bidtime.utils.basic.CArrayComm;
import org.bidtime.utils.spring.SpringMessageUtils;
import org.json.JSONObject;
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
	public final static short LIMIT_TEN_PAGE_SIZE = 10;
	public final static short LIMIT_PAGE_SIZE = 100;
	public final static String COMMA = ",";

	private static final Logger logger = LoggerFactory
			.getLogger(RequestUtils.class);

	public static Short getRequestValidPageSize(Short pageSize) {
		return getRequestValidPageSize(pageSize, LIMIT_PAGE_SIZE);
	}

	public static Short getRequestValidPageSize(Short pageSize, Short nDefault) {
		if (pageSize != null) {
			if (pageSize > nDefault) {
				return nDefault;
			} else {
				return pageSize;
			}
		} else {
			return LIMIT_TEN_PAGE_SIZE;
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
	
	public static ParserDataSet getPaserDataSetOfRequest(HttpServletRequest request, String sParam) throws Exception {
		String data = request.getParameter(sParam);
		ParserDataSet g = ParserDataSet.jsonStrToObject(data);
		if (g == null) {
			throw new Exception("json data is null");
		} else {
			return g;
		}
	}
	
	public static Object getPaserJsonOfRequest(HttpServletRequest request, String sParam) throws Exception {
		String data = request.getParameter(sParam);
		JSONObject jsonObject = new JSONObject(data);
		Object o = JSONHelper.jsonObjToObj(jsonObject);
		if (o == null) {
			throw new Exception("json data is null");
		} else {
			return o;
		}
	}
	
	public static Map<String, Object> getPaserJsonMapOfRequest(HttpServletRequest request, String sParam) throws Exception {
		JSONObject jsonObject = (JSONObject)getPaserJsonOfRequest(request, sParam);
		if (jsonObject != null) {
			return JSONHelper.jsonToMap(jsonObject);
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
			return CArrayComm.StringsToLongArray(sId.split(splitChar));
		}
	}

	public static Integer[] getIntegerSplit(HttpServletRequest request, String sParam,
			String splitChar) throws Exception {
		String sId = getString(request, sParam);
		if (sId == null) {
			throw new Exception("params is null");
		} else {
			return CArrayComm.StringsToIntegerArray(sId.split(splitChar));
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

	public static Integer getInt(HttpServletRequest request, String sParam) {
		return getInt(request, sParam, null);
	}

	public static Integer getInt(HttpServletRequest request, String sParam,
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

	private static Date yymmddToDate(String sDate) {
		// String s2 = "1996-02-45"; // yyyyMMdd
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
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
			return yymmddToDate(s);
		}
	}

	public static String toString(Object o) {
		return o == null ? null : o.toString();
	}

	public static boolean isEmpty(String s) {
		return s == null || s.equals("");
	}

	public static String repeat(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////
	// 消息

	public static String getMessage(String msgId, HttpServletRequest request) {
		return SpringMessageUtils.getMessage(request, msgId);
	}

	public static String getMessage(String msgId, String defaultValue,
			HttpServletRequest request) {
		return SpringMessageUtils.getMessage(request, msgId, defaultValue);
	}

	public static String getMessageLocalId(String msgId, String paramId,
			HttpServletRequest request) {
		return SpringMessageUtils.getMessageLocalId(request, msgId, paramId);
	}

	public static String getMessageLocal(String msgId, Object[] params,
			HttpServletRequest request) {
		return SpringMessageUtils.getMessageLocal(request, msgId, params);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getUploadPicErrorMessage(HttpServletRequest request) {
		return getUploadErrorMessage("controller.pic", request);
	}
	
	public static String getUploadDataErrorMessage(HttpServletRequest request) {
		return getUploadErrorMessage("controller.data", request);
	}
	
	public static String getCancelDataErrorMessage(HttpServletRequest request) {
		return getCancelErrorMessage("controller.data", request);
	}
	
	public static String getCheckDataErrorMessage(HttpServletRequest request) {
		return getCheckErrorMessage("controller.data", request);
	}
	
	public static String getApplyDataErrorMessage(HttpServletRequest request) {
		return getApplyErrorMessage("controller.data", request);
	}

	/**
	 * 读取错误数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getReadDataErrorMessage(HttpServletRequest request) {
		return getReadErrorMessage("controller.data", request);
	}

	/**
	 * 读取错误Json数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getReadJsonDataErrorMessage(HttpServletRequest request) {
		return getReadErrorMessage("controller.json.data", request);
	}

	/**
	 * 读取错误的格式化信息
	 * 
	 * @param paramId
	 *            : 格式化参数的Id
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getReadErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.read.error", paramId,
				request);
		return msg;
	}

	/**
	 * 读取错误的格式化信息
	 * 
	 * @param paramId
	 *            : 格式化参数的Id
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getCancelErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.cancel.error", paramId,
				request);
		return msg;
	}

	/**
	 * 读取错误的格式化信息
	 * 
	 * @param paramId
	 *            : 格式化参数的Id
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getUploadErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.upload.error", paramId,
				request);
		return msg;
	}

	/**
	 * 读取错误的格式化信息
	 * 
	 * @param paramId
	 *            : 格式化参数的Id
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getApplyErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.apply.error", paramId,
				request);
		return msg;
	}

	/**
	 * 读取错误的格式化信息
	 * 
	 * @param paramId
	 *            : 格式化参数的Id
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getCheckErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.check.error", paramId,
				request);
		return msg;
	}

	/**
	 * 写错误数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getWriteDataErrorMessage(HttpServletRequest request) {
		return getWriteErrorMessage("controller.data", request);
	}

	/**
	 * 写错误Json数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getWriteJsonDataErrorMessage(HttpServletRequest request) {
		return getWriteErrorMessage("controller.json.data", request);
	}

	/**
	 * 写错误的格式化信息
	 * 
	 * @param paramId
	 *            : 格式化参数的Id
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getWriteErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.write.error", paramId,
				request);
		return msg;
	}

	/**
	 * 得到查找数据格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getFindDataErrorMessage(HttpServletRequest request) {
		return getSqlFindErrorMessage("controller.data", request);
	}

	/**
	 * 得到查找格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getSqlFindErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.find.error", paramId,
				request);
		return msg;
	}

	/**
	 * 得到删除数据格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getSqlDeleteDataErrorMessage(HttpServletRequest request) {
		return getSqlDeleteErrorMessage("controller.data", request);
	}

	/**
	 * 得到删除格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getSqlDeleteErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.delete.error", paramId,
				request);
		return msg;
	}

	/**
	 * 得到增加数据格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getSqlInsertDataErrorMessage(HttpServletRequest request) {
		return getSqlInsertErrorMessage("controller.data", request);
	}

	/**
	 * 得到增加格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getSqlInsertErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.insert.error", paramId,
				request);
		return msg;
	}

	/**
	 * 得到修改数据格式化消息
	 * 
	 * @param request
	 * @return
	 */
	public static String getSqlUpdateDataErrorMessage(HttpServletRequest request) {
		return getSqlUpdateErrorMessage("controller.data", request);
	}

	/**
	 * 得到修改数据格式化消息
	 * 
	 * @param request
	 * @return
	 */
	public static String getSqlCheckDataErrorMessage(HttpServletRequest request) {
		return getSqlCheckErrorMessage("controller.data", request);
	}

	/**
	 * 得到修改格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getSqlUpdateErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.edit.error", paramId,
				request);
		return msg;
	}

	/**
	 * 得到审核格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getSqlCheckErrorMessage(String paramId,
			HttpServletRequest request) {
		String msg = getMessageLocalId("controller.check.error", paramId,
				request);
		return msg;
	}
	
	/////////////////////////////////////////////////////////////////////////////

	public static String UrlEncodeUtf8(String s) {
		return UrlEncode(s, "UTF-8");
	}

	public static String UrlEncode(String s, String sEncode) {
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

}
