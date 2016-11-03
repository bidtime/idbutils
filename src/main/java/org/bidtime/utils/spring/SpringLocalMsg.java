/**
 * 
 */
package org.bidtime.utils.spring;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.bidtime.dbutils.jdbc.sql.xml.TableFieldXmlsParser;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

/**
 * @author jss
 * 
 * 提供对Spring消息国际化的处理
 *
 */
public class SpringLocalMsg {

	public static String getMessage(HttpServletRequest request, String message) {
		return getMessage(request, message, null, "",
				TableFieldXmlsParser.getCtx());
	}

	public static String getMessage(HttpServletRequest request, String message,
			String defaultValue) {
		return getMessage(request, message, null, defaultValue,
				TableFieldXmlsParser.getCtx());
	}

	public static String getMessage(HttpServletRequest request, String message,
			Object[] params) {
		return getMessage(request, message, params, "",
				TableFieldXmlsParser.getCtx());
	}

	// private static String getMessage(HttpServletRequest request,
	// String message, Object[] params, ApplicationContext context) {
	// RequestContext requestContext = new RequestContext(request);
	// Locale locale = requestContext.getLocale();
	// String ss = SpringUtils.getContext()
	// .getMessage("text.menu.name", params, "", locale);
	// return ss;
	// }

	public static String getMessage(HttpServletRequest request, String message,
			Object[] params, String defaultValue) {
		return getMessage(request, message, params, defaultValue,
				TableFieldXmlsParser.getCtx());
	}

	private static String getMessage(HttpServletRequest request,
			String message, Object[] params, String defaultValue,
			ApplicationContext context) {
		RequestContext requestContext = new RequestContext(request);
		Locale locale = requestContext.getLocale();

		String ss = TableFieldXmlsParser.getCtx().getMessage(message, params,
				defaultValue, locale);
		return ss;
	}

	// 以下是格式化的方法

	public static String getMessageLocal(HttpServletRequest request,
			String message) {
		return getMessageLocal(request, message, null, "",
				TableFieldXmlsParser.getCtx());
	}

	public static String getMessageLocal(HttpServletRequest request,
			String message, String defaultValue) {
		return getMessageLocal(request, message, null, defaultValue,
				TableFieldXmlsParser.getCtx());
	}

	public static String getMessageLocal(HttpServletRequest request,
			String message, Object[] params) {
		return getMessageLocal(request, message, params, "",
				TableFieldXmlsParser.getCtx());
	}

	public static String getMessageLocalId(HttpServletRequest request,
			String message, String sParamsId) {
		return getMessageLocal(request, message, new Object[] { sParamsId },
				"", TableFieldXmlsParser.getCtx());
	}

	// private static String getMessage(HttpServletRequest request, String
	// message, Object[] params, ApplicationContext context) {
	// RequestContext requestContext = new RequestContext(request);
	// Locale locale = requestContext.getLocale();
	// String ss =
	// SpringUtils.getContext().getMessage("text.menu.name",
	// params, "", locale);
	// return ss;
	// }

	public static String getMessageLocal(HttpServletRequest request,
			String message, Object[] params, String defaultValue) {
		return getMessageLocal(request, message, params, defaultValue,
				TableFieldXmlsParser.getCtx());
	}

	/**
	 * 从国际化文件中取对应的值
	 * 
	 * @param request
	 * @param message
	 *            : message id
	 * @param params
	 *            : format params id,格式化的值
	 * @param defaultValue
	 *            : default value
	 * @param context
	 *            : applicationContext
	 * @return: string
	 */
	private static String getMessageLocal(HttpServletRequest request,
			String message, Object[] params, String defaultValue,
			ApplicationContext context) {
		RequestContext requestContext = new RequestContext(request);
		Locale locale = requestContext.getLocale();
		Object[] paramsLocal = null;
		if (params != null && params.length > 0) {
			paramsLocal = new Object[params.length];
			for (int i = 0; i < paramsLocal.length; i++) {
				paramsLocal[i] = TableFieldXmlsParser.getCtx().getMessage(
						String.valueOf(params[i]), null, "", locale); // .US
			}
		}
		String ss = TableFieldXmlsParser.getCtx().getMessage(message,
				paramsLocal, defaultValue, locale); // .US
		if (ss == null) {
			ss = "";
		}
		return ss;
	}

}
