/**
 * 
 */
package org.bidtime.web.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.bidtime.dbutils.gson.ResultDTO;
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

	public static void setSqlResultResponse(int applies, String msg,
			HttpServletResponse r) {
		short state = (applies > 0) ? UserHeadState.SUCCESS : UserHeadState.ERROR;
		if (state == UserHeadState.SUCCESS) {
			setStateMsgResponse(state, "", r);
		} else {
			setStateMsgResponse(state, msg, r);
		}
	}

	public static void setSuccessResponse(String msg,
			HttpServletResponse r) {
		setStateMsgResponse(UserHeadState.SUCCESS, msg, r);
	}

	public static void setSuccessResponse(HttpServletResponse r) {
		setStateMsgResponse(UserHeadState.SUCCESS, "", r);
	}

	public static void setErrorMsgResponse(String msg,
			HttpServletResponse r) {
		setStateMsgResponse(UserHeadState.ERROR, msg, r);
	}

	public static void setFrequentMsgResponse(String msg,
			HttpServletResponse r) {
		setStateMsgResponse(UserHeadState.USER_IS_FREQUENT, msg, r);
	}

	public static void setErrorMsgResponse(String msg, Exception e,
			HttpServletResponse r) {
		setErrorMsgResponse(msg	+ ":" + e.getMessage(), r);
	}

	public static void setErrorMsgResponse(Exception e,
			HttpServletResponse r) {
		setStateMsgResponse(UserHeadState.ERROR, e.getMessage(), r);
	}

	public static void setStateMsgResponse(int n, String msg,
			HttpServletResponse r) {
		ResultDTO<Object> d = new ResultDTO<Object>(n, msg);
		setResponseResultString(d.toJsonMsg().toString(), r);
	}

	@SuppressWarnings("rawtypes")
	public static void setResponseResult(ResultDTO d, HttpServletResponse r) {
		if (d == null) {
			return;
		}
		setResponseResultObject(d.toJson(), r);
	}

	public static void setResponseResultObject(Object o, HttpServletResponse r) {
		if (o == null) {
			return;
		}
		setResponseResultString(o.toString(), r);
	}

	/**
	 * @param sReturn
	 * @param r
	 */
	public static void setResponseResultString(String s,
			HttpServletResponse r) {
		try {
			r.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
			r.setHeader("Pragma", "no-cache"); // HTTP 1.0
			r.setDateHeader("Expires", 0); // prevents caching at the proxy server
			r.setCharacterEncoding("UTF-8");
			r.setContentType("text/html;charset=UTF-8");
			// write string
			r.getWriter().write(s);
			// flush buffer
			r.flushBuffer();
			if (logger.isDebugEnabled()) {
				logger.debug(s);
			}
		} catch (IOException e) {
			logger.error("setResponse", e);
		}
	}

	/**
	 * 无权限
	 * 
	 * @param r
	 */
	public static void setResponseHeadNoPower(HttpServletResponse r,
			String msg) {
		setResponseIntHeadString(r, UserHeadState.USER_NO_POWER, msg);
	}

	/**
	 * 未登陆
	 * 
	 * @param r
	 */
	public static void setResponseHeadNoLogin(HttpServletResponse r,
			String msg) {
		setResponseIntHeadString(r, UserHeadState.USER_NO_LOGIN, msg);
	}

	/**
	 * 帐号别处登陆
	 * 
	 * @param r
	 */
	public static void setResponseHeadNoOnLine(HttpServletResponse r,
			String msg) {
		setResponseIntHeadString(r, UserHeadState.USER_NO_ONLINE, msg);
	}
	
	/**
	 * 帐号未激活
	 * @param r
	 * @param msg
	 */
	public static void setResponseHeadNoActive(HttpServletResponse r,
			String msg) {
		setResponseResultObject(UserHeadState.noPassJsonMsg(
			UserHeadState.CHK_USER_NO_ACTIVE, msg), r);
	}
	
	/**
	 * 帐号来激活
	 * @param r
	 * @param msg
	 */
	public static void setResponseHeadNoCheck(HttpServletResponse r,
			String msg) {
		setResponseResultObject(UserHeadState.noPassJsonMsg(
			UserHeadState.CHK_USER_NO_CHECK, msg), r);
	}
	
	/**
	 * 帐号来审核
	 * @param r
	 * @param msg
	 */
	public static void setResponseHeadNoPass(HttpServletResponse r,
			String msg) {
		setResponseResultObject(UserHeadState.noPassJsonMsg(
			UserHeadState.CHK_USER_NO_PASS, msg), r);
	}
	
	/**
	 * 设置state以及GsonEbRst消息
	 * 
	 * @param r
	 * @param nLoginHeadState
	 * @param nUserState
	 * @param msg
	 */
//	public static void setResponseHeadIntMsg1(HttpServletResponse r,
//			int loginHeadState, String msg) {
//		//ResultDTO<Object> d = new ResultDTO<Object>(loginHeadState, msg);
//		setResponseIntHeadString(r, loginHeadState, msg);
//	}

	/**
	 * @param r
	 * @param n
	 */
	private static void setResponseIntHeadString(HttpServletResponse r,
			int intHead, String msg) {
		try {
			r.setCharacterEncoding("UTF-8");
			r.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
			r.setHeader("Pragma", "no-cache"); // HTTP 1.0
			r.setDateHeader("Expires", 0); // prevents caching at the proxy server
			r.setContentType("text/html;charset=UTF-8");
			// set notLogin value
			r.addIntHeader(UserHeadState.NOT_LOGININ, intHead);
			// write msg
			r.getWriter().write(msg);
			// flush buffer
			r.flushBuffer();
			if (logger.isDebugEnabled()) {
				logger.debug(UserHeadState.NOT_LOGININ + ":" + intHead);
				logger.debug(msg);
			}
		} catch (IOException e) {
			logger.error("setResponse", e);
		}
	}

	/**
	 * @param r
	 */
//	public static void setResponseHeadNoLogin(HttpServletResponse r) {
//		setResponseHeadInt(r, UserHeadState.USER_NO_LOGIN); // -1,未登陆
//	}
//
//	/**
//	 * @param r
//	 */
//	public static void setResponseHeadNoOnLine(HttpServletResponse r) {
//		setResponseHeadInt(r, UserHeadState.USER_NO_ONLINE);	//帐号已经登陆
//	}
//
//	/**
//	 * @param r
//	 */
//	public static void setResponseHeadNoOnPower(HttpServletResponse r) {
//		setResponseHeadInt(r, UserHeadState.USER_NO_POWER);//用户无此权限
//	}
//
//	/**
//	 * @param r
//	 */
//	public static void setResponseHeadNoCheck(HttpServletResponse r) {
//		setResponseHeadInt(r, UserHeadState.USER_NO_CHECK);//用户无此权限
//	}

	/**
	 * @param r
	 * @param n
	 */
//	private static void setResponseHeadInt(HttpServletResponse r, int n) {
//		r.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
//		r.setHeader("Pragma", "no-cache"); // HTTP 1.0
//		r.setDateHeader("Expires", 0); // prevents caching at the proxy server
//		r.setCharacterEncoding("UTF-8");
//		r.setContentType("text/html;charset=UTF-8");
//		// set notLogin value
//		r.addIntHeader(UserHeadState.NOT_LOGININ, n);
//		if (logger.isDebugEnabled()) {
//			logger.debug(UserHeadState.NOT_LOGININ + ":" + n);
//		}
//	}

}