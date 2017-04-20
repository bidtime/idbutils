package org.bidtime.dbutils.jdbc.sql.xml.parser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.ClazzMapCallback;
import org.bidtime.dbutils.gson.GsonEbUtils;
import org.bidtime.dbutils.gson.PropAdapt;
import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.sql.ArrayUtils;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.utils.comm.CaseInsensitiveHashSet;
import org.bidtime.utils.comm.SimpleHashMap;
import org.bidtime.utils.comm.SimpleHashSet;

public class TTableProps {

	private String className;
	private String tableName;
	private CaseInsensitiveHashSet setPk;
	
	public void setSetPk(CaseInsensitiveHashSet setPk) {
		this.setPk = setPk;
	}
	
	public CaseInsensitiveHashSet getSetPk() {
		return this.setPk;
	}

	private boolean nonePkInc=false;
	public boolean isNonePkInc() {
		return nonePkInc;
	}

	public void setNonePkInc(boolean nonePkInc) {
		this.nonePkInc = nonePkInc;
	}

	private boolean existDefault=false;
	private List<String> listDefault=null;

	public boolean isExistDefault() {
		return existDefault;
	}

	public void setExistDefault(boolean existDefault) {
		this.existDefault = existDefault;
	}

	private Map<String, ColumnPro> mapPropertyColumn = new SimpleHashMap<ColumnPro>();
	private Map<String, String> mapColumnDescript = new SimpleHashMap<String>();
	public Map<String, String> getMapColumnDescript() {
		return mapColumnDescript;
	}

	public void setMapColumnDescript(Map<String, String> mapColumnDescript) {
		this.mapColumnDescript = mapColumnDescript;
	}

	private Map<String, SqlHeadCountPro> mapSqlHeadPro = new HashMap<String, SqlHeadCountPro>();
	
	public String getSqlOfId(String id) {
		SqlHeadCountPro p = mapSqlHeadPro.get(id);
		if (p != null) {
			return p.getSql();
		} else {
			return null;
		}
	}
	
	public String getSqlOfId(String id, String colId) {
		SqlHeadCountPro p = mapSqlHeadPro.get(id);
		if (p != null) {
			return p.getSql(colId);
		} else {
			return null;
		}
	}
	
	public void addSqlHeadPro(SqlHeadCountPro p) {
		mapSqlHeadPro.put(p.getId(), p);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String[] getFieldPK() {
		List<String> list = new ArrayList<String>(this.setPk);
		return (String[])list.toArray(new String[list.size()]);
	}

	public void setFieldPK(String[] fieldPK) {
		List<String> list = Arrays.asList(fieldPK);
		this.setPk = new CaseInsensitiveHashSet(list);
	}
	
	public String getInsertSql(GsonRow g, boolean setDefault) {
		if (setDefault) {
			setDefaultValue(g);
		}
		return getInsertSqlOfJsonHead(tableName, g.getHead());
	}
	
	public String getInsertSql(GsonRow g, boolean setDefault, String insSql) {
		if (setDefault) {
			setDefaultValue(g);
		}
		return getInsertSqlOfJsonHead(tableName, g.getHead(), insSql);
	}
	
	public String getInsertSql(GsonRows g, boolean setDefault) {
		if (setDefault) {
			setDefaultValue(g);
		}
		return getInsertSqlOfJsonHead(tableName, g.getHead());
	}
	
	public String getInsertSql(GsonRows g, boolean setDefault, String insSql) {
		if (setDefault) {
			setDefaultValue(g);
		}
		return getInsertSqlOfJsonHead(tableName, g.getHead(), insSql);
	}
	
	private String getInsertSqlOfJsonHead(String tblName, String[] jsonHead) {
		List<String> listColumn=new ArrayList<String>();
		for (String sIdx: jsonHead) {
			if (mapColumnDescript.containsKey(sIdx)) {
				listColumn.add(sIdx);
			}
		}
		return SqlUtils.getInsertSql(tblName, ArrayUtils.listToStringArray(listColumn));			
	}
	
	private String getInsertSqlOfJsonHead(String tblName, String[] jsonHead, String insSql) {
		List<String> listColumn=new ArrayList<String>();
		for (String sIdx: jsonHead) {
			if (mapColumnDescript.containsKey(sIdx)) {
				listColumn.add(sIdx);
			}
		}
		return SqlUtils.getInsertSql(tblName, ArrayUtils.listToStringArray(listColumn), insSql);			
	}

	public String getUpdateSqlHead(String tblName, String[] jsonAllHead, String[] jsonPkHead) {
		Set<String> set = new SimpleHashSet(Arrays.asList(jsonPkHead));
		try {
			return getUpdateSqlHead(tblName, jsonAllHead, set);
		} finally {
			set.clear();
			set = null;
		}
	}
	
	public String getUpdateSqlHead(String tblName, String[] jsonAllHead,
			Set<String> setJsonPkHead) {
		List<String> listCols = new ArrayList<String>();
		List<String> listPks = new ArrayList<String>();
		for (int i = 0; i < jsonAllHead.length; i++) {
			String sIdx = jsonAllHead[i];
			if (setJsonPkHead.contains(sIdx)) {
				listPks.add(sIdx);
			} else {
				listCols.add(sIdx);
			}
		}
		return SqlUtils.getUpdateSql(tblName, ArrayUtils.listToStringArray(listCols),
				ArrayUtils.listToStringArray(listPks));
	}

	public String getSelectSql(String tblName) {
		return SqlUtils.getSelectSql(tblName, mapPropertyColumn);
	}

	public String getSelectSql() {
		return SqlUtils.getSelectSql(tableName, mapPropertyColumn);
	}

	public List<String> getJsonPk() {
		return new ArrayList<String>(getJsonPkSet());
	}
	
	public String[] getJsonPks() {
		Set<String> set = getJsonPkSet();
		String[] array2 = set.toArray(new String[set.size()]);
		return array2;
	}
	
	public Set<String> getJsonPkSet() {
		Set<String> set = new CaseInsensitiveHashSet();
		for (Map.Entry<String, ColumnPro> entry : mapPropertyColumn.entrySet()) {
			if (entry.getValue().getPk()) {
				set.add(entry.getKey());
			}
		}
		return set;
	}

	public String getExistsSql(String tblName, Object... ids) {
		return SqlUtils.getExistsSql(tblName, getFieldPK(), ids);
	}

	public String getExistsSql(String tblName, String[] flds, Object... ids) {
		return SqlUtils.getExistsSql(tblName, flds, ids);
	}

	public String getDeleteSql(String tblName, Object... ids) {
		return SqlUtils.getDeleteSql(tblName, getFieldPK(), ids);
	}

	public String getDeleteSql(String tblName, String[] flds, Object... ids) {
		return SqlUtils.getDeleteSql(tblName, flds, ids);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public TTableProps() {
	}

//	public void finished() {
//		setSqlHeadPro_CountSql();
//	}

	private void setDefaultValue(GsonRow r) {
		if (r == null || !r.isExistsData() || !this.existDefault) {
			return;
		}
		List<String> listAddHead = null;
		List<Object> listAddData = null;
		for (int i = 0; i < this.listDefault.size(); i++) {
			String head = this.listDefault.get(i);
			ColumnPro cp = this.mapPropertyColumn.get(head);
			if (cp == null) {
				continue;
			}
			Object objDefault = cp.getDefaultValue();			
			int nIdx = r.getPosOfName(head);
			if (nIdx == -1) {
				if (listAddHead == null) {
					listAddHead = new ArrayList<String>();
					listAddData = new ArrayList<Object>();
				}
				listAddHead.add(cp.getColumn());
				listAddData.add(objDefault);
			} else if (r.getValue(nIdx) == null) {
				r.setValue(nIdx, objDefault);
			}
		}
		if (listAddHead != null && !listAddHead.isEmpty()) {
			r.autoInsertHeadData(
					listAddHead.toArray(new String[listAddHead.size()]),
					listAddData.toArray(new Object[listAddData.size()]));
		}
	}
	
	private void setDefaultValue(GsonRows r) {
		if (r == null || !r.isExistsData() || !this.existDefault) {
			return;
		}
		List<String> listAddHead = null;
		List<Object> listAddData = null;
		for (int i = 0; i < this.listDefault.size(); i++) {
			String s = this.listDefault.get(i);
			ColumnPro cp = this.mapPropertyColumn.get(s);
			if (cp == null) {
				continue;
			}
			Object objDefault = cp.getDefaultValue();
			int nIdx = r.getPosOfName(s);
			if (nIdx == -1) {
				if (listAddHead == null) {
					listAddHead = new ArrayList<String>();
					listAddData = new ArrayList<Object>();
				}
				listAddHead.add(cp.getColumn());
				listAddData.add(objDefault);
			} else {
				Object[] listOld = r.getArray(nIdx);
				if (listOld != null && listOld.length > 0) {
					for (int nn = 0; nn < listOld.length; nn++) {
						Object objOld = listOld[nn];
						if (objOld == null) {
							listOld[nn] = objDefault;
						}
					}
					r.setArray(nIdx, listOld);
				} else {
					if (listAddHead == null) {
						listAddHead = new ArrayList<String>();
						listAddData = new ArrayList<Object>();
					}
					listAddHead.add(cp.getColumn());
					listAddData.add(objDefault);
				}
			}
		}
		if (listAddHead != null && !listAddHead.isEmpty()) {
			r.autoInsertHeadData(
				listAddHead.toArray(new String[listAddHead.size()]),
				listAddData.toArray(new Object[listAddData.size()]));
		}
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void setSqlHeadPro_CountSql() {
//		Set set = mapSqlHeadPro.entrySet();
//		Iterator it = set.iterator();
//		while(it.hasNext()) {
//			Map.Entry<String, SqlHeadCountPro> entry =
//				(Map.Entry<String, SqlHeadCountPro>) it.next();
//			SqlHeadCountPro p = entry.getValue();
//			if (p!=null && StringUtils.isNotEmpty(p.getCountSqlId())) {
//				//sqlId和countId,都已经放在一起了,所以这里再需要查找一下
//				SqlHeadCountPro hp = mapSqlHeadPro.get(p.getCountSqlId());
//				if (hp != null && StringUtils.isNotEmpty(hp.getSql())) {
//					p.setCountSql(hp.getSql());					
//				}
//			}
//		}
//	}

	public void addColsCommonPro(String sJsonColumn, ColumnPro p) {
		if (StringUtils.isNotEmpty(sJsonColumn)) {
			this.mapColumnDescript.put(p.getColumn(), sJsonColumn);
			this.mapPropertyColumn.put(sJsonColumn, p);
			if (p.getDefaultValue() != null && p.getNotNull()) {
				if (listDefault == null) {
					listDefault = new ArrayList<String>();
				}
				listDefault.add(p.getColumn());
				if (!this.existDefault) {
					this.existDefault = true;
				}
			}
		}
	}

	public void addColsPkPro(String sJsonColumn, ColumnPro p) {
		if (StringUtils.isNotEmpty(sJsonColumn)) {
			this.mapColumnDescript.put(p.getColumn(), sJsonColumn);
			this.mapPropertyColumn.put(sJsonColumn, p);
			if (!p.getIdentity() && !this.nonePkInc) {
				nonePkInc = true;
			}
			if (p.getDefaultValue() != null && p.getNotNull()) {
				if (listDefault == null) {
					listDefault = new ArrayList<String>();
				}
				listDefault.add(p.getColumn());
				if (!this.existDefault) {
					this.existDefault = true;
				}
			}
		}
	}
	
	public GsonRow mapToRow(Map<String, Object> map) {
		return GsonEbUtils.mapToRow(map);
	}
	
	public GsonRow mapToRow(Map<String, Object> map, boolean removePk) {
		GsonRow row = GsonEbUtils.mapToRow(map);
		if (row != null && !isNonePkInc() && removePk) {
			row.delHead(getFieldPK());
		}		
		return row;
	}
	
	public GsonRows mapsToRows(List<Map<String, Object>> list) {
		return GsonEbUtils.mapsToRows(list);
	}
	
	public GsonRows mapsToRows(List<Map<String, Object>> list, boolean removePk) {
		GsonRows rows = GsonEbUtils.mapsToRows(list);
		if (rows != null && !isNonePkInc() && removePk) {
			rows.delHead(getFieldPK());
		}
		return rows;
	}
	
	public GsonRow clazzToRow(Object object, PropAdapt pa) throws SQLException {
		return GsonEbUtils.mapToRow(clazzToMap(object, pa));
	}
	
	public GsonRow clazzToRow(Object object, boolean bRemovePk, PropAdapt pa) throws SQLException {
		GsonRow row = GsonEbUtils.mapToRow(clazzToMap(object, pa));
		if (row != null && !isNonePkInc() && bRemovePk) {
			row.delHead(getFieldPK());
		}
		return row;
	}
	
	@SuppressWarnings("rawtypes")
	public GsonRows clazzToRows(List list, PropAdapt pa) throws SQLException {
		return GsonEbUtils.mapsToRows(clazzToMap(list, pa));
	}
	
	@SuppressWarnings("rawtypes")
	public GsonRows clazzToRows(List list, boolean removePk, PropAdapt pa) throws SQLException {
		GsonRows rows = GsonEbUtils.mapsToRows(clazzToMap(list, pa));
		if (rows != null && !isNonePkInc() && removePk) {
			rows.delHead(getFieldPK());
		}
		return rows;
	}

	public static Map<String, Object> clazzToMap(Object o,
			final Map<String, ColumnPro> mapProperty, boolean force, PropAdapt pa) throws SQLException {
		try {
			return GsonEbUtils.clazzToMap(o, new ClazzMapCallback<String, String>() {
				@Override
				public String getIt(String s) throws Exception {
					if (mapProperty != null && !mapProperty.isEmpty()) {
						ColumnPro cp = mapProperty.get(s);
						if (cp != null) {
							return cp.getColumn();
						} else {
							return null;
						}
					} else {
						return s;
					}
				}
			}, pa, false);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	public Map<String, Object> clazzToMap(Object object, PropAdapt pa) throws SQLException {
		return clazzToMap(object, this.mapPropertyColumn, false, pa);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> clazzToMap(List list, PropAdapt pa) throws SQLException {
		if (list != null && list.size()>0) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (int i=0; i<list.size(); i++) {
				Object object = list.get(i);
				listMap.add(clazzToMap(object, pa));
			}
			return listMap;
		} else {
			return null;
		}
	}

}