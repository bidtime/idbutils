package org.bidtime.dbutils.jdbc.connection.ds;

import java.util.HashSet;
import java.util.Set;

import org.bidtime.utils.crypt.AESManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringUtils;

public class CryptPropertyConfig extends PropertyPlaceholderConfigurer {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CryptPropertyConfig.class);

	private String cryptType;

	public String getCryptType() {
		return cryptType;
	}

	public void setCryptType(String cryptType) {
		this.cryptType = cryptType;
	}

	private AESManager aesManager;
	private String propKeys;
	private Set<String> propSet;

    public AESManager getAesManager() {
		return aesManager;
	}

	public void setAesManager(AESManager aesManager) {
		this.aesManager = aesManager;
	}

	public String getPropKeys() {
		return propKeys;
	}

	public void setPropKeys(String propKeys) {
		this.propKeys = propKeys;
		if (!StringUtils.isEmpty(propKeys)) {
			if (!propKeys.trim().equals("*")) {
				propSet = new HashSet<String>();
				String[] ar = propKeys.split(";");
				for (int i=0; i<ar.length; i++) {
					propSet.add(ar[i]);
				}
			}
		} else {
			propSet = null;
		}
	}
	
    /**
     * 重写父类方法，解密指定属性名对应的属性值
     */
	@Override
	protected String convertProperty(String propertyName, String propertyValue){
		StringBuilder sb = new StringBuilder();
		sb.append("k-v:");
		sb.append(propertyName);
		sb.append(" = ");
		sb.append(propertyValue);
		if (!StringUtils.isEmpty(propertyValue)) {
			sb.append(", old value :");
			sb.append(propertyValue);
		} else {
			sb.append(", value is null ");
		}
		String propertyNew = null;
		if ( isEncryptPropertyVal(propertyName) ) {
			propertyNew = crypt(propertyValue, cryptType);			//调用解密方法
		} else {
			propertyNew = propertyValue;
		}
		sb.append(", new value:");
		sb.append(propertyNew);
		logger.debug(sb.toString());
		return propertyNew;
	}

	/**
	 * 判断属性值是否需要解密，这里我约定需要解密的属性名用encrypt开头
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptPropertyVal(String propertyName){
		if (propKeys.trim().equals("*")) {
			return true;
		} else {
			if ( propSet == null || propSet.isEmpty() ) {
				return false;
			} else {
				return propSet.contains(propertyName);
			}
		}
	}
	
	private String crypt(String content, String crypt) {
		if (!StringUtils.isEmpty(crypt)) {
			if (crypt.trim().equalsIgnoreCase("encrypt")) {
				return encrypt(content);
			} else if (crypt.trim().equalsIgnoreCase("decrypt")) {
				return decrypt(content);				
			} else {
				return content;
			}
		} else {
			return content;
		}
	}

	private String encrypt(String content) {
		try {
			return aesManager.getAesUtils().encrypt(content);
		} catch (Exception e) {
			logger.error("decrypt", e);
			return content;
		}
	}
	
	private String decrypt(String content) {
		try {
			return aesManager.getAesUtils().decrypt(content);
		} catch (Exception e) {
			logger.error("decrypt", e);
			return content;
		}
	}
 
}
