package org.bidtime.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
public class ResponseJsonp {
	
	//private static final Logger logger = LoggerFactory
	//		.getLogger(ResponseJsonp.class);
	
	private static final String CALLBACK_FUN = "callbackFun";
	//private static final int SQL_ZERO = 0;

//	@SuppressWarnings("rawtypes")
//	public static void setResponseResult(ResultDTO o,
//			HttpServletRequest request, HttpServletResponse response) {
//		String callback_name = RequestUtils.getString(request, CALLBACK_FUN);
//		if (StringUtils.isNotEmpty(callback_name)) {
//			if (o == null) {
//				return;
//			}
//			StringBuilder sb = new StringBuilder();
//			sb.append(callback_name);
//			sb.append("(");
//			sb.append(o.toJson().toString());
//			sb.append(")");
//			ResponseUtils.setResponseResultString(sb.toString(), response);
//		} else {
//			ResponseUtils.setResponseResult(o, response);
//		}
//	}
	
	public static void setCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				response.addCookie(c);
			}
		}
	}
	
	public static void writeString(HttpServletRequest request, HttpServletResponse response, 
			String msg) {
		String callback_name = RequestUtils.getString(request, CALLBACK_FUN);
		if (StringUtils.isNotEmpty(callback_name)) {
			request.getCookies();
			StringBuilder sb = new StringBuilder();
			sb.append(callback_name);
			sb.append("(");
			sb.append(msg);
			sb.append(")");
			setCookies(request, response);
			ResponseUtils.writeString(sb.toString(), response);
		} else {
			ResponseUtils.writeString(msg, response);
		}
	}

}