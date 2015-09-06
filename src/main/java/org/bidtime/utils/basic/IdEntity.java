package org.bidtime.utils.basic;

import java.util.UUID;

public class IdEntity {
	/** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(boolean bDelMinus) { 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号
        if (bDelMinus) {
        	return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        } else {
        	return s;
        }
    }

}
