package org.bidtime.dbutils.jdbc.sql.xml.parser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.gson.GsonEbUtils;
import org.bidtime.dbutils.gson.dataset.GsonRow;
import org.bidtime.dbutils.gson.dataset.GsonRows;
import org.bidtime.dbutils.jdbc.sql.ArrayUtils;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.utils.comm.CaseInsensitiveHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TTableProps {
	
	private static final Logger logger = LoggerFactory
			.getLogger(TTableProps.class);

	private String className="";
	private String tableName="";
	private String[] fieldPK=null;
	private boolean nonePkInc=false;
	public boolean isNonePkInc() {
		return nonePkInc;
	}

	public void setNonePkInc(boolean nonePkInc) {
		this.nonePkInc = nonePkInc;
	}

	private boolean encry=false;
	private boolean existDefault=false;
	private List<String> listDefault=null;

	public boolean isExistDefault() {
		return existDefault;
	}

	public void setExistDefault(boolean existDefault) {
		this.existDefault = existDefault;
	}

	private Map<String,ColumnPro> mapPropertyColumn = new HashMap<>();
	private Map<String,String> mapColumnDescript = new HashMap<>();
	public Map<String, String> getMapColumnDescript() {
		return mapColumnDescript;
	}

	public void setMapColumnDescript(Map<String, String> mapColumnDescript) {
		this.mapColumnDescript = mapColumnDescript;
	}

	private Map<String,JsonHeadPro> mapJsonHeadPro = new HashMap<>();
	private Map<String,SqlHeadCountPro> mapSqlHeadPro = new HashMap<>();
	
	//新的xml配置,使用此方法
	public HeadSqlArray getHeadSqlArrayOfId(String id) throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("sqlId:"+id);
		}
		HeadSqlArray headSqlArray = null;
		SqlHeadCountPro sqlHeadPro = getShcProOfId(id);
		if (sqlHeadPro != null) {
			JsonHeadPro jsonHeadPro = getJsonHeadProOfId(sqlHeadPro.getHeadId());
			if (jsonHeadPro != null) {
				headSqlArray = new HeadSqlArray(sqlHeadPro.getSql(), jsonHeadPro.getArHeadFlds(),
					sqlHeadPro.getCountSql(), this.mapColumnDescript);					
			} else {
				headSqlArray = new HeadSqlArray(sqlHeadPro.getSql(), sqlHeadPro.getCountSql());
			}
		} else {
			throw new SQLException("id:" + id + " is none sql props");
		}
		return headSqlArray;
	}
	
	public SqlHeadCountPro getShcProOfId(String id) {
		return mapSqlHeadPro.get(id);
	}
	
	public String getSqlOfId(String id) {
		SqlHeadCountPro p = getShcProOfId(id);
		if (p != null) {
			return p.getSql();
		} else {
			return null;
		}
	}
	
	public String[] getHeadArrayOfId(String id) {
		JsonHeadPro p = getJsonHeadProOfId(id);
		if (p != null) {
			return p.getArHeadFlds();
		} else {
			return null;
		}
	}
	
	private JsonHeadPro getJsonHeadProOfId(String id) {
		return mapJsonHeadPro.get(id);
	}
	
	public void addJsonHeadPro(JsonHeadPro p) {
		mapJsonHeadPro.put(p.getId(), p);
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

	public boolean isEncry() {
		return encry;
	}

	public void setEncry(boolean encry) {
		this.encry = encry;
	}
	
	public String[] getFieldPK() {
		return fieldPK;
	}

	public void setFieldPK(String[] fieldPK) {
		this.fieldPK = fieldPK;
	}
	
	public String getInsertSql(GsonRow g, boolean setDefault) {
		if (setDefault) {
			setDefaultValue(g);
		}
		return getInsertSqlOfJsonHead(tableName, g.getHead());
	}
	
	public String getInsertSql(GsonRows g, boolean setDefault) {
		if (setDefault) {
			setDefaultValue(g);
		}
		return getInsertSqlOfJsonHead(tableName, g.getHead());
	}
	
	private String getInsertSqlOfJsonHead(String tblName, String[] jsonHead) {
		List<String> listColumn=new ArrayList<String>();
		for (String sIdx: jsonHead) {
			ColumnPro p = this.mapPropertyColumn.get(sIdx.toLowerCase());
			if (p!=null) {
				listColumn.add(p.getColumn());
			} else {
				listColumn.add(sIdx);
			}
		}
		return SqlUtils.getInsertSql(tblName,ArrayUtils.listToStringArray(listColumn));			
	}

	public String getUpdateSqlHead(String tblName, String[] jsonAllHead, String[] jsonPkHead) {
		Map<String, Object> mapJsonPkHead = new CaseInsensitiveHashMap(jsonPkHead);
		try {
			List<String> listCols = new ArrayList<String>();
			List<String> listPks = new ArrayList<String>();
			for (int i = 0; i < jsonAllHead.length; i++) {
				String sIdx = jsonAllHead[i];
				ColumnPro p = this.mapPropertyColumn.get(sIdx.toLowerCase());
				if (p != null) {
					if (mapJsonPkHead.containsKey(sIdx)) {
						listPks.add(p.getColumn());
					} else {
						listCols.add(p.getColumn());
					}
				} else {
					listCols.add(sIdx);						
				}
			}
			return SqlUtils.getUpdateSql(tblName,ArrayUtils.listToStringArray(listCols)
					,ArrayUtils.listToStringArray(listPks));
		} finally {
			mapJsonPkHead.clear();
			mapJsonPkHead = null;
		}
	}

	/*
	 * 通过json名称,获取可以执行的update sql
	 */	
	public String getUpdateSqlPk(String tblName, String[] jsonHead, List<String> listPkJson) {
		List<String> listColumn=new ArrayList<String>();
		List<String> listPk=new ArrayList<String>();
		for (int i = 0; i< jsonHead.length; i++) {
			String sIdx = jsonHead[i].toLowerCase();
			ColumnPro p = this.mapPropertyColumn.get(sIdx);
			if (p != null) {
				if (p.getPk()) {
					listPk.add(p.getColumn());
					listPkJson.add(sIdx);
				} else {
					listColumn.add(p.getColumn());
				}
			} else {
				listColumn.add(sIdx);
			}
		}
		return SqlUtils.getUpdateSql(tblName,ArrayUtils.listToStringArray(listColumn),
				ArrayUtils.listToStringArray(listPk));
	}
	
	/*
	 * 获取可以执行的delete sql
	 */
	public String getDeleteSql(String tblName) {
		return SqlUtils.getDeleteSql(tblName, this.fieldPK);
	}

	public String getDeleteSql(String tblName, Object[] ids) {
		return SqlUtils.getDeleteSql(tblName, this.fieldPK, ids);
	}

	public String getDeleteSqlOfHead(String tblName, String[] heads) {
		List<String> listPk = new ArrayList<>();
		for (int i=0; i<heads.length; i++) {
			String sIdx = heads[i];
			ColumnPro p = this.mapPropertyColumn.get(sIdx.toLowerCase());
			if (p != null) {
				listPk.add(p.getColumn());
			}
		}
		return SqlUtils.getDeleteSql(tblName, ArrayUtils.listToStringArray(listPk));	
	}

	public String getDeleteSqlOfHead(String tblName, String[] heads, List<String> listJsonPk) {
		List<String> listPk = new ArrayList<>();
		for (int i=0; i<heads.length; i++) {
			String sIdx = heads[i];
			ColumnPro p = this.mapPropertyColumn.get(sIdx.toLowerCase());
			if (p != null && p.getPk()) {
				listPk.add(p.getColumn());
				listJsonPk.add(sIdx);
			}
		}
		return SqlUtils.getDeleteSql(tblName, ArrayUtils.listToStringArray(listPk));	
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public TTableProps() {
	}

	public void finished() {
		setSqlHeadPro_CountSql();
	}

	private void setDefaultValue(GsonRow r) {
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
		if (r != null && r.isExistsData() && this.existDefault) {
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
					Object[] listOld = r.getArrayValueOfIdx(nIdx);
					if (listOld != null && listOld.length > 0) {
						for (int nn = 0; nn < listOld.length; nn++) {
							Object objOld = listOld[nn];
							if (objOld == null) {
								listOld[nn] = objDefault;
							}
						}
						r.setArrayValueOfIdx(nIdx, listOld);
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
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setSqlHeadPro_CountSql() {
		Set set = mapSqlHeadPro.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, SqlHeadCountPro> entry =
				(Map.Entry<String, SqlHeadCountPro>) it.next();
			SqlHeadCountPro p = entry.getValue();
			if (p!=null && StringUtils.isNotEmpty(p.getCountSqlId())) {
				//sqlId和countId,都已经放在一起了,所以这里再需要查找一下
				SqlHeadCountPro hp = mapSqlHeadPro.get(p.getCountSqlId());
				if (hp != null && StringUtils.isNotEmpty(hp.getSql())) {
					p.setCountSql(hp.getSql());					
				}
			}
		}
	}

	public void addColsCommonPro(String sJsonColumn, ColumnPro p) {
		if (StringUtils.isNotEmpty(sJsonColumn)) {
			this.mapColumnDescript.put(p.getColumn(), sJsonColumn);
			this.mapPropertyColumn.put(sJsonColumn.toLowerCase(), p);
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
			this.mapPropertyColumn.put(sJsonColumn.toLowerCase(), p);
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
	
	public GsonRow mapToRow(Map<String, Object> map, boolean bRemovePk) {
		GsonRow row = GsonEbUtils.mapToRow(map);
		if (row != null && !isNonePkInc() && bRemovePk) {
			row.delHead(this.getFieldPK());
		}		
		return row;
	}
	
	public GsonRows mapsToRows(List<Map<String, Object>> list) {
		return GsonEbUtils.mapsToRows(list);
	}
	
	public GsonRows mapsToRows(List<Map<String, Object>> list, boolean bRemovePk) {
		GsonRows rows = GsonEbUtils.mapsToRows(list);
		if (rows != null && !isNonePkInc() && bRemovePk) {
			rows.delHead(this.getFieldPK());
		}
		return rows;
	}
	
	public GsonRow clazzToRow(Object object) throws SQLException {
		return GsonEbUtils.mapToRow(clazzToMap(object));
	}
	
	public GsonRow clazzToRow(Object object, boolean bRemovePk) throws SQLException {
		GsonRow row = GsonEbUtils.mapToRow(clazzToMap(object));
		if (row != null && !isNonePkInc() && bRemovePk) {
			row.delHead(this.getFieldPK());
		}
		return row;
	}
	
	@SuppressWarnings("rawtypes")
	public GsonRows clazzToRows(List list) throws SQLException {
		GsonRows rows = GsonEbUtils.mapsToRows(clazzToMap(list));
		return rows;
	}
	
	@SuppressWarnings("rawtypes")
	public GsonRows clazzToRows(List list, boolean bRemovePk) throws SQLException {
		GsonRows rows = GsonEbUtils.mapsToRows(clazzToMap(list));
		if (rows != null && !isNonePkInc() && bRemovePk) {
			rows.delHead(this.getFieldPK());
		}
		return rows;
	}
	
	public Map<String, Object> clazzToMap(Object object) throws SQLException {
		return GsonEbUtils.clazzToMap(object, this.mapPropertyColumn);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> clazzToMap(List list) throws SQLException {
		if (list != null && list.size()>0) {
			List<Map<String, Object>> listMap = new ArrayList<>();
			for (int i=0; i<list.size(); i++) {
				Object object = list.get(i);
				Map<String, Object> map = GsonEbUtils.clazzToMap(object, this.mapPropertyColumn);
				listMap.add(map);
			}
			return listMap;
		} else {
			return null;
		}
	}

}