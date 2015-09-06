package org.bidtime.utils.crypt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESManager {

	private String keyPathFile;
	private String key;
	private static final Logger logger = LoggerFactory
			.getLogger(AESManager.class);
	
	private AESUtils aesUtils = null;
//	private volatile static AESManager instance = null;
//	
//	public static AESManager getInstance() {
//		return instance;
//	}

//	public static void main(String[] args) {
//		AESManager a = new AESManager();
//		a.keyPathFile = "d:/pch.txt";
//		a.key = "wedcvbmju384759!#$%^@pch34342.com";
//		//
//		a.writeKeyToFile("sldkjoiewricvxd238766@12674pch.com");
//		//
//		//a.init();
//	}

	public AESUtils getAesUtils() {
		return aesUtils;
	}

	public void setAesUtils(AESUtils aesUtils) {
		this.aesUtils = aesUtils;
	}

	public void init() {
		logger.debug("init begin...");
		String sDesKey = readKeyFromFile();
		aesUtils = new AESUtils(sDesKey);
		AESUtils.setInst(aesUtils);
		logger.debug("init end.");
	}

	public String getKeyPathFile() {
		return keyPathFile;
	}

	public void setKeyPathFile(String keyPathFile) {
		this.keyPathFile = keyPathFile;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/*
	 * 将一个串，通过 key 加密，并存放到文件中
	 */
	public void writeKeyToFile(String sKeyRaw) {
		//logger.debug("writeKeyToFile begin...");
		AESUtils u = new AESUtils(key);
		String sDesKey = u.encrypt(sKeyRaw);
		writeContextToFile(keyPathFile, sDesKey);
		//logger.debug("writeKeyToFile end.");
	}

	public String readKeyFromFile() {
		StringBuilder sb = new StringBuilder();
		AESUtils u = new AESUtils(key);
		String key = null;
		try {
			String sFileKey = readFileByLines(keyPathFile);
			sb.append("file:");
			sb.append(sFileKey);
			key = u.decrypt(sFileKey);
			return key;
		} catch (Exception e) {
			logger.error("readKeyFromFile:", e);
			return null;
		} finally {
			logger.debug("readKeyFromFile:", sb.toString());
			u = null;
			sb.setLength(0);
			sb = null;
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	private static String readFileByLines(String fileName) {
		StringBuilder sbReturn = new StringBuilder();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String lineText = null;
			// 一次读入一行，直到读入null为文件结束
			while ((lineText = reader.readLine()) != null) {
				sbReturn.append(lineText);
			}
			reader.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error(e1.getMessage());
				}
			}
		}
		return sbReturn.toString();
	}
	
	private static void writeContextToFile(String path, String content) {
		try {
			File f = new File(path);
			if (f.exists()) {
				f.deleteOnExit();
				f.createNewFile();
				// System.out.println("文件存在");
			} else {
				// System.out.println("文件不存在，正在创建...");
				if (f.createNewFile()) {
					// System.out.println("文件创建成功！");
				} else {
					// System.out.println("文件创建失败！");
				}
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(content);
			output.close();
		} catch (Exception e) {
			logger.error("writeContextToFile:", e);
		}
	}

}
