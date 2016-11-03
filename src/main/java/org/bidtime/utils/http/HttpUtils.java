package org.bidtime.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(HttpUtils.class);
	
	private static final String GET_METHOD = "GET";
	private static final String POST_METHOD = "POST";
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String CHARSET_GBK = "GBK";
	private static final int BUFFER_SIZE = 2048;
	
	public static String urlEncodeUtf8(String s) throws UnsupportedEncodingException {
		 return java.net.URLEncoder.encode(s, CHARSET_UTF8);
	}

	public static String urlDecodeUtf8(String s) throws UnsupportedEncodingException {
		 return java.net.URLDecoder.decode(s, CHARSET_UTF8);
	}
	
	public static String urlEncodeGbk(String s) throws UnsupportedEncodingException {
		 return java.net.URLEncoder.encode(s, CHARSET_GBK);
	}

	public static String urlDecodeGbk(String s) throws UnsupportedEncodingException {
		 return java.net.URLDecoder.decode(s, CHARSET_GBK);
	}
	
	public static String urlEncode(String s, String encode) throws UnsupportedEncodingException {
		 return java.net.URLEncoder.encode(s, encode);
	}

	public static String urlDecode(String s, String decode) throws UnsupportedEncodingException {
		 return java.net.URLDecoder.decode(s, decode);
	}

	/**
	 * @param sUrl
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public static String get(String sUrl, String context) throws IOException {
		return http_get_post(sUrl +"?" + context, null, GET_METHOD);
	}

	/**
	 * @param sUrl
	 * @return
	 * @throws IOException
	 */
	public static String get(String sUrl) throws IOException {
		return http_get_post(sUrl, null, GET_METHOD);
	}

	public static String get_gbk(String sUrl) throws IOException {
		return http_get_post_gbk(sUrl, null, GET_METHOD);
	}

	public static String get_charset(String sUrl, String charset) throws IOException {
		return http_get_post_charset(sUrl, null, GET_METHOD, charset);
	}

	/**
	 * @param sUrl
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public static String post(String sUrl, String context) throws IOException {
		return http_get_post(sUrl, context, POST_METHOD);
	}

	public static String post_charset(String sUrl, String context, String charset) throws IOException {
		return http_get_post_charset(sUrl, context, POST_METHOD, charset);
	}

	public static String post_gbk(String sUrl, String context) throws IOException {
		return http_get_post_gbk(sUrl, context, POST_METHOD);
	}

	/**
	 * @param sUrl
	 * @param context
	 * @param sMethod
	 * @return
	 * @throws IOException
	 */
	public static String http_get_post(String sUrl, String context,
			String sMethod) throws IOException {
		return http_get_post_charset(sUrl, context, sMethod, CHARSET_UTF8);
	}

	public static String http_get_post_gbk(String sUrl, String context,
			String sMethod) throws IOException {
		return http_get_post_charset(sUrl, context, sMethod, CHARSET_GBK);
	}

	/**
	 * @param sUrl
	 * @param context
	 * @param sMethod
	 * @return
	 * @throws IOException
	 */
	public static String http_get_post_charset(String sUrl, String context,
			String sMethod, String charset) throws IOException {
		logger.debug(sMethod + ":" + sUrl);
		logger.debug("context:" + context);
		URL url = new URL(sUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try {
			conn.setRequestMethod(sMethod);
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Foxy/1; .NET CLR 2.0.50727; MEGAUPLOAD 1.0)");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			// conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			// conn.setRequestProperty(key, value)
			conn.setRequestProperty("Charset", charset);
			//
			conn.setDoInput(true);
			conn.setUseCaches(false);
			if (StringUtils.isNotEmpty(context)) {
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Length", "" + context.length());
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream(), charset);
				try {
					out.write(context);
				} finally {
					out.flush();
					out.close();
				}
			}
			conn.connect();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				logger.info("connect failed!");
				return null;
			} else {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int n;
				// no need unzip
				InputStream in = conn.getInputStream();
				try {
					while ((n = in.read(buffer)) >= 0) {
						out.write(buffer, 0, n);
					}
				} finally {
					in.close();
					out.flush();
					out.close();
				}
				String result = out.toString(charset);
				logger.debug(result);
				return result;
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
//	public static void main(String[] args) {
//		String sUrl = "http://192.168.1.15:8080/CarWeb/carColor/find/all/0/10";
//		try {
//			String ss = HttpUtils.get(sUrl);
//			SessionUtils.listMapSession();
//		} catch (IOException e) {
//		}
//	}
	
}
