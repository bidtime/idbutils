package org.bidtime.utils.basic;


public class CNetComm {

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

}
