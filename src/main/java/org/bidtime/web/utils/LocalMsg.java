package org.bidtime.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.bidtime.utils.spring.SpringMessageUtils;

public class LocalMsg {

	// /////////////////////////////////////////////////////////////////////////////////////////////
	// 消息

	public static String get(String msgId, HttpServletRequest request) {
		return SpringMessageUtils.getMessage(request, msgId);
	}

	public static String get(String msgId, String defaultValue,
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
	
	public static String getUploadPicError(HttpServletRequest request) {
		return getUploadError("controller.pic", request);
	}
	
	public static String getDownloadPicError(HttpServletRequest request) {
		return getDownloadError("controller.pic", request);
	}
	
	public static String getDownloadDataError(HttpServletRequest request) {
		return getDownloadError("controller.data", request);
	}
	
	public static String getUploadDataError(HttpServletRequest request) {
		return getUploadError("controller.data", request);
	}
	
	public static String getCancelDataError(HttpServletRequest request) {
		return getCancelError("controller.data", request);
	}
	
	public static String getCheckDataError(HttpServletRequest request) {
		return getCheckError("controller.data", request);
	}
	
	public static String getApplyDataError(HttpServletRequest request) {
		return getApplyError("controller.data", request);
	}

	public static String getSaveDataError(HttpServletRequest request) {
		return getSaveError("controller.data", request);
	}

	/**
	 * 读取错误数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getReadDataError(HttpServletRequest request) {
		return getReadError("controller.data", request);
	}

	/**
	 * 读取错误Json数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getReadJsonDataError(HttpServletRequest request) {
		return getReadError("controller.json.data", request);
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
	public static String getReadError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.read.error", paramId,
				request);
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
	public static String getCancelError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.cancel.error", paramId,
				request);
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
	public static String getUploadError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.upload.error", paramId,
				request);
	}

	public static String getDownloadError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.download.error", paramId,
				request);
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
	public static String getApplyError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.apply.error", paramId,
				request);
	}

	public static String getSaveError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.save.error", paramId,
				request);
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
	public static String getCheckError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.check.error", paramId,
				request);
	}

	/**
	 * 写错误数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getWriteDataError(HttpServletRequest request) {
		return getWriteError("controller.data", request);
	}

	/**
	 * 写错误Json数据的格式化信息
	 * 
	 * @param request
	 *            : Http request
	 * @return
	 */
	public static String getWriteJsonDataError(HttpServletRequest request) {
		return getWriteError("controller.json.data", request);
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
	public static String getWriteError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.write.error", paramId,
				request);
	}

	/**
	 * 得到查找数据格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getFindDataError(HttpServletRequest request) {
		return getFindError("controller.data", request);
	}

	public static String getInputParamError(HttpServletRequest request) {
		return getInputError("controller.param", request);
	}
	
	public static String getInputError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.input.error", paramId,
				request);
	}

	/**
	 * 得到查找格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getFindError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.find.error", paramId,
				request);
	}

	/**
	 * 得到删除数据格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getDeleteDataError(HttpServletRequest request) {
		return getDeleteError("controller.data", request);
	}

	/**
	 * 得到删除格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getDeleteError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.delete.error", paramId,
				request);
	}

	/**
	 * 得到增加数据格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getInsertDataError(HttpServletRequest request) {
		return getInsertError("controller.data", request);
	}

	/**
	 * 得到增加格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getInsertError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.insert.error", paramId,
				request);
	}

	/**
	 * 得到修改数据格式化消息
	 * 
	 * @param request
	 * @return
	 */
	public static String getUpdateDataError(HttpServletRequest request) {
		return getUpdateError("controller.data", request);
	}

	/**
	 * 得到修改格式化消息
	 * 
	 * @param paramId
	 * @param request
	 * @return
	 */
	public static String getUpdateError(String paramId,
			HttpServletRequest request) {
		return getMessageLocalId("controller.edit.error", paramId,
				request);
	}

}
