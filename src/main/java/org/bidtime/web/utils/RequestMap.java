package org.bidtime.web.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bidtime.utils.basic.MapParamsUtils;

/**
 * @author jss
 * 
 * 提供对从Request中取出参数的功能,并放入HashMap中
 *
 */
public class RequestMap {
	
	private HttpServletRequest request = null;
	private Map<String,Object> map = new HashMap<String,Object>();
	
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public RequestMap(HttpServletRequest request) {
		this.request = request;
	}
	
	public void addStringToMap(String param) {
		addStringToMap(param,param);
	}
	
	public void addPercentStringToMap(String param) {
		addPercentStringToMap(param,param);
	}
	
	public void addLeftPercentStringToMap(String param) {
		addLeftPercentStringToMap(param,param);
	}
	
	public void addRightPercentStringToMap(String param) {
		addRightPercentStringToMap(param,param);
	}
	
	public void addIntegerToMap(String param) {
		addIntegerToMap(param,param);
	}
	
	public void addLongToMap(String param) {
		addLongToMap(param,param);
	}
	
	public void addDateToMap(String param) {
		addDateToMap(param,param);
	}
	
	public void addDoubleToMap(String param) {
		addDoubleToMap(param,param);
	}
	
	public void addFloatToMap(String param) {
		addFloatToMap(param,param);
	}
	
	public void addShortToMap(String param) {
		addShortToMap(param,param);
	}
	
	public void addByteToMap(String param) {
		addByteToMap(param,param);
	}
	
	/////////////////////////////////////////////////////////////
	
	public void addStringToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getByte(request, param));
	}
	
	public void addPercentStringToMap(String key, String param) {
		MapParamsUtils.addPercentToMap(map, key, RequestUtils.getByte(request, param));
	}
	
	public void addLeftPercentStringToMap(String key, String param) {
		MapParamsUtils.addLeftPercentToMap(map, key, RequestUtils.getByte(request, param));
	}
	
	public void addRightPercentStringToMap(String key, String param) {
		MapParamsUtils.addRightPercentToMap(map, key, RequestUtils.getByte(request, param));
	}
	
	public void addIntegerToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getInt(request, param));
	}
	
	public void addLongToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getLong(request, param));
	}
	
	public void addDateToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getDate(request, param));
	}
	
	public void addDoubleToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getDouble(request, param));
	}
	
	public void addFloatToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getFloat(request, param));
	}
	
	public void addShortToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getShort(request, param));
	}
	
	public void addByteToMap(String key, String param) {
		MapParamsUtils.addToMap(map, key, RequestUtils.getByte(request, param));
	}
	
}
