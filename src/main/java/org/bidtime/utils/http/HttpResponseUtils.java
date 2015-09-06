package org.bidtime.utils.http;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.bidtime.web.utils.ResponseUtils;

public class HttpResponseUtils {

	public static void post(String sUrl, String sData,
			HttpServletResponse response) throws IOException {
		http_get_post(sUrl, sData, true, response);
	}

	public static void post(String sUrl, HttpServletResponse response)
			throws IOException {
		http_get_post(sUrl, null, true, response);
	}

	public static void get(String sUrl, String sData,
			HttpServletResponse response) throws IOException {
		http_get_post(sUrl, sData, false, response);
	}

	public static void get(String sUrl, HttpServletResponse response)
			throws IOException {
		http_get_post(sUrl, null, false, response);
	}

	/**
	 * @param sUrl
	 * @param sData
	 * @param bPost
	 * @param response
	 * @throws IOException
	 */
	public static void http_get_post(String sUrl, String sData, boolean bPost,
			HttpServletResponse response) throws IOException {
		String sReturn = null;
		if (bPost) {
			sReturn = HttpUtils.post(sUrl, sData);
		} else {
			sReturn = HttpUtils.get(sUrl, sData);
		}
		ResponseUtils.setResponseResultString(sReturn, response);
	}

}
