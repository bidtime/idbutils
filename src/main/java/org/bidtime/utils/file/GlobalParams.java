package org.bidtime.utils.file;

public class GlobalParams {
	private String uploadPath;
	private String downloadPath;
	private String dataPath;
	private String ptUrl;
	private String apacheUrl;
	private String apacheRoot;
	
	public String getApacheRoot() {
		return apacheRoot;
	}

	public void setApacheRoot(String apacheRoot) {
		this.apacheRoot = apacheRoot;
	}

	public String getApacheUrl() {
		return apacheUrl;
	}

	public void setApacheUrl(String apacheUrl) {
		this.apacheUrl = apacheUrl;
	}

	public String getPtUrl() {
		return ptUrl;
	}

	public void setPtUrl(String ptUrl) {
		this.ptUrl = ptUrl;
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
