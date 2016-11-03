package org.bidtime.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpGzipUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(HttpGzipUtils.class);
	
	private static final String GET_METHOD = "GET";
	private static final String POST_METHOD = "POST";
	private static final String ENCODING_UTF8 = "UTF-8";
	private static final int BUFFER_SIZE = 2048;

	/**
	 * @param sUrl
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public static String get(String sUrl, String context) throws IOException {
		return http_get_post(sUrl, context, GET_METHOD);
	}

	/**
	 * @param sUrl
	 * @return
	 * @throws IOException
	 */
	public static String get(String sUrl) throws IOException {
		return http_get_post(sUrl, null, GET_METHOD);
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

	/**
	 * @param sUrl
	 * @param context
	 * @param sMethod
	 * @return
	 * @throws IOException
	 */
	public static String http_get_post(String sUrl, String context,
			String sMethod) throws IOException {
		logger.info(sMethod + ":" + sUrl);
		logger.info("context:" + context);
		URL url = new URL(sUrl);
		// String sessionId = "";
		boolean bGetSessionId = false;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try {
			conn.setRequestMethod(sMethod);
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Foxy/1; .NET CLR 2.0.50727; MEGAUPLOAD 1.0)");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			// conn.setRequestProperty(key, value)
			conn.setRequestProperty("Charsert", ENCODING_UTF8);
			//
			bGetSessionId = SessionUtils.autoGetMapSessionIdOfConn(conn);
			//
			conn.setDoInput(true);
			conn.setUseCaches(false);
			if (StringUtils.isNotEmpty(context)) {
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Length", "" + context.length());
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream(), ENCODING_UTF8);
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
				// InputStream in = conn.getInputStream();
				// try {
				// while ((n = in.read(buffer)) >= 0) {
				// out.write(buffer, 0, n);
				// }
				// } finally {
				// in.close();
				// out.flush();
				// out.close();
				// }
				// un gzip
				GZIPInputStream gunzip = new GZIPInputStream(
						conn.getInputStream());
				try {
					while ((n = gunzip.read(buffer)) >= 0) {
						out.write(buffer, 0, n);
					}
				} finally {
					gunzip.close();
					out.flush();
					out.close();
				}
				String result = out.toString(ENCODING_UTF8);
				logger.debug(result);
				// 如果没有从上面取到sessionId,则需要存放到map中
				if (!bGetSessionId) {
					SessionUtils.autoSetMapSessionIdOfConn(conn);
				}
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
//			String ss = HttpGZipUtils.get(sUrl);
//			SessionUtils.listMapSession();
//		} catch (IOException e) {
//		}
//	}
	
}
