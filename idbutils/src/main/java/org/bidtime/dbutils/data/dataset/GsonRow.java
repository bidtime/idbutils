package org.bidtime.dbutils.data.dataset;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bidtime.dbutils.utils.basic.ArrayComm;
import org.bidtime.dbutils.utils.basic.ObjectComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonRow extends GsonData {
	
	private static final Logger logger = LoggerFactory
			.getLogger(GsonRow.class);

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
		} else if (list == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("data is null");
			}
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
			}
		}
		setHead(head);
		setData(list);
		clearIndex();
	}
	
	Object[] data;

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}
	
//	protected JSONArray dataToJson() {
//		JSONArray ar1 = new JSONArray();
//		for (int j = 0; j < head.length; j++) {
//			Object obj;
//			if (j < data.length) {
//				obj = data[j];
//			} else {
//				obj = null;
//			}
//			ar1.put(JSONHelper.objToJsonObj(obj, null));
//		}
//		return ar1;
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
			this.setValue(nIdx, value);
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
			this.setValue(nIdx, value);
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
	}

	private GsonRow remain(Integer[] nIdxs) {
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
	
	public GsonRow remain(String[] arHead) throws SQLException {
		Integer[] listCols = getHeadPosArrayOfName(arHead);
		return remain(listCols);
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
	
	public void setValue(String colName, Object value) {
		int colNo = getPosOfName(colName);
		if (colNo > -1) {
			data[colNo] = value;
		}
	}
	
	public Object getValue(int nIdx) {
		return getValue(nIdx, null);
	}
	
	public Object getValue(int nIdx, Object value) {
		if (nIdx > -1) {
			Object obj = data[nIdx];
			if (obj == null) {
				return value;
			} else {
				return obj;
			}
		} else {
			return value;
		}
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
	
	@Override
	public int getDataLen() {
		if (this.data != null) {
			return this.data.length;
		} else {
			return 0;
		}
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
//		String[] heads = new String[]{"id", "code", "name", "memo", "mp"};
//		Object[] datas = new Object[]{1, "code1", "name1", "memo1", "mp1"};
//		GsonRow row = new GsonRow(heads, datas);
//		row.moveToEnd(new String[]{"id", "code"});
//		System.out.println(row.toString());
//		//row g = row.parserString(s);
//		//System.out.println(g.objectToJsonStr());
//	}

}
