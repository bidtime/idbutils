package org.bidtime.utils.basic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class NetComm {

	public static int isInnerIP(long a_ip) {
		int bValid = -1;
		if ((a_ip >> 24 == 0xa) || (a_ip >> 16 == 0xc0a8)
				|| (a_ip >> 22 == 0x2b0)) {
			bValid = 0;
		}
		return bValid;
	}

	public static long ipToLong(String strIP) {
		long[] ip = new long[4];
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}
	
	public static String getFirstClientIP(HttpServletRequest request) {
		return getClientIP(request, 0);
	}
	
	public static String getClientIP(HttpServletRequest request, int idx) {
		String s = getClientIP(request);
		if (StringUtils.isEmpty(s)) {
			return null;
		}
		String[] ar = s.split(",");
		if (ar.length>0) {
			if (idx<ar.length) {
				return ar[idx];
			} else {
				return ar[0];
			}
		} else {
			return null;
		}
	}	

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
