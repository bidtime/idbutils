/**
 * 
 */
package org.bidtime.dbutils.gson.dataset;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class ParserDataSet {

	List<GsonRow> add = null;

	List<GsonRow> edit = null;

	List<GsonRow> del = null;

	public ParserDataSet() {
	}

//	protected void initial() {
//		add = new ArrayList<GsonHeadOneRow>();
//		edit = new ArrayList<GsonHeadOneRow>();
//		del = new ArrayList<GsonHeadOneRow>();
//	}

	private void delHead(List<GsonRow> listRows, String sHead) {
		if (listRows != null && !listRows.isEmpty()) {
			for (int i = 0; i < listRows.size(); i++) {
				GsonRow r = listRows.get(i);
				r.delHead(sHead);
			}
		}
	}

	private void delHead(List<GsonRow> listRows, String[] arHead) {
		if (listRows != null && !listRows.isEmpty()) {
			for (int i = 0; i < listRows.size(); i++) {
				GsonRow r = listRows.get(i);
				r.delHead(arHead);
			}
		}
	}

	public void delHead(String sHead) {
		delHead(add, sHead);
		delHead(del, sHead);
		delHead(edit, sHead);
	}

	public void delHead(String[] arHead) {
		delHead(add, arHead);
		delHead(del, arHead);
		delHead(edit, arHead);
	}

//	private void cloneParser(ParserDataSetJson p) {
//		this.add = p.add;
//		this.del = p.del;
//		this.edit = p.edit;
//	}

	public List<GsonRow> getListInsertRows() {
		return add;
	}

	public void setListInsertRows(List<GsonRow> add) {
		this.add = add;
	}

	public void addInsertRow(GsonRow row) {
		if (this.add == null) {
			this.add = new ArrayList<GsonRow>();
		}
		this.add.add(row);
	}

	public void addEditRow(GsonRow row) {
		if (this.edit == null) {
			this.edit = new ArrayList<GsonRow>();
		}
		this.edit.add(row);
	}

	public void addDelRow(GsonRow row) {
		if (this.del == null) {
			this.del = new ArrayList<GsonRow>();
		}
		this.del.add(row);
	}

	public List<GsonRow> getListUpdateRows() {
		return edit;
	}

	public void setListUpdateRows(List<GsonRow> edit) {
		this.edit = edit;
	}

	public List<GsonRow> getListDelRows() {
		return del;
	}

	public void setListDelRows(List<GsonRow> del) {
		this.del = del;
	}
	
//	public ParserDataSet(String json) {
//		fromString(json);
//	}

//	protected void fromString(String json) {
//		JSONObject jsonobj = new JSONObject(json);
//		fromJson(jsonobj);
//	}
//		
//	public ParserDataSet(JSONObject jsonObject) {
//		fromJson(jsonObject);
//	}
//	
//	private List<GsonRow> jsonObjectKeyToList(JSONObject jsonObject, String prop) {
//		List<GsonRow> list = null;
//		if (jsonObject.has(prop)) {
//			JSONArray jsonAttr = jsonObject.getJSONArray(prop);
//			if (jsonAttr != null && jsonAttr.length() > 0) {
//				list = new ArrayList<GsonRow>();
//				for (int i = 0; i < jsonAttr.length(); i++) {
//					Object object = jsonAttr.get(i);
//					list.add(new GsonRow((JSONObject)object));
//				}
//			}
//		}
//		return list;
//	}
	
//	public void fromJson(JSONObject jsonObject) {
//		add = jsonObjectKeyToList(jsonObject, "add");
//		edit = jsonObjectKeyToList(jsonObject, "edit");
//		del = jsonObjectKeyToList(jsonObject, "del");
//	}
	
//	public static ParserDataSet parserString(String json) {
//		return parserString(json, false);
//	}

//	public static ParserDataSet parserString(String json, boolean bNew) {
//		if (StringUtils.isNotEmpty(json)) {
//			JSONObject jsonobj = new JSONObject(json);
//			if (jsonobj.length() > 0 || bNew) {
//				ParserDataSet row = new ParserDataSet();
//				row.fromJson(jsonobj);
//				return row;
//			} else {
//				return null;
//			}
//		} else {
//			return null;
//		}
//	}

	public boolean isExistsInsertRows() {
		return ( add != null && !add.isEmpty() ) ? true : false;
	}

	public boolean isExistsDelRows() {
		return ( del != null && !del.isEmpty() ) ? true : false;
	}

	public boolean isExistsUpdateRows() {
		return ( edit != null && !edit.isEmpty() ) ? true : false;
	}

	public boolean isExistsRows() {
		if (isExistsInsertRows() || isExistsDelRows() || isExistsUpdateRows()) {
			return true;
		} else {
			return false;
		}
	}

	public String objectToJsonStr() {
		return toString();	//GsonComm.toJson(this, ParserDataSetJson.class, true);
	}

//	public static ParserDataSet jsonStrToObject(String json) {
//		return parserString(json);	//GsonComm.fromJson(s, ParserDataSetJson.class);
//	}

//	protected JSONObject toJson() {
//		JSONObject jsonObject = new JSONObject();
//		if (this.isExistsInsertRows()) {
//			jsonObject.put("add", JSONHelper.listToJson(this.add, null));
//		}
//		if (this.isExistsUpdateRows()) {
//			jsonObject.put("edit", JSONHelper.listToJson(this.edit, null));
//		}
//		if (this.isExistsDelRows()) {
//			jsonObject.put("del", JSONHelper.listToJson(this.del, null));
//		}
//		return jsonObject;
//	}

//	@Override
//	public String toString() {
//		JSONObject jsonObject = toJson();
//		return jsonObject.toString();
//	}
	
//	private String testToJson() {
//		String[] head = new String[]{"id", "id", "id"};
//		Object[] data = new Object[]{1, 2, 3};
//		GsonHeadOneRow row = new GsonHeadOneRow(head, data);
//		this.addInsertRow(row);
//		return this.toString();
//	}
	
//	private String testToJson() {
//		return "{\"add\":[{\"head\":[\"idx\",\"partsName\",\"needNum\",\"ptFactTpId\",\"ptFactTpName\",\"memo\"],\"data\":[\"1\",\"pp\",\"1\",\"0\",\"原厂\",\"11\"]},{\"head\":[\"idx\",\"partsName\",\"needNum\",\"ptFactTpId\",\"ptFactTpName\",\"memo\"],\"data\":[\"2\",\"kk\",\"2\",\"1\",\"国内品牌\",\"22\"]}]}";
//	}
//	
//	public static void main(String[] args) {
//		String sJson = new ParserDataSetJson().testToJson();
//		System.out.println("raw");
//		System.out.println(sJson);
//		ParserDataSetJson p = new ParserDataSetJson(sJson);
//		String sReturn = p.toString();
//		System.out.println("return");
//		System.out.println(sReturn);
//	}

}
