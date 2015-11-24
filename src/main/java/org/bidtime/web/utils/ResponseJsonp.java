package org.bidtime.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ResponseJsonp {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ResponseJsonp.class);
	
	private static final String CALLBACK_FUN = "callbackFun";
	private static final int SQL_ZERO = 0;

	@SuppressWarnings("rawtypes")
	public static void setResponseResult(ResultDTO o,
			HttpServletRequest request, HttpServletResponse response) {
		String callback_name = RequestUtils.getString(request, CALLBACK_FUN);
		if (StringUtils.isNotEmpty(callback_name)) {
			if (o == null) {
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(callback_name);
			sb.append("(");
			sb.append(o.toJson().toString());
			sb.append(")");
			ResponseUtils.setResponseResultString(sb.toString(), response);
		} else {
			ResponseUtils.setResponseResult(o, response);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void setSqlResultResponse(int applies, String msg,
			HttpServletRequest request, HttpServletResponse response) {
		ResultDTO gsonEbRst = null;
		short nState = (applies > SQL_ZERO) ? UserHeadState.SUCCESS : UserHeadState.ERROR;
		if (nState == UserHeadState.SUCCESS) {
			gsonEbRst = new ResultDTO(nState, "");
		} else {
			gsonEbRst = new ResultDTO(nState, msg);
		}
		setResponseResult(gsonEbRst, request, response);
	}

	public static void setStateMsgResponse(int n, String msg,
			HttpServletRequest request, HttpServletResponse response) {
		ResultDTO<Object> r = new ResultDTO<Object>(n, msg);
		setResponseResult(r, request, response);
	}

	public static void setSuccessResponse(String msg,
			HttpServletRequest request, HttpServletResponse response) {
		setStateMsgResponse(UserHeadState.SUCCESS, msg, request, response);
	}

	public static void setSuccessResponse(HttpServletRequest request, HttpServletResponse response) {
		ResultDTO<Object> r = new ResultDTO<Object>(UserHeadState.SUCCESS, "");
		setResponseResult(r, request, response);
	}

	public static void setErrorMsgResponse(String msg,
			HttpServletRequest request, HttpServletResponse response) {
		ResultDTO<Object> r = new ResultDTO<Object>(UserHeadState.ERROR, msg);
		setResponseResult(r, request, response);
	}

	public static void setErrorMsgResponse(String msg, Exception e,
			HttpServletRequest request, HttpServletResponse response) {
		setErrorMsgResponse(msg + ":" + e.getMessage(), request, response);
	}
	/**
	 * @param response
	 */
	public static void setResponseHeadNotLogin(HttpServletResponse response) {
		setResponseHeadInt(response, UserHeadState.USER_NO_LOGIN); // -1,未登陆
		// setStateMsgResponse(-1, "未登陆", response); //此为测试时使用的代码,使用客户端显示未登陆消息
	}

	/**
	 * @param response
	 */
	public static void setResponseHeadNotOnLine(HttpServletResponse response) {
		setResponseHeadInt(response, UserHeadState.USER_NO_ONLINE);
		// setStateMsgResponse(1, "帐号已经登陆", response); //帐号已经登陆
	}

	/**
	 * @param response
	 */
	public static void setResponseHeadNotOnPower(HttpServletResponse response) {
		setResponseHeadInt(response, UserHeadState.USER_NO_POWER);
		// setStateMsgResponse(0, "无权限", response); //此为测试时使用的代码,使用客户端显示无权限消息
	}
	
	private static void setResponseHeadInt(HttpServletResponse response, int n) {
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // prevents caching at the proxy
												// server
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// set notLogin value
		response.addIntHeader(UserHeadState.NOT_LOGININ, n);
		if (logger.isDebugEnabled()) {
			logger.debug(UserHeadState.NOT_LOGININ + ":" + n);
		}
	}

	/**
	 * 无权限
	 * @param response
	 */
	public static void setResponseHeadNotPower(HttpServletRequest request, HttpServletResponse response, String msg) {
		setResponseHeadIntMsg(request, response, UserHeadState.USER_NO_POWER, UserHeadState.USER_NO_POWER, msg);
	}

	/**
	 * 未登陆
	 * @param response
	 */
	public static void setResponseHeadNotLogin(HttpServletRequest request, HttpServletResponse response, String msg) {
		setResponseHeadIntMsg(request, response, UserHeadState.USER_NO_LOGIN, UserHeadState.USER_NO_LOGIN, msg);
	}

	/**
	 * 帐号别处登陆
	 * @param response
	 */
	public static void setResponseHeadNotOnLine(HttpServletRequest request, HttpServletResponse response, String msg) {
		setResponseHeadIntMsg(request, response, UserHeadState.USER_NO_ONLINE, UserHeadState.USER_NO_ONLINE, msg);
	}
	
	private static void setResponseHeadIntMsg(HttpServletRequest request, HttpServletResponse response, 
			int nLoginHeadState, int nUserState, String msg) {
//		String callback_name = RequestUtils.getString(request, CALLBACK_FUN);
//		if (StringUtils.isNotEmpty(callback_name)) {
//			GsonEbRst rst = new GsonEbRst(nUserState, msg);			
//			StringBuilder sb = new StringBuilder();
//			sb.append(callback_name);
//			sb.append("(");
//			//sb.append(GsonComm.toJson(rst, rst.getClass(), true));
//			sb.append(")");
//			setResponseHeadIntMsgRaw(response, nLoginHeadState, sb.toString());
//		} else {
//			ResponseUtils.setResponseHeadIntMsg(response, nLoginHeadState, nUserState, msg);
//		}
	}
	
	/**
	 * @param response
	 * @param n
	 */
//	private static void setResponseHeadIntMsgRaw(HttpServletResponse response, int n, String msg) {
//		try {
//			response.setCharacterEncoding("UTF-8");
//			response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
//			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
//			response.setDateHeader("Expires", 0); // prevents caching at the proxy
//													// server
//			response.setContentType("text/html;charset=UTF-8");
//			// set notLogin value
//			response.addIntHeader(SessionUtils.NOT_LOGIN_IN, n);
//			// write msg
//			response.getWriter().write(msg);
//			response.flushBuffer();
//			if (logger.isDebugEnabled()) {
//				logger.debug(SessionUtils.NOT_LOGIN_IN + ":" + n);
//				logger.debug(msg);
//			}
//		} catch (IOException e) {
//			logger.error("setResponseHeadIntMsgRaw:"+e.getMessage());
//		}
//	}

}
