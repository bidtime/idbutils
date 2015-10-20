package org.bidtime.dbutils.gson.dataset;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bidtime.dbutils.gson.JSONHelper;
import org.bidtime.utils.basic.ArrayComm;
import org.bidtime.utils.basic.ObjectComm;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonRow {
	private static final Logger logger = LoggerFactory
			.getLogger(GsonRow.class);

	HashMap<String, Integer> mapHead = new HashMap<String, Integer>();

	public GsonRow() {
	}

	public GsonRow(String[] head, Object[] list) {
		setHeadList(head, list);
	}
	
	public void setHeadList(String[] head, Object[] list) {
		if (head == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("head is null");
			}
			// throw new Exception("xml head is null ");
		} else if (list == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("data is null");
			}
			// throw new Exception("data is null ");
		} else {
			if (head.length != list.length) {			
				StringBuilder sb = new StringBuilder();
				sb.append("head( ");
				sb.append(head.length);
				sb.append(" )");
				sb.append(" and data( ");
				sb.append(list.length);
				sb.append(" ) isn't mismatch.");
				logger.error(sb.toString());
				// throw new Exception("xml head and date  mismatching ");
			}
		}
		this.setHead(head);
		this.setData(list);
		clearIndex();
	}

	String[] head;
	
	Object[] data;

	public String[] getHead() {
		return head;
	}

	public void setHead(String[] head) {
		this.head = head;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}
	
	private JSONArray dataToJson() {
		JSONArray ar1 = new JSONArray();
		for (int j = 0; j < head.length; j++) {
			Object obj;
			if (j < data.length) {
				obj = data[j];
			} else {
				obj = null;
			}
			ar1.put(JSONHelper.objToJsonObj(obj, null));
		}
		return ar1;
	}

	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		if (head != null && head.length>0) {
			jsonObject.put("head", this.head);			
		}
		if (this.data != null && this.data.length>0) {
			jsonObject.put("data", dataToJson());
		} else {
			jsonObject.putOpt("data", JSONObject.NULL);
		}
		return jsonObject;
	}

	@Override
	public String toString() {
		JSONObject jsonObject1 = toJson();
		return jsonObject1.toString();
	}
	
//	public GsonRow(String json) {
//		fromString(json);
//	}
//	
//	public GsonRow(JSONObject jsonObject) {
//		fromJson(jsonObject);
//	}
//	
//	public static GsonRow parserString(String json) {
//		return parserString(json, false);
//	}
//	
//	public static GsonRow parserString(String json, boolean bNew) {
//		if (StringUtils.isNotEmpty(json)) {
//			JSONObject jsonobj = new JSONObject(json);
//			if (jsonobj.length() > 0 || bNew) {
//				GsonRow row = new GsonRow();
//				row.fromJson(jsonobj);
//				return row;
//			} else {
//				return null;
//			}
//		} else {
//			return null;
//		}
//	}
	
//	public void fromString(String json) {
//		JSONObject jsonobj = new JSONObject(json);
//		fromJson(jsonobj);
//	}
//	
//	public void fromJson(JSONObject jsonObject) {
//		JSONArray headArray = jsonObject.optJSONArray("head");
//		if (headArray != null && headArray.length()>0) {
//			head = new String[headArray.length()];
//			for (int i = 0; i < headArray.length(); i++) {
//				Object object = headArray.get(i);
//				head[i] = (String)object;
//			}
//		}
//		//
//		if (jsonObject.has("data") && !jsonObject.isNull("data")) {
//			JSONArray dataArray = jsonObject.getJSONArray("data");
//			if (dataArray.length() > 0) {
//				this.data = new Object[dataArray.length()];
//				for (int n = 0; n < dataArray.length(); n++) {
//					data[n] = JSONHelper.jsonObjToObj(dataArray.get(n));
//				}
//			} else {
//				CArrayComm.clearArray(data);
//			}
//		} else {
//			CArrayComm.clearArray(data);
//		}
//	}

	public boolean renameHead(String newName, String oldName) {
		int nPos = this.getPosOfName(oldName);
		if (nPos >= 0) {
			if (getPosOfName(newName)<0) {
				head[nPos] = newName;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean canRenameHead(String[] newNames, String[] oldNames, Integer[] idxOlds) {
		boolean bReturn = true;
		for (int i=0; i<newNames.length; i++) {
			int nPos = this.getPosOfName(oldNames[i]);
			if (nPos >= 0) {
				if (getPosOfName(newNames[i])>=0) {
					bReturn = false;
					break;
				} else {
					idxOlds[i] = nPos;
				}
			} else {
				bReturn = false;
				break;
			}
		}
		return bReturn;
	}
	
	public boolean renameHead(String[] newNames, String[] oldNames) {
		if (newNames.length != oldNames.length) {
			return false;
		} else {
			Integer[] idxOlds = new Integer[newNames.length];
			if (!canRenameHead(newNames, oldNames, idxOlds)) {
				return false;
			}
			for (int i=0; i<idxOlds.length; i++) {
				head[idxOlds[i]] = newNames[i];
			}
			return true;
		}
	}

	public void autoAddHeadData(String headName, Object value) {
		int nIdx = this.getPosOfName(headName);
		if (nIdx < 0) {
			addHeadData(headName, value);
		} else {
			this.setValueOfIdx(nIdx, value);
		}
	}

	public void autoAddHeadData(String[] headName, Object[] value) {
		for (int i=0; i<headName.length; i++) {
			autoAddHeadData(headName[i], value[i]);
		}
	}

	public void autoInsertHeadData(String headName, Object value) {
		int nIdx = this.getPosOfName(headName);
		if (nIdx < 0) {
			insertHeadData(headName, value);
		} else {
			this.setValueOfIdx(nIdx, value);
		}
	}

	public void autoInsertHeadData(String[] headName, Object[] value) {
		for (int i=0; i<headName.length; i++) {
			autoInsertHeadData(headName[i], value[i]);
		}
	}

	public void insertHeadData(String arHead, Object arData) {
		head = ArrayComm.mergeStringArray(arHead, head);
		data = ArrayComm.mergeArray(arData, data);
		clearIndex();
	}

	public void insertHeadData(String[] arHead, Object[] arData) {
		head = ArrayComm.mergeStringArray(arHead, head);
		data = ArrayComm.mergeArray(arData, data);
		clearIndex();
	}

	public void addHeadData(String arHead, Object arData) {
		head = ArrayComm.mergeStringArray(head, arHead);
		data = ArrayComm.mergeArray(data, arData);
		clearIndex();
	}

	public void addHeadData(String[] arHead, Object[] arData) {
		head = ArrayComm.mergeStringArray(head, arHead);
		data = ArrayComm.mergeArray(data, arData);
		clearIndex();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Integer[] getHeadPosArrayOfName(String[] arHead) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < head.length; i++) {
			for (int j = 0; j < arHead.length; j++) {
				if (arHead[j].equalsIgnoreCase(head[i])) {
					list.add(i);
				} else {
				}
			}
		}
		if (list.size()>1) {
			Comparator comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					Integer p1 = (Integer) o1;
					Integer p2 = (Integer) o2;
					if (p1 < p2)
						return 0;
					else
						return 1;
				}
			};
			Collections.sort(list, comp);
		}
		return (Integer[]) list.toArray(new Integer[list.size()]);
	}
	
	public void revHead(String[] arHead) throws Exception {
		List<String> list = new ArrayList<String>();			
		for (int i=0; i<this.head.length; i++) {
			String sHeadRaw = head[i];
			int nPos = ArrayComm.getIgnoreCasePosOfName(arHead, sHeadRaw);
			if (nPos<0) {
				list.add(sHeadRaw);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			delHead(list.get(i));
		}
		clearIndex();
	}
	
	public void delHead(String sHead) {
		int nIdx = getPosOfName(sHead);
		delHead(nIdx);
	}
	
	public void delHead(int nIdx) {
		if (nIdx > -1) {
			List<String> listHead = new ArrayList<String>();
			ArrayComm.arraysToList(head, listHead);
			listHead.remove(nIdx);
			head = ArrayComm.listToStringArray(listHead);
			//
			List<Object> listData = new ArrayList<Object>();
			ArrayComm.arraysToList(data, listData);
			listData.remove(nIdx);
			data = listData.toArray();
			clearIndex();
		}
	}
	
	public void delHead(String[] arHead) {
		for (int i=0; i<arHead.length; i++) {
			delHead(arHead[i]);
		}
		/*Integer[] nPos = getHeadPosArrayOfName(arHead);
		if (nPos != null && nPos.length > 0) {
			List<String> listHead = new ArrayList<String>();
			CArrayComm.arraysToList(head, listHead);
			List<Object> listData = new ArrayList<Object>();
			CArrayComm.arraysToList(data, listData);
			for (int i = nPos.length-1; i >= 0; i--) {
				int nIdx = nPos[i];
				if (nIdx > -1) {
					listHead.remove(nIdx);
					listData.remove(nIdx);
				}
			}
			head = CArrayComm.listToStringArray(listHead);
			data = listData.toArray();
			clearIndex();
		} */
	}
	
	private void clearIndex() {
		mapHead.clear();
	}
	
	private void initIndex() {
		mapHead.clear();
		for (int i = 0; i < head.length; i++) {
			mapHead.put(head[i].toLowerCase(), i);
		}
	}

	public GsonRow remain(Integer[] nIdxs) {
		GsonRow g = new GsonRow();
		g.head = new String[nIdxs.length];
		g.data = new Object[nIdxs.length];
		for (int i = 0; i < nIdxs.length; i++) {
			int nIdx = nIdxs[i];
			g.head[i] = head[nIdx];
		}
		for (int i = 0; i < nIdxs.length; i++) {
			int nIdx = nIdxs[i];
			g.data[i] = data[nIdx];
		}
		return g;
	}
	
	public GsonRow remain(String[] arHead) {
		Integer[] listCols=getHeadPosArrayOfName(arHead);
		return remain(listCols);
	}
	
	public int getPosOfName(String headName) {
		int n = -1;
		try {
			if (mapHead.size()<=0) {
				initIndex();
			}
			n = mapHead.get(headName.toLowerCase());
		} catch (Exception e) {

		}
		return n;
	}
	
	public String getHeadValueOfIdx(int nIdx) {
		try {
			return head[nIdx];
		} catch (Exception e) {
			return null;
		}
	}

	@Deprecated
	public Object getValueOfName(String colName) {
		return getValue(colName, null);
	}
	
	public Object getValue(String colName) {
		return getValue(colName, null);
	}
	
	public Object getValue(String colName, Object value) {
		int nIdx = getPosOfName(colName);
		return getValue(nIdx, value);
	}
	
	/**
	 * @param rowNo
	 * @param colName
	 * @return
	 */
	public Date getDateTime(String colName) {
		int colNo = this.getPosOfName(colName);
		return getDateTime(colNo, null);
	}
	
	public Date getDateTime(String colName, Date value) {
		int colNo = this.getPosOfName(colName);
		return getDateTime(colNo, value);
	}
	
	public Date getDate(String colName) {
		return getDate(colName, null);
	}
	
	public Date getDate(String colName, Date value) {
		int colNo = this.getPosOfName(colName);
		return ObjectComm.objectToDate(getValue(colNo));
	}
	
	public Date getDateFmt(String colName, String fmtDate) {
		int colNo = this.getPosOfName(colName);
		return getDateFmt(colNo, fmtDate);
	}
	
	public Date getDateFmt(String colName, String fmtDate, Date value) {
		int colNo = this.getPosOfName(colName);
		return getDateFmt(colNo, fmtDate, value);
	}

	/**
	 * @param rowNo
	 * @param colNo
	 * @return
	 */
	public Date getDateTime(int colNo) {
		return ObjectComm.objectToDateTime(getValue(colNo));
	}
	
	public Date getDateTime(int colNo, Date value) {
		return ObjectComm.objectToDateTime(getValue(colNo), value);
	}
	
	public Date getDate(int colNo) {
		return ObjectComm.objectToDate(getValue(colNo));
	}
	
	public Date getDate(int colNo, Date value) {
		return ObjectComm.objectToDate(getValue(colNo), value);
	}
	
	public Date getDateFmt(int colNo, String fmtDate) {
		return ObjectComm.objectToDateFmt(getValue(colNo), fmtDate);
	}
	
	public Date getDateFmt(int colNo, String fmtDate, Date dateValue) {
		return ObjectComm.objectToDateFmt(getValue(colNo), fmtDate, dateValue);
	}

	@Deprecated
	public void setValueOfName(String colName, Object value) {
		setValue(colName, value);
	}
	
	public void setValue(String colName, Object value) {
		int colNo = getPosOfName(colName);
		if (colNo > -1) {
			data[colNo] = value;
		}
	}

	@Deprecated
	public Object getValueOfIdx(int nIdx) {
		return getValue(nIdx, null);
	}
	
	public Object getValue(int nIdx) {
		return getValue(nIdx, null);
	}
	
	public Object getValue(int nIdx, Object value) {
		int nPos = nIdx;
		if (nPos > -1) {
			Object obj = data[nPos];
			if (obj == null) {
				return value;
			} else {
				return obj;
			}
		} else {
			return value;
		}
	}
	
	@Deprecated
	public void setValueOfIdx(int idx, Object value) {
		setValue(idx, value);
	}
	
	public void setValue(int idx, Object value) {
		if (idx > -1) {
			data[idx] = value;
		}
	}
	
	public Double getDouble(int colNo) {
		return ObjectComm.objectToDouble(getValue(colNo));
	}
	
	public Double getDouble(int colNo, Object value) {
		return ObjectComm.objectToDouble(getValue(colNo), value);
	}
	
	public Integer getInteger(int colNo) {
		return ObjectComm.objectToInteger(getValue(colNo));
	}
	
	public Integer getInteger(int colNo, Object value) {
		return ObjectComm.objectToInteger(getValue(colNo), value);
	}
	
	public BigDecimal getBigDecimal(int colNo) {
		return ObjectComm.objectToBigDecimal(getValue(colNo));
	}
	
	public BigDecimal getBigDecimal(int colNo, Object value) {
		return ObjectComm.objectToBigDecimal(getValue(colNo), value);
	}
	
	public BigInteger getBigInteger(int colNo) {
		return ObjectComm.objectToBigInteger(getValue(colNo));
	}
	
	public BigInteger getBigInteger(int colNo, Object value) {
		return ObjectComm.objectToBigInteger(getValue(colNo), value);
	}
	
	public Long getLong(int colNo) {
		return ObjectComm.objectToLong(getValue(colNo));
	}
	
	public Long getLong(int colNo, Object value) {
		return ObjectComm.objectToLong(getValue(colNo), value);
	}
	
	public String getString(int colNo) {
		return ObjectComm.objectToString(getValue(colNo));
	}
	
	public String getString(int colNo, Object value) {
		return ObjectComm.objectToString(getValue(colNo), value);
	}
	
	public Boolean getBoolean(int colNo) {
		return ObjectComm.objectToBoolean(getValue(colNo));
	}
	
	public Boolean getBoolean(int colNo, Object value) {
		return ObjectComm.objectToBoolean(getValue(colNo), value);
	}
	public Double getDouble(String colName) {
		return ObjectComm.objectToDouble(getValue(colName));
	}
	
	public Integer getInteger(String colName) {
		return ObjectComm.objectToInteger(getValue(colName));
	}
	
	public Integer getInteger(String colName, Object value) {
		return ObjectComm.objectToInteger(getValue(colName), value);
	}
	
	public BigDecimal getBigDecimal(String colName) {
		return ObjectComm.objectToBigDecimal(getValue(colName));
	}
	
	public BigDecimal getBigDecimal(String colName, Object value) {
		return ObjectComm.objectToBigDecimal(getValue(colName), value);
	}
	
	public BigInteger getBigInteger(String colName) {
		return ObjectComm.objectToBigInteger(getValue(colName));
	}
	
	public BigInteger getBigInteger(String colName, Object value) {
		return ObjectComm.objectToBigInteger(getValue(colName), value);
	}
	
	public Double getDouble(String colName, Object value) {
		return ObjectComm.objectToDouble(getValue(colName), value);
	}
	
	public Long getLong(String colName) {
		return ObjectComm.objectToLong(getValue(colName));
	}
	
	public Long getLong(String colName, Object value) {
		return ObjectComm.objectToLong(getValue(colName), value);
	}
	
	public String getString(String colName) {
		return ObjectComm.objectToString(getValue(colName));
	}
	
	public String getString(String colName, Object value) {
		return ObjectComm.objectToString(getValue(colName), value);
	}
	
	public Boolean getBoolean(String colName) {
		return ObjectComm.objectToBoolean(getValue(colName));
	}
	
	public Boolean getBoolean(String colName, Object value) {
		return ObjectComm.objectToBoolean(getValue(colName), value);
	}
	
	public int getDataLength() {
		if (this.data!=null) {
			return this.data.length;
		} else {
			return 0;
		}
	}

	public boolean isExistsData() {
		return getDataLength() > 0 ? true:false;
	}
	
	public boolean moveToEnd(String moveHead) {
		int idx = getPosOfName(moveHead);
		if (idx > -1) {
			if (idx == this.head.length-1) {
				return true;
			} else {
				Object arObj = this.getValue(idx);
				this.delHead(idx);
				this.addHeadData(moveHead, arObj);
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean moveToEnd(String[] moveHeads) {
		boolean bReturn = false;
		for (int i=0; i<moveHeads.length; i++) {
			if (moveToEnd(moveHeads[i])) {
				if (!bReturn) {
					bReturn = true;
				}
			}
		}
		return bReturn;
	}
	
//	public static void main(String[] args) throws Exception {
//		String[] heads = new String[]{"id","code","name"};
//		Object[] datas = new Object[]{1,"x",new Date()};
//		GsonHeadOneRow row = new GsonHeadOneRow(heads, datas);
//		//row.moveToEnd("id");
//		String s = row.objectToJsonStr();
//		System.out.println(s);
//		GsonHeadOneRow g = GsonHeadOneRow.parserString(s);
//		System.out.println(g.objectToJsonStr());
//	}

}
