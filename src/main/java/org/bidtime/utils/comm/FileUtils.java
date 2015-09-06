package org.bidtime.utils.comm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(FileUtils.class);

	public static String getPath() {
		String filePath = System.getProperty("java.class.path");
		logger.info("filePath:" + filePath);
		String pathSplit = System.getProperty("path.separator");
		logger.info("pathSplit:" + pathSplit);
		if (filePath.contains(pathSplit)) {
			filePath = filePath.substring(0, filePath.indexOf(pathSplit));
		} else if (filePath.endsWith(".jar")) {
			filePath = filePath.substring(0,
					filePath.lastIndexOf(File.separator) + 1);
			// File.separator:Character that separates components of a file
			// path. This is "/" on UNIX and "" on Windows.
		}
		return filePath;
	}

	public static String getPath2() {
		URL url = FileUtils.class.getProtectionDomain().getCodeSource()
				.getLocation();
		logger.info(url.getFile());
		String filePath = null;
		try {
			filePath = URLDecoder.decode(url.getPath(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}

		if (filePath.endsWith(".jar")) {
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		}
		File file = new File(filePath);
		filePath = file.getAbsolutePath();
		return filePath;
	}

	public static String mergeSubPath(String sPath, String sSubFile) {
		return sPath + File.separatorChar + sSubFile;
	}

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back to system
			// class loader...
			logger.error(ex.getMessage());
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = FileUtils.class.getClassLoader();
		}
		return cl;
	}

	public static InputStream getInputStream(String path) throws IOException {
		InputStream is;
		is = getDefaultClassLoader().getResourceAsStream(path);
		if (is == null) {
			throw new FileNotFoundException(
					" cannot be opened because it does not exist:"+path);
		}
		return is;
	}

	public static boolean isLinux() {
		if ("\\".equals(File.separator)) {
			return false;
			// is WINDOWS
		} else if ("/".equals(File.separator)) {
			// is LINUX
			return true;
		} else {
			return true;
		}
	}

	public static String getFileRealPath(String path) throws IOException {
		String sPath = FileUtils.class.getClassLoader().getResource(".").getPath();
		String sReturn = sPath + path;
//		logger.info("path:"+sPath);
//		logger.info("input:"+path);
//		logger.info("ret:"+sReturn);
		return sReturn;
	}
	
	public static String getLocalFileRealPath(String path) throws IOException {
		String sPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String sReturn = sPath + path;
//		logger.info("path:"+sPath);
//		logger.info("input:"+path);
//		logger.info("ret:"+sReturn);
		return "file://"+sReturn;
	}
	
	public static String getAppPath() {
		return getAppPath(FileUtils.class);
	}
	
	public static String getAppPath(String path) {
		return getAppPath(FileUtils.class) + path;
	}
	
	public static String getAppRoot(String path) {
		return getAppRoot() + path;
	}
		
	public static String getAppRoot() {
		String ss = getAppPath();
		int npos = ss.lastIndexOf("/");
		if (npos>0) {
			return ss.substring(0, npos+1);
		} else {
			return ss;
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getAppPath(Class cls) {
		// 检查用户传入的参数是否为空
		if (cls == null) {
			throw new IllegalArgumentException("参数不能为空！");
		}
		ClassLoader loader = getDefaultClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包
		Package pack = cls.getPackage();
		String path = "";
		// 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax.")) {
				throw new IllegalArgumentException("不要传送系统类！");
			}
			// 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (packName.indexOf(".") < 0)
				path = packName + "/";
			else {// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		}
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		URL url = loader.getResource(path + clsName);
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1) {
			realPath = realPath.substring(pos + 5);
		}
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!")) {
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		}
		/*------------------------------------------------------------
		ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径
		中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要
		的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的
		中文及空格路径
		-------------------------------------------------------------*/
		try {
			realPath = URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}// getAppPath定义结束

}
