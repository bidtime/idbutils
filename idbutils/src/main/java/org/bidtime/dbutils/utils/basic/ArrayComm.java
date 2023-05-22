package org.bidtime.dbutils.utils.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ArrayComm {
	
//	public static Long[] stringToLongArray(String[] strs) throws Exception {
//		if (strs == null || strs.length<=0) {
//			return null;
//		}
//		Long[] lReturn = new Long[strs.length]; //声明long类型的数组
//		for(int i = 0; i<strs.length; i++) {
//			String str = strs[i];       		//将strs字符串数组中的第i个值赋值给str
//			Long thelong = Long.valueOf(str);	//将str转换为long类型，并赋值给thelong
//			lReturn[i] = thelong;					//将thelong赋值给 longs数组中对应的地方
//		}
//		return lReturn;  							//返回long数组
//	}
	
//	public static Object[] stringToObject(String[] strs, Class<T> type) throws Exception {
//		if (strs == null || strs.length<=0) {
//			return null;
//		}
//		Object[] arReturn = type.newInstance()[strs.length];
//		//Long[] longs = new Long[strs.length]; //声明long类型的数组
//		for(int i = 0; i<strs.length; i++) {
//			String str = strs[i];       		//将strs字符串数组中的第i个值赋值给str
//			Long thelong = Long.valueOf(str);	//将str转换为long类型，并赋值给thelong
//			longs[i] = thelong;					//将thelong赋值给 longs数组中对应的地方
//		}
//		return arReturn;  							//返回long数组
//	}

	public static String[] ObjectToStringArray(Object[] ar) throws Exception {
		if (ar == null || ar.length<=0) {
			return null;
		}
		String[] ia = new String[ar.length];
		for (int i = 0; i < ar.length; i++) {
			ia[i] = String.valueOf(ar[i]);
		}
		return ia;
	}

	public static Long[] StringsToLongArray(String[] ar) throws Exception {
		if (ar == null || ar.length<=0) {
			return null;
		}
		Long[] ia = new Long[ar.length];
		for (int i = 0; i < ar.length; i++) {
			ia[i] = Long.parseLong(ar[i]);
		}
		return ia;
	}

	public static Integer[] StringsToIntegerArray(String[] ar) throws Exception {
		if (ar == null || ar.length<=0) {
			return null;
		}
		Integer[] ia = new Integer[ar.length];
		for (int i = 0; i < ar.length; i++) {
			ia[i] = Integer.parseInt(ar[i]);
		}
		return ia;
	}

	@SuppressWarnings("rawtypes")
	public static Object getListZero(List list) {
		if ((list != null) && (list.size() > 0)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Object getListOne(List list) {
		if ((list != null) && (list.size() > 1)) {
			return list.get(1);
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Object getObjectOfIdx(List list, int nIdx) {
		if ((list != null) && (list.size() > 0)) {
			if (nIdx >= 0 && nIdx < list.size()) {
				return list.get(nIdx);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int getListCount(List list) {
		if ((list != null) && (list.size() > 0)) {
			return list.size();
		} else {
			return 0;
		}
	}

	public static String[] listToStringArray(List<String> list) {
		if (list != null) {
			return (String[]) list.toArray(new String[list.size()]);
		} else
			return null;
	}
	
	public static int getIgnoreCasePosOfName(String[] array, String s) {
		int nPos = -1;
		for (int i=0; i<array.length; i++) {
			if (StringUtils.equalsIgnoreCase(array[i], s)) {
				nPos = i;
			}
		}
		return nPos;
	}

	public static Object[][] listToObjectArray(List<Object[]> list) {
		if (list != null) {
			Object[][] objReturn = null;// new
										// Object[list.size()][list.get(0).];
			for (int i = 0; i < list.size(); i++) {
				Object[] a = list.get(i);
				if (objReturn == null) {
					objReturn = new Object[list.size()][a.length];
				}
				objReturn[i] = a;
			}
			return objReturn; // return (Object[][]) list.toArray(new
								// Object[list.size()]);
		} else {
			return null;
		}
	}

	public static Object[] listToObjectArray(List<Object[]> list, int nPos) {
		if (list != null) {
			Object[] objReturn = null;// new
										// Object[list.size()][list.get(0).];
			for (int i = 0; i < list.size(); i++) {
				Object a = list.get(i)[nPos];
				if (objReturn == null) {
					objReturn = new Object[list.size()];
				}
				objReturn[i] = a;
			}
			return objReturn;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String[] mapToStringArray(Map<String, String> map) {
		if (map != null) {
			List<String> list = new ArrayList<String>();
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				// Object key = entry.getKey();
				Object val = entry.getValue();
				list.add((String) val);
				// if (val instanceof Map) {
				// Iterator iter1 = ((Map)val).entrySet().iterator();
				// while (iter1.hasNext()) {
				// Map.Entry entry1 = (Map.Entry) iter1.next();
				// Object key1 = entry1.getKey();
				// Object val1 = entry.getValue();
				// }
				// }
			}
			return listToStringArray(list);
		} else
			return null;
	}

	public static String[] mapStringArray(String[] obj1, String[] obj2) {
		if (ArrayComm.getLenOfArray(obj1) <= 0) {
			return obj2;
		} else if (ArrayComm.getLenOfArray(obj2) <= 0) {
			return obj1;
		} else {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < obj1.length; i++) {
				map.put(obj1[i].toLowerCase(), obj1[i]);
				// list.add(obj1[i]);
			}
			for (int i = 0; i < obj2.length; i++) {
				// list.add(obj2[i]);
				map.put(obj2[i].toLowerCase(), obj2[i]);
			}
			return mapToStringArray(map);
		}
	}

	public static String[] mergeStringArray(String[] obj1, String[] obj2) {
		if (ArrayComm.getLenOfArray(obj1) <= 0) {
			return obj2;
		} else if (ArrayComm.getLenOfArray(obj2) <= 0) {
			return obj1;
		} else {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < obj1.length; i++) {
				list.add(obj1[i]);
			}
			for (int i = 0; i < obj2.length; i++) {
				list.add(obj2[i]);
			}
			return listToStringArray(list);
		}
	}

	public static String[] mergeStringArray(String[] obj1, String obj2) {
		return mergeStringArray(obj1, new String[] { obj2 });
	}

	public static String[] mergeStringArray(String obj2, String[] obj1) {
		return mergeStringArray(new String[] { obj2 }, obj1);
	}

	public static int getLenOfArray(Object[] obj1) {
		if (obj1 == null || obj1.length <= 0) {
			return 0;
		} else {
			return obj1.length;
		}
	}

	public static Object getArrayZero(Object[] obj1) {
		if ((obj1 != null) && (obj1.length > 0)) {
			return obj1[0];
		} else {
			return null;
		}
	}

	public static boolean isExistEmpty(Object[] obj1) {
		boolean bReturn = false;
		if ((obj1 != null) && (obj1.length > 0)) {
			for (int i = 0; i < obj1.length; i++) {
				Object objPer = obj1[i];
				if (objPer == null) {
					bReturn = true;
					break;
				} else {
					if (objPer instanceof String) {
						if (((String) objPer).trim().isEmpty()) {
							bReturn = true;
							break;
						}
					}
				}
			}
		} else {
			bReturn = true;
		}
		return bReturn;
	}

	public static Object[] mergeArray(Object[] obj1, Object[] obj2) {
		if (getLenOfArray(obj1) <= 0) {
			return obj2;
		} else if (getLenOfArray(obj2) <= 0) {
			return obj1;
		} else {
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < obj1.length; i++) {
				list.add(obj1[i]);
			}
			for (int i = 0; i < obj2.length; i++) {
				list.add(obj2[i]);
			}
			return list.toArray();
		}
	}

	public static Object[] mergeArray(Object obj1, Object[] obj2) {
		List<Object> list = new ArrayList<Object>();
		list.add(obj1);
		for (int i = 0; i < obj2.length; i++) {
			list.add(obj2[i]);
		}
		return list.toArray();
	}

	public static Object[] mergeArray(Object[] obj1, Object obj2) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < obj1.length; i++) {
			list.add(obj1[i]);
		}
		list.add(obj2);
		return list.toArray();
	}

	public static void clearArray(Object[] obj1) {
		if (obj1 != null && obj1.length>0) {
			for(int i = 0; i<obj1.length; i++) {
				obj1[i] = null;
			}
		}
	}

	public static Object[] mergeArray(Object[] obj1, Object obj2, Object obj3) {
		List<Object> list = new ArrayList<Object>();
		if (obj1 != null) {
			for (int i = 0; i < obj1.length; i++) {
				list.add(obj1[i]);
			}
		}
		list.add(obj2);
		list.add(obj3);
		return list.toArray();
	}

	public static String arrayToString(Object[] obj1, String split) {
		if (getLenOfArray(obj1) <= 0) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < obj1.length; i++) {
				if (!StringUtils.isEmpty(sb.toString())) {
					sb.append(split);
				}
				sb.append(obj1[i]);
			}
			return sb.toString();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void arraysToList(Object[] objs, List list) {
		for (int i = 0; i < objs.length; i++) {
			list.add(objs[i]);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void delListOfIdx(List list, int index) {
		if (list != null && list.size() > 0 && index < list.size()) {
			list.remove(index);
		}
	}

	public static Object[][] sortASC(Object[][] params, int index) {
		return sortIndex(params, index, false);
	}
	
	public static Object[][] sortDESC(Object[][] params, int index) {
		return sortIndex(params, index, true);
	}

	public static void clearArray(Object[][] params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length - 1; i++) {
				for (int j = i + 1; j < params.length; j++) {
					params[i][j] = null;
				}
			}
		}
	}

	public static Object[][] sortIndex(Object[][] params, int index, boolean desc) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length - 1; i++) {
				for (int j = i + 1; j < params.length; j++) {
					Object o1 = params[i][index];
					Object o2 = params[j][index];
					if (desc) {
						if (ObjectComm.compareObject(o2, o1) > 0) {
							Object[] tmp = params[i];
							params[i] = params[j];
							params[j] = tmp;
						}
					} else {
						if (ObjectComm.compareObject(o2, o1) < 0) {
							Object[] tmp = params[i];
							params[i] = params[j];
							params[j] = tmp;
						}
					}
				}
			}
		}
		return params;
	}
	
	public static List<Object[]> sortASC(List<Object[]> params, int index) {
		return sortIndex(params, index, false);
	}
	
	public static List<Object[]> sortDESC(List<Object[]> params, int index) {
		return sortIndex(params, index, true);
	}
	
	public static List<Object[]> sortIndex(List<Object[]> params, int index, boolean desc) {
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size() - 1; i++) {
				for (int j = i + 1; j < params.size(); j++) {
					Object o1 = params.get(i)[index];
					Object o2 = params.get(j)[index];
					if (desc) {
						if (ObjectComm.compareObject(o2, o1) > 0) {
							Object[] tmp = params.get(i);
							params.set(i, params.get(j));
							params.set(j, tmp);
						}
					} else {
						if (ObjectComm.compareObject(o2, o1) < 0) {
							Object[] tmp = params.get(i);
							params.set(i, params.get(j));
							params.set(j, tmp);
						}
					}
				}
			}
		}
		return params;
	}
}
