package org.bidtime.dbutils.gson.dataset;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.JSONHelper;
import org.bidtime.utils.basic.CArrayComm;
import org.bidtime.utils.basic.ObjectComm;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonRows {
	private static final Logger logger = LoggerFactory
			.getLogger(GsonRows.class);

	HashMap<String, Integer> mapHead = new HashMap<String, Integer>();

	public GsonRows() {
	}

	public GsonRows(String[] head, List<Object[]> list) {
		setHeadList(head, list);
	}

	public void setHeadList(String[] head, Object[][] array2) {
		if (head == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("head is null");
			}
			// throw new Exception("xml head is null ");
		} else if (array2 == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("data is null");
			}
			// throw new Exception("xml data is null ");
		} else if (array2.length < 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("data < 0");
			}
			// throw new Exception("xml data is null ");
		} else {
			Object[] oo = array2[0];
			if (oo == null) {
				// logger.info("xml data is null");
			} else if (oo.length < 1) {
				// logger.info("xml data length is 0");
			} else {
				if (head.length != oo.length) {
					StringBuilder sb = new StringBuilder();
					sb.append("head( ");
					sb.append(head.length);
					sb.append(" )");
					sb.append(" and data( ");
					sb.append(oo.length);
					sb.append(" ) isn't mismatch.");
					//logger.error(sb.toString());
					Exception e = new Exception(sb.toString());
					logger.error("setHeadList:", e);
				}
			}
		}

		this.setHead(head);
		this.setData(array2);
		this.clearIndex();
	}

	public void setHeadList(String[] head, List<Object[]> list) {
		if (head == null) {
			logger.error("head is null");
			// throw new Exception("xml head is null ");
		} else if (list == null) {
			logger.error("data is null");
			// throw new Exception("xml data is null ");
		} else if (list.size() < 1) {
			logger.error("data size < 0");
			// throw new Exception("xml data is null ");
		} else {
			Object[] oo = list.get(0);
			if (oo == null) {
				logger.error("data is null");
			} else if (oo.length < 1) {
				logger.info("data length is 0");
			} else {
				if (head.length != oo.length) {
					StringBuilder sb = new StringBuilder();
					sb.append("head( ");
					sb.append(head.length);
					sb.append(" )");
					sb.append(" and data( ");
					sb.append(oo.length);
					sb.append(" ) isn't mismatch.");
					//logger.error(sb.toString());
					Exception e = new Exception(sb.toString());
					logger.error("setHeadList:", e);
				}
			}
		}

		this.setHead(head);
		Object[][] array2 = (Object[][]) list
				.toArray(new Object[list.size()][head.length]);
		this.setData(array2);
		this.clearIndex();
	}

	public void addListData(List<Object[]> list) {
		if (list != null && list.size() > 0 && data != null) {
			int nLen = data.length + list.size();
			Object[][] objReturn = new Object[nLen][head.length];
			for (int i = 0; i < data.length; i++) {
				objReturn[i] = data[i];
			}
			for (int i = data.length, j = 0; j < list.size(); i++, j++) {
				objReturn[i] = list.get(j);
			}
			data = objReturn;
		}
	}

	// 一维转二维
	private static Object[][] oneDegreeToTwoDegree(Object[] objects, int row,
			int col) {
		int k = 0;
		if (objects != null) {
			Object[][] twoDegree = new Object[row][col];
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					twoDegree[i][j] = objects[k];
					k++;
				}
			}
			return twoDegree;
		} else {
			return null;
		}
	}

	private static Object[][] oneDegreeToTwoDegree(Object[] objects) {
		if (objects != null) {
			return oneDegreeToTwoDegree(objects, 1, objects.length);
		} else {
			return null;
		}
	}

	public GsonRows(String[] head, Object[] objects) {
		this.setHead(head);
		// List<Object[]> list = new ArrayList<Object[]>();
		// list.add(objects);
		// Object[][] array2 = (Object[][])list.toArray(new
		// Object[list.size()]);
		Object[][] array2 = oneDegreeToTwoDegree(objects);
		this.setData(array2);
		this.clearIndex();
	}

	public GsonRows(String[] head, Object[][] array2) {
		this.setHead(head);
		this.setData(array2);
		this.clearIndex();
	}

	protected String[] head;

	protected Object[][] data;

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

	public void autoInsertHeadData(String[] arHead, Object[] arData) {
		for (int i = 0; i < arHead.length; i++) {
			autoInsertHeadData(arHead[i], arData[i], false);
		}
		clearIndex();
	}

	public void autoInsertHeadArray(String headName, Object[] arData) {
		if (this.getPosOfName(headName) < 0) {
			insertHeadArray(headName, (Object[])arData, true);
		}
	}

	public void autoAddHead(String headName)
			throws Exception {
		if (this.getPosOfName(headName) < 0) {
			addHeadData(headName, (Object)null, true);
		}
	}

	public void autoAddHead(String[] heads)
			throws Exception {
		autoAddHeadData(heads, (Object[])null);
	}

	public void autoAddHeadArray(String headName, Object[] arData)
			throws Exception {
		if (this.getPosOfName(headName) < 0) {
			addHeadArray(headName, (Object[])arData, true);
		}
	}

	public void autoAddHeadData(String[] arHead, Object[] arData) {
		for (int i = 0; i < arHead.length; i++) {
			autoAddHeadData(arHead[i], arData[i], false);
		}
		clearIndex();
	}

	public void autoAddHeadData(String headName, Object o, boolean bInitIndex) {
		if (this.getPosOfName(headName) < 0) {
			addHeadData(headName, o, bInitIndex);
		}
	}

	public void autoInsertHead(String headName) {
		if (this.getPosOfName(headName) < 0) {
			insertHeadData(headName, (Object)null, true);
		}
	}

	public void autoInsertHead(String[] heads) {
		autoInsertHeadData(heads, (Object[])null);
	}

	public void autoInsertHeadData(String headName, Object o) {
		if (this.getPosOfName(headName) < 0) {
			insertHeadData(headName, o, true);
		}
	}

	public void autoInsertHeadData(String headName, Object o, boolean bInitIndex) {
		if (this.getPosOfName(headName) < 0) {
			insertHeadData(headName, o, bInitIndex);
		}
	}

	public void autoAddHeadData(String headName, Object o) {
		if (this.getPosOfName(headName) < 0) {
			addHeadData(headName, o);
		}
	}

	public void addHead(String headName) {
		addHeadData(headName, (Object)null, true);
	}

	public void addHead(String[] arHead) {
		addHeadData(arHead, (Object[])null);
	}

	public void addHeadData(String arHead, Object o) {
		addHeadData(arHead, o, true);
	}

	private void addHeadData(String arHead, Object o, boolean bInitIndex) {
		head = CArrayComm.mergeStringArray(head, arHead);
		Object[][] objReturn = new Object[data.length][head.length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < head.length; j++) {
				if (j < head.length - 1) {
					objReturn[i][j] = data[i][j];
				} else {
					objReturn[i][j] = o;
				}
			}
		}
		data = objReturn;
		if (bInitIndex) {
			clearIndex();
		}
	}

	public void addHeadArray(String headName, Object[] arObject) throws Exception {
		addHeadArray(headName, arObject, true);
	}
	
	private void addHeadArray(String oneHead, Object[] arObject, boolean bInitIndex)
			throws Exception {
		head = CArrayComm.mergeStringArray(head, oneHead);
		Object[][] objReturn = new Object[data.length][head.length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < head.length; j++) {
				if (j < head.length - 1) {
					objReturn[i][j] = data[i][j];
				} else {
					objReturn[i][j] = arObject[i];
				}
			}
		}
		data = objReturn;
		if (bInitIndex) {
			clearIndex();
		}
	}

	private void insertHeadData(String arHead, Object o, boolean bInitIndex) {
		head = CArrayComm.mergeStringArray(arHead, head);
		Object[][] objReturn = new Object[data.length][head.length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < head.length; j++) {
				if (j == 0) {
					objReturn[i][j] = o;
				} else {
					objReturn[i][j] = data[i][j - 1];
				}
			}
		}
		data = objReturn;
		if (bInitIndex) {
			clearIndex();
		}
	}
	
	public void insertHead(String[] arHead) {
		insertHeadData(arHead, (Object[])null);
	}
	
	public void insertHead(String arHead) {
		insertHeadData(arHead, (Object)null, true);
	}
	
	public void insertHeadArray(String arHead, Object[] arObject) {
		insertHeadArray(arHead, arObject, true);
	}
	
	private void insertHeadArray(String arHead, Object[] arObject, boolean bInitIndex) {
		head = CArrayComm.mergeStringArray(arHead, head);
		Object[][] objReturn = new Object[data.length][head.length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < head.length; j++) {
				if (j == 0) {
					objReturn[i][j] = arObject[i];
				} else {
					objReturn[i][j] = data[i][j - 1];
				}
			}
		}
		data = objReturn;
		if (bInitIndex) {
			clearIndex();
		}
	}

	public void insertHeadData(String[] arHead, Object[] arData) {
		for (int i = 0; i < arHead.length; i++) {
			if (arData != null && i<arData.length) {
				insertHeadData(arHead[i], arData[i], false);				
			} else {
				insertHeadData(arHead[i], (Object)null, false);
			}
		}
		clearIndex();
	}

	public void addHeadData(String[] arHead, Object[] arData) {
		for (int i = 0; i < arHead.length; i++) {
			if (arData != null && i<arData.length) {
				addHeadData(arHead[i], arData[i], false);
			} else {
				addHeadData(arHead[i], (Object)null, false);				
			}
		}
		clearIndex();
	}

	public String[] getHead() {
		return head;
	}

	public void setHead(String[] head) {
		this.head = head;
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	public GsonRows remain(Integer[] nIdxs) {
		GsonRows g = new GsonRows();
		g.head = new String[nIdxs.length];
		g.data = new Object[data.length][nIdxs.length];
		for (int i = 0; i < nIdxs.length; i++) {
			int nIdx = nIdxs[i];
			g.head[i] = head[nIdx];
		}
		for (int i = 0; i < nIdxs.length; i++) {
			int nIdx = nIdxs[i];
			for (int j = 0; j < data.length; j++) {
				g.data[j][i] = data[j][nIdx];
			}
		}
		return g;
	}

	public GsonRows remain(String[] arHead) {
		Integer[] listCols = getHeadPosArrayOfName(arHead);
		return remain(listCols);
	}

	public void setHeadValueOfObject(String arHead, Object o) {
		setHeadsValueOfObject(new String[] { arHead }, o);
	}

	public void setHeadsValueOfObject(String[] arHead, Object o) {
		for (String head : arHead) {
			int idx = getPosOfName(head);
			if (idx > -1) {
				for (int i = 0; i < this.getDataLength(); i++) {
					this.setValue(i, idx, o);
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Integer[] getHeadPosArrayOfName(String[] arHead) {
		List<Integer> list = new ArrayList<Integer>();
		for (String head : arHead) {
			int idx = getPosOfName(head);
			if (idx > -1) {
				list.add(idx);
			}
		}
		if (list.size() > 1) {
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
			int nPos = CArrayComm.getIgnoreCasePosOfName(arHead, sHeadRaw);
			if (nPos<0) {
				list.add(sHeadRaw);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			delHead(list.get(i), true);
		}
		clearIndex();
	}

	public void delHead(String[] arHead) {
		for (int i = 0; i < arHead.length; i++) {
			delHead(arHead[i]);
		}
	}

	public void delHead(String sHead) {
		delHead(sHead, true);
	}

	public void delHead(String sHead, boolean bInitIndex) {
		int nIdx = getPosOfName(sHead);
		delHead(nIdx, bInitIndex);
	}
	
	public void delHead(int nIdx, boolean bInitIndex) {
		if (nIdx > -1) {
			List<String> listHead = new ArrayList<String>();
			CArrayComm.arraysToList(head, listHead);
			listHead.remove(nIdx);
			head = CArrayComm.listToStringArray(listHead);
			//
			Object[][] objReturn = new Object[data.length][head.length];
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < head.length; j++) {
					if (j < nIdx) {
						objReturn[i][j] = data[i][j];
					} else {
						objReturn[i][j] = data[i][j + 1];
					}
				}
			}
			data = objReturn;
			if (bInitIndex) {
				clearIndex();
			}
		}
	}

	private void clearIndex() {
		mapHead.clear();
	}

	public void initIndex() {
		mapHead.clear();
		for (int i = 0; i < head.length; i++) {
			mapHead.put(head[i].toLowerCase(), i);
		}
	}

	public int getPosOfName(String headName) {
		int n = -1;
		try {
			if (mapHead.size() <= 0) {
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
			logger.info(e.getMessage());
			return null;
		}
	}

	public Object getValue(int rowNo, String colName) {
		return getValue(rowNo, colName, null);
	}

	public Object getValue(int rowNo, String headName, Object value) {
		int colNo = getPosOfName(headName);
		return getValue(rowNo, colNo, value);
	}

	public Object getValue(int rowNo, int colNo) {
		return getValue(rowNo, colNo, null);
	}

	public Object getValue(int rowNo, int colNo, Object value) {
		if (colNo > -1) {
			Object obj = data[rowNo][colNo];
			if (obj == null) {
				return value;
			} else {
				return obj;
			}
		} else {
			return value;
		}
	}
	
	public Double getDouble(int rowNo, int colNo) {
		return ObjectComm.objectToDouble(getValue(rowNo, colNo));
	}
	
	public Double getDouble(int rowNo, int colNo, Object value) {
		return ObjectComm.objectToDouble(getValue(rowNo, colNo), value);
	}
	
	public Integer getInteger(int rowNo, int colNo) {
		return ObjectComm.objectToInteger(getValue(rowNo, colNo));
	}
	
	public Integer getInteger(int rowNo, int colNo, Object value) {
		return ObjectComm.objectToInteger(getValue(rowNo, colNo), value);
	}
	
	public BigDecimal getBigDecimal(int rowNo, int colNo) {
		return ObjectComm.objectToBigDecimal(getValue(rowNo, colNo));
	}
	
	public BigDecimal getBigDecimal(int rowNo, int colNo, Object value) {
		return ObjectComm.objectToBigDecimal(getValue(rowNo, colNo), value);
	}
	
	public BigInteger getBigInteger(int rowNo, int colNo) {
		return ObjectComm.objectToBigInteger(getValue(rowNo, colNo));
	}
	
	public BigInteger getBigInteger(int rowNo, int colNo, Object value) {
		return ObjectComm.objectToBigInteger(getValue(rowNo, colNo), value);
	}
	
	public Long getLong(int rowNo, int colNo) {
		return ObjectComm.objectToLong(getValue(rowNo, colNo));
	}
	
	public Long getLong(int rowNo, int colNo, Object value) {
		return ObjectComm.objectToLong(getValue(rowNo, colNo), value);
	}
	
	public String getString(int rowNo, int colNo) {
		return ObjectComm.objectToString(getValue(rowNo, colNo));
	}
	
	public String getString(int rowNo, int colNo, Object value) {
		return ObjectComm.objectToString(getValue(rowNo, colNo), value);
	}
	
	public Boolean getBoolean(int rowNo, int colNo) {
		return ObjectComm.objectToBoolean(getValue(rowNo, colNo));
	}
	
	public Boolean getBoolean(int rowNo, int colNo, Object value) {
		return ObjectComm.objectToBoolean(getValue(rowNo, colNo), value);
	}
	
	public Double getDouble(int rowNo, String colName) {
		return ObjectComm.objectToDouble(getValue(rowNo, colName));
	}
	
	public Double getDouble(int rowNo, String colName, Object value) {
		return ObjectComm.objectToDouble(getValue(rowNo, colName), value);
	}
	
	public Integer getInteger(int rowNo, String colName) {
		return ObjectComm.objectToInteger(getValue(rowNo, colName));
	}
	
	public Integer getInteger(int rowNo, String colName, Object value) {
		return ObjectComm.objectToInteger(getValue(rowNo, colName), value);
	}
	
	public BigDecimal getBigDecimal(int rowNo, String colName) {
		return ObjectComm.objectToBigDecimal(getValue(rowNo, colName));
	}
	
	public BigDecimal getBigDecimal(int rowNo, String colName, Object value) {
		return ObjectComm.objectToBigDecimal(getValue(rowNo, colName), value);
	}
	
	public BigInteger getBigInteger(int rowNo, String colName) {
		return ObjectComm.objectToBigInteger(getValue(rowNo, colName));
	}
	
	public BigInteger getBigInteger(int rowNo, String colName, Object value) {
		return ObjectComm.objectToBigInteger(getValue(rowNo, colName), value);
	}
	
	public Long getLong(int rowNo, String colName) {
		return ObjectComm.objectToLong(getValue(rowNo, colName));
	}
	
	public Long getLong(int rowNo, String colName, Object value) {
		return ObjectComm.objectToLong(getValue(rowNo, colName), value);
	}
	
	public String getString(int rowNo, String colName) {
		return ObjectComm.objectToString(getValue(rowNo, colName));
	}
	
	public String getString(int rowNo, String colName, Object value) {
		return ObjectComm.objectToString(getValue(rowNo, colName), value);
	}
	
	public Boolean getBoolean(int rowNo, String colName) {
		return ObjectComm.objectToBoolean(getValue(rowNo, colName));
	}
	
	public Boolean getBoolean(int rowNo, String colName, Object value) {
		return ObjectComm.objectToBoolean(getValue(rowNo, colName), value);
	}
	
	/**
	 * @param rowNo
	 * @param colName
	 * @return
	 */
	public Date getDateTime(int rowNo, String colName) {
		int colNo = this.getPosOfName(colName);
		return getDateTime(rowNo, colNo);
	}
	
	public Date getDateTime(int rowNo, String colName, Date value) {
		int colNo = this.getPosOfName(colName);
		return getDateTime(rowNo, colNo, value);
	}
	
	public Date getDate(int rowNo, String colName) {
		return getDate(rowNo, colName, null);
	}
	
	public Date getDate(int rowNo, String colName, Date value) {
		int colNo = this.getPosOfName(colName);
		return ObjectComm.objectToDate(getValue(rowNo, colNo));
	}
	
	public Date getDateFmt(int rowNo, String colName, String fmtDate) {
		int colNo = this.getPosOfName(colName);
		return getDateFmt(rowNo, colNo, fmtDate);
	}
	
	public Date getDateFmt(int rowNo, String colName, String fmtDate, Date value) {
		int colNo = this.getPosOfName(colName);
		return getDateFmt(rowNo, colNo, fmtDate, value);
	}

	/**
	 * @param rowNo
	 * @param colNo
	 * @return
	 */
	public Date getDateTime(int rowNo, int colNo) {
		return ObjectComm.objectToDateTime(getValue(rowNo, colNo));
	}
	
	public Date getDateTime(int rowNo, int colNo, Date value) {
		return ObjectComm.objectToDateTime(getValue(rowNo, colNo), value);
	}
	
	public Date getDate(int rowNo, int colNo) {
		return ObjectComm.objectToDate(getValue(rowNo, colNo));
	}
	
	public Date getDate(int rowNo, int colNo, Date value) {
		return ObjectComm.objectToDate(getValue(rowNo, colNo), value);
	}
	
	public Date getDateFmt(int rowNo, int colNo, String fmtDate) {
		return ObjectComm.objectToDateFmt(getValue(rowNo, colNo), fmtDate);
	}
	
	public Date getDateFmt(int rowNo, int colNo, String fmtDate, Date dateValue) {
		return ObjectComm.objectToDateFmt(getValue(rowNo, colNo), fmtDate, dateValue);
	}
	
	public void setValue(int rowNo, String colName, Object newValue) {
		int colNo = getPosOfName(colName);
		if (colNo > -1) {
			data[rowNo][colNo] = newValue;
		}
	}
	
	public void setValue(int rowNo, int colNo, Object newValue) {
		data[rowNo][colNo] = newValue;
	}

	public Object[] getArrayValueOfName(String headName) {
		int nIdx = getPosOfName(headName);
		return getArrayValueOfIdx(nIdx);
	}

	public Object[] getArrayValueOfIdx(int nIdx) {
		if (nIdx > -1) {
			Object[] obj = new Object[data.length];
			for (int i = 0; i < data.length; i++) {
				obj[i] = data[i][nIdx];
			}
			return obj;
		} else {
			return null;
		}
	}

	public void setArrayValueOfIdx(int nIdx, Object[] value) {
		if (nIdx > -1) {
			data[nIdx] = value;
		}
	}

	public void setArrayValueOfName(String headName, Object[] value) {
		int nIdx = getPosOfName(headName);
		setArrayValueOfIdx(nIdx, value);
	}

	public int getDataLength() {
		if (this.data != null) {
			return this.data.length;
		} else {
			return 0;
		}
	}

	public boolean isExistsData() {
		return getDataLength() > 0 ? true : false;
	}

	public void sortASC(String headName) {
		int index = getPosOfName(headName);
		if (index > -1) {
			CArrayComm.sortASC(this.data, index);
		}
	}

	public void sortDESC(String headName) {
		int index = getPosOfName(headName);
		if (index > -1) {
			CArrayComm.sortDESC(this.data, index);
		}
	}

	public void sortASC(int index) {
		if (index > -1) {
			CArrayComm.sortASC(this.data, index);
		}
	}

	public void sortDESC(int index) {
		if (index > -1) {
			CArrayComm.sortDESC(this.data, index);
		}
	}

	public boolean moveToEnd(String moveHead) throws Exception {
		int idx = getPosOfName(moveHead);
		if (idx > -1) {
			if (idx == this.head.length-1) {
				return true;
			} else {
				Object[] arObj = this.getArrayValueOfIdx(idx);
				this.delHead(idx, true);
				this.addHeadArray(moveHead, arObj);
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean moveToEnd(String[] moveHeads) throws Exception {
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

	public String objectToJsonStr() {
		return toString();
	}

	public static GsonRows jsonStrToObject(String json) {
		return GsonRows.parserString(json);
	}
	
	private JSONArray dataToJson() {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < data.length; i++) {
			JSONArray ar1 = new JSONArray();
			Object[] arObj = data[i];
			for (int j = 0; j < head.length; j++) {
				Object obj;
				if (j<arObj.length) {
					obj = arObj[j];
				} else {
					obj = null;
				}
				ar1.put(JSONHelper.objToJsonObj(obj));
			}
			jsonArray.put(ar1);
		}
		return jsonArray;
	}

	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		if (head != null && head.length>0) {
			jsonObject.put("head", this.head);
		}
		if (this.data != null && this.data.length>0) {
			jsonObject.put("data", dataToJson());
		} else {
			jsonObject.put("data", JSONObject.NULL);
		}
		return jsonObject;
	}

	@Override
	public String toString() {
		JSONObject jsonObject1 = toJson();
		return jsonObject1.toString();
	}
	
	public GsonRows(String json) {
		fromString(json);
	}
	
	public GsonRows(JSONObject jsonObject) {
		fromJson(jsonObject);
	}

	public static GsonRows parserString(String json) {
		return parserString(json, false);
	}

	public static GsonRows parserString(String json, boolean bNew) {
		if (StringUtils.isNotEmpty(json)) {
			JSONObject jsonobj = new JSONObject(json);
			if (jsonobj.length() > 0 || bNew) {
				GsonRows rows = new GsonRows();
				rows.fromJson(jsonobj);
				return rows;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public void fromString(String json) {
		JSONObject jsonobj = new JSONObject(json);
		fromJson(jsonobj);
	}
	
	public void fromJson(JSONObject jsonObject) {
		JSONArray headArray = jsonObject.optJSONArray("head");
		if (headArray != null && headArray.length()>0) {
			head = new String[headArray.length()];
			for (int i = 0; i < headArray.length(); i++) {
				Object object = headArray.get(i);
				head[i] = (String)object;
			}
		}
		//data
		if (jsonObject.has("data") && !jsonObject.isNull("data")) {
			JSONArray dataArray = jsonObject.getJSONArray("data");
			if (dataArray != null) {
				this.data = new Object[dataArray.length()][];
				for (int i = 0; i < dataArray.length(); i++) {
					JSONArray arrayPerRow = (JSONArray)dataArray.get(i);
					Object[] objRow = new Object[arrayPerRow.length()];
					for (int j = 0; j < arrayPerRow.length(); j++) {
						objRow[j] = JSONHelper.jsonObjToObj(arrayPerRow.get(j));
					}
					data[i] = objRow;
				}
			} else {
				CArrayComm.clearArray(data);
			}
		} else {
			CArrayComm.clearArray(data);
		}
	}
	
//	private static String getJson() {
//		return "{\"head\":[\"code\",\"name\",\"id\"],\"data\":[[\"1\",\"1\",2],[\"1\",\"1\",3]]}";
//	}
//	
//	public static void main(String[] args) throws Exception {
//		GsonHeadManyRows rows = GsonHeadManyRows.parserString(getJson());
//		rows.moveToEnd(new String[]{"code", "name"});
//		System.out.println(rows.toString());
//	}
	
//	public static void main(String[] args) throws Exception {
//		String[] heads = new String[]{"id","code","name"};
//		Object[] datas = new Object[]{1,"x",new Date()};
//		GsonHeadManyRows rows = new GsonHeadManyRows(heads, datas);
//		//rows.moveToEnd("id");
//		String s = rows.objectToJsonStr();
//		System.out.println(s);
//		GsonHeadManyRows rows2 = GsonHeadManyRows.parserString(s);
//		System.out.println(rows2.objectToJsonStr());
//	}

}
