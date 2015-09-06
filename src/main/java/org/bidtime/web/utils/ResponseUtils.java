/**
 * 
 */
package org.bidtime.web.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.bidtime.dbutils.gson.GsonEbParams;
import org.bidtime.dbutils.gson.ResultDTO;
import org.bidtime.utils.http.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jss
 * 
 *         提供对Response的操作,往Response输出各种对象
 *
 */
public class ResponseUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(ResponseUtils.class);

	private static short getStateOfSqlUpdate(int nResults) {
		if (nResults > 0) {
			return GsonEbParams.STATE_SUCCESS;
		} else {
			return GsonEbParams.STATE_FAILURE;
		}
	}

	public static void setSqlResultResponse(int nUpdates, String msg,
			HttpServletResponse response) {
		ResultDTO<Object> gsonEbRst = null;
		short nState = getStateOfSqlUpdate(nUpdates);
		if (nState == GsonEbParams.STATE_SUCCESS) {
			gsonEbRst = new ResultDTO<Object>(nState, "");
		} else {
			gsonEbRst = new ResultDTO<Object>(nState, msg);
		}
		setResponseResult(gsonEbRst, response);
	}

	public static void setSuccessResponse(String msg,
			HttpServletResponse response) {
		ResultDTO<Object> gsonEbRst = new ResultDTO<Object>(GsonEbParams.STATE_SUCCESS, msg);
		setResponseResult(gsonEbRst, response);
	}

	public static void setSuccessResponse(HttpServletResponse response) {
		setSuccessResponse("", response);
	}

	public static void setErrorMsgResponse(String msg,
			HttpServletResponse response) {
		setStateMsgResponse(GsonEbParams.STATE_FAILURE, msg, response);
	}

	public static void setFrequentMsgResponse(String msg,
			HttpServletResponse response) {
		ResultDTO<Object> gsonEbRst = new ResultDTO<Object>(GsonEbParams.STATE_USER_IS_FREQUENT, msg);
		setResponseResult(gsonEbRst, response);
	}

	public static void setErrorMsgResponse(String msg, Exception e,
			HttpServletResponse response) {
		setErrorMsgResponse(msg	+ ":" + e.getMessage(), response);
	}

	public static void setErrorMsgResponse(Exception e,
			HttpServletResponse response) {
		setStateMsgResponse(GsonEbParams.STATE_FAILURE, e.getMessage(), response);
	}

	public static void setStateMsgResponse(int n, String msg,
			HttpServletResponse response) {
		ResultDTO<Object> gsonEbRst = new ResultDTO<Object>(n, msg);
		setResponseResult(gsonEbRst, response);
	}

	@SuppressWarnings("rawtypes")
	public static void setResponseResult(ResultDTO r, HttpServletResponse response) {
		if (r == null) {
			return;
		}
		setResponseResultObject(r.toJson(), response);
	}

	public static void setResponseResultObject(Object o,
			HttpServletResponse response) {
		if (o == null) {
			return;
		}
		setResponseResultString(o.toString(), response);
	}

	/**
	 * @param sReturn
	 * @param response
	 */
	public static void setResponseResultString(String sReturn,
			HttpServletResponse response) {
		try {
			response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setDateHeader("Expires", 0); // prevents caching at the
													// proxy server
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(sReturn);
			response.flushBuffer();
			if (logger.isDebugEnabled()) {
				logger.debug(sReturn);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 无权限
	 * 
	 * @param response
	 */
	public static void setResponseHeadNotPower(HttpServletResponse response,
			String msg) {
		setResponseHeadIntMsg(response, SessionUtils.USER_NOT_POWER,
				GsonEbParams.STATE_USER_NOT_POWER, msg);
	}

	/**
	 * 未登陆
	 * 
	 * @param response
	 */
	public static void setResponseHeadNotLogin(HttpServletResponse response,
			String msg) {
		setResponseHeadIntMsg(response, SessionUtils.USER_NOT_LOGIN,
				GsonEbParams.STATE_USER_NOT_LOGIN, msg);
	}

	/**
	 * 帐号别处登陆
	 * 
	 * @param response
	 */
	public static void setResponseHeadNotOnLine(HttpServletResponse response,
			String msg) {
		setResponseHeadIntMsg(response, SessionUtils.USER_NOT_ONLINE,
				GsonEbParams.STATE_USER_NOT_ONLINE, msg);
	}

	/**
	 * 设置state以及GsonEbRst消息
	 * 
	 * @param response
	 * @param nLoginHeadState
	 * @param nUserState
	 * @param msg
	 */
	public static void setResponseHeadIntMsg(HttpServletResponse response,
			int nLoginHeadState, int nUserState, String msg) {
		ResultDTO<Object> r = new ResultDTO<Object>(nLoginHeadState, msg);
		setResponseHeadIntMsgRaw(response, nLoginHeadState, r.toJson().toString());
	}

	/**
	 * @param response
	 * @param n
	 */
	private static void setResponseHeadIntMsgRaw(HttpServletResponse response,
			int n, String msg) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setDateHeader("Expires", 0); // prevents caching at the
													// proxy
													// server
			response.setContentType("text/html;charset=UTF-8");
			// set notLogin value
			response.addIntHeader(SessionUtils.NOT_LOGIN_IN, n);
			// write msg
			response.getWriter().write(msg);
			response.flushBuffer();
			if (logger.isDebugEnabled()) {
				logger.debug(SessionUtils.NOT_LOGIN_IN + ":" + n);
				logger.debug(msg);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * @param response
	 */
	public static void setResponseHeadNotLogin(HttpServletResponse response) {
		setResponseHeadInt(response, SessionUtils.USER_NOT_LOGIN); // -1,未登陆
	}

	/**
	 * @param response
	 */
	public static void setResponseHeadNotOnLine(HttpServletResponse response) {
		setResponseHeadInt(response, SessionUtils.USER_NOT_ONLINE);	//帐号已经登陆
	}

	/**
	 * @param response
	 */
	public static void setResponseHeadNotOnPower(HttpServletResponse response) {
		setResponseHeadInt(response, SessionUtils.USER_NOT_POWER);//用户无此权限
		// setStateMsgResponse(0, "无权限", response); 
	}

	/**
	 * @param response
	 * @param n
	 */
	private static void setResponseHeadInt(HttpServletResponse response, int n) {
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // prevents caching at the proxy
												// server
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// set notLogin value
		response.addIntHeader(SessionUtils.NOT_LOGIN_IN, n);
		if (logger.isDebugEnabled()) {
			logger.debug(SessionUtils.NOT_LOGIN_IN + ":" + n);
		}
	}

}