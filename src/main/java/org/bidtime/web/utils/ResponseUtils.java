/**
 * 
 */
package org.bidtime.web.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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
			r.setContentType("application/json;charset=UTF-8");
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
	
//	/**
//	 * 帐号未激活
//	 * @param r
//	 * @param msg
//	 */
//	public static void setResponseHeadNoActive(HttpServletResponse r,
//			String msg) {
//		setResponseResultObject(UserHeadState.noPassJsonMsg(
//			UserHeadState.CHK_USER_NO_ACTIVE, msg), r);
//	}
//	
//	/**
//	 * 帐号来激活
//	 * @param r
//	 * @param msg
//	 */
//	public static void setResponseHeadNoCheck(HttpServletResponse r,
//			String msg) {
//		setResponseResultObject(UserHeadState.noPassJsonMsg(
//			UserHeadState.CHK_USER_NO_CHECK, msg), r);
//	}
//	
//	/**
//	 * 帐号来审核
//	 * @param r
//	 * @param msg
//	 */
//	public static void setResponseHeadNoPass(HttpServletResponse r,
//			String msg) {
//		setResponseResultObject(UserHeadState.noPassJsonMsg(
//			UserHeadState.CHK_USER_NO_PASS, msg), r);
//	}
	
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
			r.setContentType("application/json;charset=UTF-8");
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

}