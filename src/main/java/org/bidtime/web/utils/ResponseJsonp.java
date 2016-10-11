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
	
	public static void setCookies(HttpServletRequest req, HttpServletResponse res) {
		Cookie[] cs = req.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				res.addCookie(c);
			}
		}
	}
	
	public static void writeString(HttpServletRequest req, HttpServletResponse res, String s) {
		String callback_name = RequestUtils.getString(req, CALLBACK_FUN);
		if (StringUtils.isNotEmpty(callback_name)) {
			req.getCookies();
			StringBuilder sb = new StringBuilder();
			sb.append(callback_name);
			sb.append("(");
			sb.append(s);
			sb.append(")");
			setCookies(req, res);
			ResponseUtils.writeString(sb.toString(), res);
		} else {
			ResponseUtils.writeString(s, res);
		}
	}

}
