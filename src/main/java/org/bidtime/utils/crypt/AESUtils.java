package org.bidtime.utils.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(AESUtils.class);

	private static final String Algorithm = "AES";
	private static AESUtils inst = null;

	public static void setInst(AESUtils u) {
		inst = u;
	}

	public static AESUtils getInst() {
		return inst;
	}

	private Cipher cipher_encrypt = null;
	private Cipher cipher_decrypt = null;

	public AESUtils(String password) {
		init(password.getBytes());
	}

	public AESUtils(byte[] password) {
		init(password);
	}

	private void init(byte[] password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(Algorithm);
			//linux需要加此方法,否则会报
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
			secureRandom.setSeed(password);
			
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, Algorithm);

			cipher_encrypt = Cipher.getInstance(Algorithm);// 创建密码器
			cipher_encrypt.init(Cipher.ENCRYPT_MODE, key);// 初始化

			cipher_decrypt = Cipher.getInstance(Algorithm); // 创建密码器
			cipher_decrypt.init(Cipher.DECRYPT_MODE, key); // 初始化
		} catch (NoSuchAlgorithmException e) {
			logger.error("init", e);
		} catch (NoSuchPaddingException e) {
			logger.error("init", e);
		} catch (InvalidKeyException e) {
			logger.error("init", e);
		}
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public byte[] encrypt(byte[] content) {
		try {
			return cipher_encrypt.doFinal(content); // 加密
		} catch (IllegalBlockSizeException e) {
			logger.error("encrypt", e);
		} catch (BadPaddingException e) {
			logger.error("encrypt", e);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public byte[] decrypt(byte[] content) {
		try {
			return cipher_decrypt.doFinal(content); // 加密
		} catch (IllegalBlockSizeException e) {
			logger.error("decrypt", e);
		} catch (BadPaddingException e) {
			logger.error("decrypt", e);
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	public String encrypt(String content) {
		if (content == null) {
			return null;
		} else if (StringUtils.isEmpty(content)) {
			return content;
		} else {
			return encryptRaw(content);
		}
	}
	
	public String decrypt(String content) {
		if (content == null) {
			return null;
		} else if (StringUtils.isEmpty(content)) {
			return content;
		} else {
			return decryptRaw(content);
		}
	}

	private String encryptRaw(String content) {
		try {
			byte[] encryptResult = encrypt(content.getBytes("utf-8"));
			return parseByte2HexStr(encryptResult);
		} catch (UnsupportedEncodingException e) {
			logger.error("encryptRaw", e);
		}
		return null;
	}

	private String decryptRaw(String content) {
		try {
			byte[] encryptResult = decrypt(parseHexStr2Byte(content));
			return new String(encryptResult, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("decryptRaw", e);
		}
		return null;
	}

//	private static String getFormatTimeNow(long date) {
//		long endTime = System.currentTimeMillis();
//		return getFormatTime(date, endTime);
//	}
//
//	private static String getFormatTime(long startTime, long endTime) {
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(endTime - startTime);
//		String sReturn = "耗时: " + c.get(Calendar.MINUTE) + "m:"
//				+ c.get(Calendar.SECOND) + "s:" + c.get(Calendar.MILLISECOND)
//				+ "ms" + " ";
//        return sReturn;
//    }
//
//	public static void main(String args[]) throws UnsupportedEncodingException {
//		//String password = "nothing!1234@voxyabtr.com";
//		//String password = "12345678";
//		String password = "qmsk_abc@163.com";
//		AESUtils u = new AESUtils(password);
//		u.encrypt(u);
//		//u.decrypt(u);
//	}

//	private void decrypt(AESUtils u) {
//		String content = "AAE83E3810265054CBD456C3F8E15C11";
//		System.out.println(content);
//		System.out.println(u.decrypt(content));
//	}
//	
//	private void encrypt(AESUtils u) {
//		String content = "2";
//		System.out.println(content);
//		System.out.println(u.encrypt(content));
//	}
	
//	public static void test2() {
//		String password = "nothing!1234@voxyabtr.com";
//		//String content = "123456789012345";
//		String content = "4F5852EC46EB6C87E03DEAAF0C9BEB46";
//		AESUtils u = new AESUtils(password);
//		// 加密
//		long startTime = System.currentTimeMillis();
//
//		//System.out.println("加密前：" + content);
//		String encryptResultStr = "";
//		//for (int i=0; i<100; i++) {
//			encryptResultStr = u.encrypt(content);
//		//}
//		System.out.println("密钥： " + password);
//		System.out.println("密钥：长度 " + password.length());
//		System.out.println("内容：" + content);
//		System.out.println("内容：长度 " + content.length());
//		System.out.println("加密后：" + encryptResultStr);
//		System.out.println("加密后：长度 " + encryptResultStr.length());
//		System.out.println(getFormatTimeNow(startTime));
//		// 解密
//		startTime = System.currentTimeMillis();
//		String decryptResult = "";
//		//for (int i=0; i<100; i++) {
//			decryptResult = u.decrypt(encryptResultStr);
//		//}
//		System.out.println("解密后：" + decryptResult);
//		System.out.println(getFormatTimeNow(startTime));
//	}
	
}
