package org.bidtime.utils.file;

public class GlobalParams {
	private String uploadPath;
	private String downloadPath;
	private String dataPath;

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
	
	private String tmpPath;
	private String tmpDownloadPath;
	private String tmpUploadPath;
	
	public String getTmpPath() {
		return tmpPath;
	}

	public void setTmpPath(String tmpPath) {
		this.tmpPath = tmpPath;
		String p = null;
		if (tmpPath.indexOf('/') >= 0 ) {
			p = "/";
		} else {
			p = "\\";
		}
		this.tmpDownloadPath = tmpPath + "dl" + p;
		this.tmpUploadPath = tmpPath + "ul" + p;
	}

	public String getTmpUploadPath() {
		return tmpUploadPath;
	}

	public String getTmpDownloadPath() {
		return tmpDownloadPath;
	}

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

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

}
