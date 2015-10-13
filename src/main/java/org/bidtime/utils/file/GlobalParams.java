package org.bidtime.utils.file;

public class GlobalParams {
	
	private final static String TMP = "tmp";
	//private String dataPath;
	private String webUrl;
	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getWebRoot() {
		return webRoot;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

	private String webRoot;
	
//	private String getExistsSlash(String path) {
//		String result = null;
//		if ( path.indexOf('/') >= 0 ) {
//			result = "/";
//		} else {
//			result = "\\";
//		}
//		return result;
//	}
	
	public String mergeUrl(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(webUrl);
		sb.append(s);
		sb.append("/");
		return sb.toString();
	}
	
	public String getTmpUrl() {
		return mergeUrl(TMP);
	}
	
	public String mergePath(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(webRoot);
		sb.append(s);
		sb.append("/");
		return sb.toString();
	}
	
	public String getTmpPath() {
		return mergePath(TMP);
	}

//	public void setTmpPath(String tmpPath) {
//		this.tmpPath = tmpPath;
//		String p = getExistsSlash(tmpPath);
//		this.tmpDownloadPath = tmpPath + "dl" + p;
//		this.tmpUploadPath = tmpPath + "ul" + p;
//	}

	private GlobalParams() {
	}

	private volatile static GlobalParams instance = null;
	
//	public static void setInstance(GlobalParams o) {
//		globalParams = o;
//	}

	public static GlobalParams getInstance() {
		return getGlobalParams();
	}
	
	public static GlobalParams getGlobalParams() {
//		if (instance == null) {
//			synchronized (GlobalParams.class) {
//				if (instance == null) {
//					instance = (GlobalParams)SpringContextUtils.getBean("globalParams");
//				}
//			}
//		}
		return instance;
	}
	
	public void init() {
		instance = this;
	}

//	public String getDataPath() {
//		return this.dataPath;
//	}
//
//	public void setDataPath(String dataPath) {
//		this.dataPath = dataPath;
//	}

}
