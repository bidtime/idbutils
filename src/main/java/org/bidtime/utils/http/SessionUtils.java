package org.bidtime.utils.http;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(SessionUtils.class);
	
	public static final Integer USER_NOT_CONNECT = -2;		//no connect.
	public static final Integer USER_NOT_LOGIN = -1;		//no login, user does not login.
	public static final Integer USER_NOT_POWER = 0;			//no power, user has no power at current url.
	public static final Integer USER_NOT_ONLINE = 1;		//no online, user login at others.
	public static final String NOT_LOGIN_IN = "notLogin";	//not login
	
	private static Map<String,String> listHostPath = new HashMap<String,String>();
	
	private static String getSessionIdOfUrl(String sHostPath) {
		String sSessionId = (String)listHostPath.get(sHostPath);
		return sSessionId;
	}
	
//	@SuppressWarnings({ "unused", "rawtypes" })
//	public static void listMapSession() {
//		 int size = listHostPath.size();
//		 Set keysSet = listHostPath.keySet();			
//		 Iterator iterator = keysSet.iterator();			
//		 while(iterator.hasNext()) {
//			Object key = iterator.next();//key				
//			Object value = listHostPath.get(key);//value
//			logger.info( (String)(key) + " : " + (String)(value));
//		}
//	}
	
	private static void setSessionIdOfUrl(String sHostPath, String sSessionId) {
		listHostPath.put(sHostPath, sSessionId);
	}
	
//	private static String autoSetSessionIdOfUrl(String sHostPath, String sSessionId) {
//		String sResult = getSessionIdOfUrl(sHostPath);
//		if (sResult == null) {
//			setSessionIdOfUrl(sHostPath, sSessionId);
//		}
//		return sResult;
//	}
	
	public static String getWebPath(String sPath) {
		String sWebApp = "";
		int nPos = sPath.indexOf("/");
		if (nPos>-1) {
			int nPos2 = sPath.indexOf("/", nPos+1);
			if (nPos2>-1) {
				sWebApp = sPath.substring(nPos, nPos2);
			}
		}
		return sWebApp;
	}
	
	public static String getHostPortContext(HttpURLConnection conn) {
		logger.info("host: "+conn.getURL().getHost());
		logger.info("host: "+conn.getURL().getPort());
		String sWebApp = getWebPath(conn.getURL().getPath());
		return conn.getURL().getHost()+":"+conn.getURL().getPort()+sWebApp;
	}
	
	public static String getMapSessionIdOfConn(HttpURLConnection conn) {
		String sHostPortContext = getHostPortContext(conn);
		logger.info("HostPortContext: "+sHostPortContext);
		return getSessionIdOfUrl(sHostPortContext);
	}
	
	public static boolean autoGetMapSessionIdOfConn(HttpURLConnection conn) {
		String sHostPortContext = getHostPortContext(conn);
		logger.info("HostPortContext: "+sHostPortContext);
		String sessionId = getSessionIdOfUrl(sHostPortContext);
		if (StringUtils.isNotEmpty(sessionId)) {
			//conn.setRequestProperty("Cookie", "JSESSIONID="+sessionId);
			conn.setRequestProperty("Cookie", sessionId);
			return true;
		} else {
			return false;
		}
	}
	
	public static void autoSetMapSessionIdOfConn(HttpURLConnection conn) {
		String sHostPortContext = getHostPortContext(conn);
		String sessionId = getNewSessionIdOfConn(conn);
		setSessionIdOfUrl(sHostPortContext,sessionId);
	}
	
	public static Integer getNotLoginOfHead(HttpURLConnection conn) {
		List<String> vs = conn.getHeaderFields().get(NOT_LOGIN_IN);
		if (vs != null && vs.size()>0) {
			return Integer.valueOf(vs.get(0));
		} else {
			return null;
		}
	}

	private static String getNewSessionIdOfConn(HttpURLConnection conn) {
		// 打印请求头信息
//		Map hfs = conn.getHeaderFields();
//		Set<String> keys = hfs.keySet();
//		for (String str : keys) {
//			List<String> vs = (List) hfs.get(str);
//			for (String v : vs) {
//				logger.info(str + ": "+ v + "\t");
//			}
//			//logger.info();
//		}
//		logger.info("-----------------------");
		String cookieValue = conn.getHeaderField("Set-Cookie");
		logger.info("cookie value:" + cookieValue);
		
		String sessionId = null;
		if (cookieValue != null) {
			sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
		}
		logger.info(sessionId);
		return sessionId;
	}

}
