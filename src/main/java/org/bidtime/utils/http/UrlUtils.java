package org.bidtime.utils.http;

public class UrlUtils {
	private StringBuilder sbList = new StringBuilder();
	public String toUrl() {
		return sbList.toString();
	}
	
	public void insert(String str) {
		sbList.insert(0, str);
	}
	
	public void insert(int offset, String str) {
		sbList.insert(offset, str);
	}
	
	public String toUrl(boolean ptFlag) {
		if (ptFlag) {
			addPtFlag();
		}
		return sbList.toString();
	}

	public void add(String s, Object o) {
		add(s);
		add(o);
	}
	
	public void add(String s) {
		sbList.append(s);
		sbList.append("/");
	}
	
	public void add(Object o) {
		sbList.append(String.valueOf(o));
		sbList.append("/");
	}
	
	private void addPtFlag() {
		add("pt");
	}
}
