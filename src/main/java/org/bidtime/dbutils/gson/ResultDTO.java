package org.bidtime.dbutils.gson;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.bidtime.dbutils.gson.dataset.JsonData;
import org.bidtime.utils.comm.CaseInsensitiveHashSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResultDTO<T> implements Serializable {
	
	Map<String, Set<String>> colMapProps = null;
	protected Class<T> type;
	
	@SuppressWarnings("rawtypes")
	public Class getType() {
		return type;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setType(Class type) {
		this.type = type;
	}

	public Map<String, Set<String>> getColMapProps() {
		return colMapProps;
	}

	public void setColMapProps(Map<String, Set<String>> setPro) {
		this.colMapProps = setPro;
	}

//	public Set<String> getColSetProps() {
//		return (colMapProps != null && type != null) ? colMapProps.get(type.getName()) : null;
//	}

//	public void setColSetProps(Set<String> setPro) throws Exception {
//		if (data == null) {
//			throw new Exception("data is not null.");
//		}
//		if (colMapProps != null) {
//			colMapProps.put(data.getClass().getName(), setPro);
//		} else {
//			colMapProps = new HashMap<String ,Set<String>>();
//			colMapProps.put(data.getClass().getName(), setPro);
//		}
//	}
	
	public void addColSetProps(String col) {
		if (colMapProps == null || type == null) {
			return;
		}
		
		Set<String> setColPro = colMapProps.get(type.getName());
		if (setColPro == null) {
			setColPro = new CaseInsensitiveHashSet();
		}
		setColPro.add(col);
		colMapProps.put(type.getName(), setColPro);
	}
	
	public void addColSetProps(Set<String> colSetProps) {
		if (colMapProps == null || type == null) {
			return;
		}
		Set<String> setColPro = colMapProps.get(type.getName());
		if (setColPro == null) {
			setColPro = new CaseInsensitiveHashSet();
		}
		setColPro.addAll(colSetProps);
		colMapProps.put(type.getName(), setColPro);
	}

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setData(T data) {
		if (data != null) {
			//this.setType(data.getClass());
			if (data instanceof List) {
				this.len = ((List)data).size();
			} else if (data instanceof ResultDTO) {
				ResultDTO resultDTO = (ResultDTO)data;
				if ( this.colMapProps == null ) {
					colMapProps = resultDTO.colMapProps;
				} else {
					colMapProps.putAll(resultDTO.colMapProps);
				}
			}
		}
		this.data = data;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void putMapHead(ResultDTO resultDTO) {
		if ( this.colMapProps == null ) {
			colMapProps = resultDTO.colMapProps;
		} else {
			if (resultDTO.colMapProps != null) {
				colMapProps.putAll(resultDTO.colMapProps);
			}
		}		
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
	
	private static boolean isJavaSimpleClazz(Class<?> type) {
		boolean result = false;
		if (type == null) {		//要转换成json.null
			result = false;
		} else if (type.equals(String.class) || type.equals(Number.class) ||
			type.equals(StringBuilder.class) || type.equals(StringBuffer.class) ||
			type.equals(Boolean.class) || type.equals(Appendable.class)) {
			result = true;
		} else if (type.equals(java.util.Date.class)) {
			//objData = dateToyyyyMMddHHmmss((Date) objRaw);
			result = true;
		} else if (type.equals(JsonData.class)) {
			result = false;
		} else if (type.equals(Set.class) || type.equals(Queue.class) ||
			type.equals(Character.class) ||
			type.equals(Math.class) || type.equals(Enum.class)) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object dataToJson() {
		Object jsonObject = null;
		//if (data != null) {
		if (data instanceof List && !((List) data).isEmpty()) {
			Object o = ((List) data).get(0);
			boolean bMap = (o != null && o instanceof Map) ? true : false;
			if (bMap) {
				jsonObject = JSONHelper
						.listMapToJsonArray((List<Map<String, Object>>) data
								, colMapProps);
			} else {
				if (o != null && isJavaSimpleClazz(o.getClass())) {
					jsonObject = new JSONArray((List) data);
				} else {
					jsonObject = JSONHelper.clazzToJsonArray((List) data, colMapProps);
				}
			}
		} else {
			//jsonArray = new JSONArray();
			//JSONObject jsonObject = null;
			if (data instanceof Map) {
				jsonObject = JSONHelper.mapToJson((Map)(data), colMapProps);
			} else {
				if (this.type != null && isJavaSimpleClazz(type)) {
					jsonObject = new JSONObject(data);
				} else {
					jsonObject = JSONHelper.clazzToJson(data, colMapProps);
				}
			}
			//jsonArray.put(jsonObject);
		}
		//} else {
		//	jsonArray = new JSONArray();
		//}
		return jsonObject;
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
