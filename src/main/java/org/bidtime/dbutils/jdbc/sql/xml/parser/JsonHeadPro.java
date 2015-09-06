package org.bidtime.dbutils.jdbc.sql.xml.parser;


/*
 * JsonHead属性
 * 	1. 标识解决密字段
 * 	2. 标识json头
 */
public class JsonHeadPro {

	private String id;
	private String decryFlds;
	private String[] arDecryFlds;
	private String type; // 保留
	private String headFlds;
	private String[] arHeadFlds;

	public String getHeadFlds() {
		return headFlds;
	}

	public void setHeadFlds(String headFlds) {
		this.headFlds = headFlds;
		setArHeadFlds(headFlds.split(","));
	}

	public String[] getArHeadFlds() {
		return arHeadFlds;
	}

	public void setArHeadFlds(String[] arHeadFlds) {
		this.arHeadFlds = arHeadFlds;
	}

	public JsonHeadPro() {

	}

	public void initial() {
		id = "";
		decryFlds = "";
		arDecryFlds = null;
		type = "head"; // 0
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDecryFlds() {
		return decryFlds;
	}

	public void setDecryFlds(String decryFlds) {
		this.decryFlds = decryFlds;
		setArDecryFlds(decryFlds.split(","));
	}

	public String[] getArDecryFlds() {
		return arDecryFlds;
	}

	public void setArDecryFlds(String[] arDecryFlds) {
		this.arDecryFlds = arDecryFlds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
