package org.bidtime.utils.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DES {

	private static final Logger logger = LoggerFactory
			.getLogger(DES.class);
	
	public String deskey;

	private DES() {
	}

	private volatile static DES instance = null;

	public static DES getInstance() {
		if (instance == null) {
			synchronized (DES.class) {
				if (instance == null) {
					instance = new DES();
				}
			}
		}
		return instance;
	}
	
//	public static void main(String[] args) {
//		String sContext="����ɽ";
//		System.out.println("ԭ��������:" + sContext);
//		
//		byte[] btEncryt = encrypt(sContext.getBytes(), DESKEY);
//		System.out.println("���ܺ������:" + btEncryt);
//		
//		String sEncrypt = "";
//		try {
//			sEncrypt = new String(btEncryt, "ISO8859-1");
//		} catch (UnsupportedEncodingException e) {
//			logger.error(e.getMessage());
//		}
//
//		byte[] sDecryt = decrypt(sEncrypt.getBytes(), DESKEY);
//
//		System.out.println("���ܺ������:" + new String(sDecryt));
//	}
	
//		try {
//			DES t = new DES();
//			Cipher cip = Cipher.getInstance("DES");
//			// "12345678"������
//			String sKey = "12345678";
//			cip.init(Cipher.ENCRYPT_MODE, t.getKey(sKey));
//			System.out.println("hello!");
//
//			byte[] bstr = cip.doFinal("hello!".getBytes());
//			System.out.println("���ܺ�����ݣ�" + bstr);
//
//			cip.init(Cipher.DECRYPT_MODE, t.getKey(sKey));
//			String unStr = new String(cip.doFinal(bstr));
//			System.out.println("���ܺ������" + unStr);
//		} catch (NoSuchAlgorithmException e) {
//	logger.error(e.getMessage());
//		} catch (NoSuchPaddingException e) {
//	logger.error(e.getMessage());
//		} catch (InvalidKeyException e) {
//	logger.error(e.getMessage());
//		} catch (IllegalStateException e) {
//			logger.error(e.getMessage());
//		} catch (IllegalBlockSizeException e) {
//	logger.error(e.getMessage());
//		} catch (BadPaddingException e) {
//	logger.error(e.getMessage());
//		}
//	}
	
	public static String encryptStr(String sContext) {
		return new String(encrypt(sContext.getBytes(), DES.instance.getDeskey()));
	}
	
	public static String decryptStr(String sContext) {
		return new String(decrypt(sContext.getBytes(), DES.instance.getDeskey()));
	}
	
	public static String encryptStr(String sContext, String sKey) {
		return new String(encrypt(sContext.getBytes(), sKey));
	}
	
	public static String decryptStr(String sContext, String sKey) {
		return new String(decrypt(sContext.getBytes(), sKey));
	}
	
	public static byte[] encrypt(byte[] bytes) {
		return encrypt(bytes, instance.getDeskey());
	}
	
	public static byte[] decrypt(byte[] bytes) {
		return decrypt(bytes, instance.getDeskey());
	}
	
	public static byte[] encrypt(byte[] bytes, String sKey) {
		byte[] btReturn = null;
		try {
			DES t = new DES();
			Cipher cip = Cipher.getInstance("DES");

			cip.init(Cipher.ENCRYPT_MODE, t.getKey(sKey));
			//System.out.println("hello!");

			//byte[] bstr = cip.doFinal(bytes);
			btReturn = cip.doFinal(bytes);
			//System.out.println("���ܺ�����ݣ�" + btReturn);
			
			//cip.init(Cipher.DECRYPT_MODE, t.getKey(sKey));
			//String unStr = new String(cip.doFinal(bstr));
			//System.out.println("���ܺ������" + unStr);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage());
		} catch (BadPaddingException e) {
			logger.error(e.getMessage());
		}
		return btReturn;
	}
	
	public static byte[] decrypt(byte[] bytes, String sKey) {
		byte[] btReturn = null;
		try {
			DES t = new DES();
			Cipher cip = Cipher.getInstance("DES");
			
			cip.init(Cipher.DECRYPT_MODE, t.getKey(sKey));
			btReturn = cip.doFinal(bytes);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage());
		} catch (BadPaddingException e) {
			logger.error(e.getMessage());
		}
		return btReturn;
	}

	public SecretKey getKey(String password) {
		DESKeySpec spec;
		SecretKey key = null;
		try {
			spec = new DESKeySpec(password.getBytes("UTF-8"));
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
			key = kf.generateSecret(spec);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeySpecException e) {
			logger.error(e.getMessage());
		}
		return key;
	}

	public String getDeskey() {
		return deskey;
	}

	public void setDeskey(String deskey) {
		this.deskey = deskey;
	}
}
