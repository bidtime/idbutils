package org.bidtime.utils.comm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.bidtime.utils.basic.ObjectComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(PropUtils.class);

	private Properties prop = new Properties(); // 属性集合对象

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public PropUtils() {

	}

	// public PropUtils(String fileName) {
	// load(fileName);
	// }

	public Object get(Object key) {
		return this.prop.get(key);
	}

	public String getString(Object key) {
		return String.valueOf(this.prop.get(key));
	}

	public String getString(Object key, String sDefault) {
		Object o = get(key);
		if (o != null) {
			return String.valueOf(o);
		} else {
			return sDefault;
		}
	}

	public Integer getInteger(Object key) {
		Object o = get(key);
		return ObjectComm.objectToInteger(o);
	}

	public Integer getInteger(Object key, Integer nDefault) {
		Object o = get(key);
		return ObjectComm.objectToInteger(o, nDefault);
	}

	public Long getLong(Object key) {
		Object o = get(key);
		return ObjectComm.objectToLong(o);
	}

	public Long getLong(Object key, Long lDefault) {
		Object o = get(key);
		return ObjectComm.objectToLong(o, lDefault);
	}

	public void loadOutJar(String fileName) {
		prop.clear();
		String propFile = null;
		// propFile =
		// FileUtils.mergeSubPath(FileUtils.getPath2(),"srvcfg.properties");
		propFile = FileUtils.mergeSubPath(FileUtils.getPath(), fileName);
		logger.info("load:" + propFile);
		// Thread.currentThread().getContextClassLoader().getResources(
		// ".");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propFile);
			prop.load(fis);
			// prop.list(out);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public void loadOfSrc(String fileName) {
		InputStream input = null;
		try {
			input = FileUtils.getInputStream(fileName);
			if (input != null) {
				prop.load(input);
			}
		} catch (IOException e) {
			logger.error("loadOfSrc:" + fileName + "->" + e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("loadOfSrc:close(" + fileName + ")->"
							+ e.getMessage());
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void propTpMap(Map map) throws Exception {
		PropComm.propTpMap(prop, map);
	}
}
