package org.bidtime.dbutils.gson;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResultDTO<T> implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1559037714406652458L;

	/**
	 * 数据，真正的结果对象
	 */
	T data;

	public T getData() {
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void setData(T data) {
		if (data != null) {
			if (data instanceof List) {
				this.len = ((List)data).size();
			}
		}
		this.data = data;
	}
	
	public boolean isSuccess() {
		return state == 0 ? true : false;
	}

	/**
	 * 状态
	 * 
	 */
	private int state=0;

	private long len=0;

	public long getLen() {
		return len;
	}

	public void setLen(long len) {
		this.len = len;
	}

	/**
	 * 消息
	 */
	private String msg;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSuccess(boolean b) {
		setState(b ? 0 : 1);
	}
	
	public ResultDTO() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResultDTO success(Object data) {
		ResultDTO r = new ResultDTO();
		r.setData(data);
		return r;
	}

	@SuppressWarnings({ "rawtypes" })
	public static ResultDTO error(int state, String msg) {
		ResultDTO r = new ResultDTO();
		r.setState(state);
		r.setMsg(msg);
		return r;
	}

	@SuppressWarnings({ "rawtypes" })
	public static ResultDTO error(String msg) {
		ResultDTO r = new ResultDTO();
		r.setSuccess(false);
		r.setMsg(msg);
		return r;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResultDTO error(String msg, Object data) {
		ResultDTO r = new ResultDTO();
		r.setSuccess(false);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public ResultDTO(int state, String msg, T data) {
		setState(state);
		setMsg(msg);
		setData(data);
	}

	public ResultDTO(int state, String msg) {
		setState(state);
		setMsg(msg);
	}

//	@SuppressWarnings("rawtypes")
//	private boolean isMapType() {
//		Object o = null;
//		if (data != null && data instanceof List) {
//			o = ((List) data).get(0);
//		}
//		return (o != null && o instanceof Map) ? true : false;
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray dataToJson() {
		JSONArray jsonArray = null;
		//if (data != null) {
		if (data instanceof List) {
			Object o = ((List) data).get(0);
			boolean bMap = (o != null && o instanceof Map) ? true : false;
			if (bMap) {
				jsonArray = JSONHelper
						.listMapToJsonArray((List<Map<String, Object>>) data);
			} else {
				jsonArray = JSONHelper.clazzToJsonArray((List) data);
			}
		} else {
			jsonArray = new JSONArray();
			JSONObject jsonObject = JSONHelper.clazzToJson(data);
			jsonArray.put(jsonObject);
		}
		//} else {
		//	jsonArray = new JSONArray();
		//}
		return jsonArray;
	}

	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", this.state);
		jsonObject.put("msg", this.msg);
		jsonObject.put("len", this.len);
		if (this.data != null) {
			jsonObject.put("data", dataToJson());
		} else {
			jsonObject.putOpt("data", JSONObject.NULL);
		}
		return jsonObject;
	}
	
	public JSONObject toJsonMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", this.state);
		//if (StringUtils.isBlank(msg)) {
		//	jsonObject.put("msg", JSONObject.NULL);
		//} else {
		jsonObject.put("msg", this.msg);
		//}
		return jsonObject;
	}

}
