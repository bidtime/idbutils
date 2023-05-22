package org.bidtime.dbutils.data.dataset;

import java.lang.reflect.Array;
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

public class GsonRows extends GsonData {
	private static final Logger logger = LoggerFactory
			.getLogger(GsonRows.class);

	public GsonRows() {
	}

	public GsonRows(String[] head, List<List<Object>> list) {
		setHeadList(head, list);
	}
	
	public void resetHeadList(String[] head, Object[][] array) {
		this.setHead(head);
		this.setData(array);
		this.clearIndex();
	}
	
	public void resetHeadList(List<String> listHead, List<Object[]> listData) {
		String head[] = ArrayComm.listToStringArray(listHead);
		Object[][] array = (Object[][]) listData
				.toArray(new Object[listData.size()][head.length]);
		resetHeadList(head, array);
	}
	
	public void setHeadList(String[] head, List<List<Object>> list) {
		if (head == null) {
			logger.error("head is null");
		} else if (list == null) {
			logger.error("data is null");
		} else if (list.size() < 1) {
			logger.error("data size < 0");
		} else {
			List<Object> oo = list.get(0);
			if (oo == null) {
				logger.error("data is null");
			} else if (oo.size() < 1) {
				logger.info("data length is 0");
			} else {
				if (head.length != oo.size()) {
					StringBuilder sb = new StringBuilder();
					sb.append("head( ");
					sb.append(head.length);
					sb.append(" )");
					sb.append(" and data( ");
					sb.append(oo.size());
					sb.append(" ) isn't mismatch.");
					Exception e = new Exception(sb.toString());
					logger.error("setHeadList:", e);
				}
			}
		}
		this.setHead(head);
		Object[][] array2 = listsToArrays(list, Object.class);
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

	public void addList(List<List<Object>> list) {
		if (list != null && list.size() > 0 && data != null) {
			int nLen = data.length + list.size();
			Object[][] objReturn = new Object[nLen][head.length];
			for (int i = 0; i < data.length; i++) {
				objReturn[i] = data[i];
			}
			for (int i = data.length, j = 0; j < list.size(); i++, j++) {
				List<Object> ll = list.get(j);
				objReturn[i] = ll.toArray(new Object[0]);
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
  
	@SuppressWarnings("unchecked")
	public static <T> T[][] listsToArrays(List<List<T>> src, Class<T> type) {
    if (src == null || src.isEmpty()) {
      return null;
    }

    // 初始化泛型二维数组
    // JAVA中不允许这样初始化泛型二维数组： T[][] dest = new T[src.size()][];
    T[][] dest = dest = (T[][]) Array.newInstance(type, src.size(), src.get(0).size());

    for (int i = 0; i < src.size(); i++) {
      for (int j = 0; j < src.get(i).size(); j++) {
        dest[i][j] = src.get(i).get(j);
      }
    }

    return dest;
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
			insertArray(headName, (Object[])arData, true);
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

  public void testAddHeadData(String headName, Object o) {
    int idx = this.getPosOfName(headName);
    if (idx >= 0) {
      setColumnValue(idx, o);
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
		head = ArrayComm.mergeStringArray(head, arHead);
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

	public void addHeadArray(String headName, Object[] arObject) {
		addHeadArray(headName, arObject, true);
	}
	
	private void addHeadArray(String oneHead, Object[] arObject, 
			boolean bInitIndex) {
		head = ArrayComm.mergeStringArray(head, oneHead);
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
		head = ArrayComm.mergeStringArray(arHead, head);
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
	
	public void insertArray(String arHead, Object[] arObject) {
		insertArray(arHead, arObject, true);
	}
	
	private void insertArray(String arHead, Object[] arObject, boolean bInitIndex) {
		head = ArrayComm.mergeStringArray(arHead, head);
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

	public GsonRows remain(String[] arHead) throws SQLException {
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
        for (int i = 0; i < this.getDataLen(); i++) {
          this.setValue(i, idx, o);
        }
      }
    }
  }

  public void setColumnValue(int idx, Object o) {
    if (idx > -1) {
      for (int i = 0; i < this.getDataLen(); i++) {
        this.setValue(i, idx, o);
      }
    }
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
			ArrayComm.arraysToList(head, listHead);
			listHead.remove(nIdx);
			head = ArrayComm.listToStringArray(listHead);
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

	public Object[] getArray(String headName) {
		int nIdx = getPosOfName(headName);
		return getArray(nIdx);
	}

	public Object[] getArray(int nIdx) {
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

	public void setArray(int nIdx, Object[] value) {
		if (nIdx > -1) {
			data[nIdx] = value;
		}
	}

	public void setArray(String headName, Object[] value) {
		int nIdx = getPosOfName(headName);
		setArray(nIdx, value);
	}

	@Override
	public int getDataLen() {
		if (this.data != null) {
			return this.data.length;
		} else {
			return 0;
		}
	}

	public void sortASC(String headName) {
		int index = getPosOfName(headName);
		if (index > -1) {
			ArrayComm.sortASC(this.data, index);
		}
	}

	public void sortDESC(String headName) {
		int index = getPosOfName(headName);
		if (index > -1) {
			ArrayComm.sortDESC(this.data, index);
		}
	}

	public void sortASC(int index) {
		if (index > -1) {
			ArrayComm.sortASC(this.data, index);
		}
	}

	public void sortDESC(int index) {
		if (index > -1) {
			ArrayComm.sortDESC(this.data, index);
		}
	}

	public boolean moveToEnd(String moveHead) {
		int idx = getPosOfName(moveHead);
		if (idx > -1) {
			if (idx == this.head.length-1) {
				return true;
			} else {
				Object[] arObj = this.getArray(idx);
				this.delHead(idx, true);
				this.addHeadArray(moveHead, arObj);
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
	
//	@Override
//	protected JSONArray dataToJson() {
//		JSONArray jsonArray = new JSONArray();
//		for (int i = 0; i < data.length; i++) {
//			JSONArray ar1 = new JSONArray();
//			Object[] arObj = data[i];
//			for (int j = 0; j < head.length; j++) {
//				Object obj;
//				if (j<arObj.length) {
//					obj = arObj[j];
//				} else {
//					obj = null;
//				}
//				ar1.put(JSONHelper.objToJsonObj(obj, null));
//			}
//			jsonArray.put(ar1);
//		}
//		return jsonArray;
//	}
	
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
