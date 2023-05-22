package org.bidtime.dbutils.jdbc.sql.xml.parser;

import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.bidtime.dbutils.jdbc.sql.SqlUtils;
import org.bidtime.dbutils.utils.basic.DateTimeComm;

public class ColumnPro {
	private String name;
	private String column;
	private Integer length;
	private String generator;
	private Boolean notNull;
	public Boolean getNotNull() {
		return notNull;
	}

	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}

	private Boolean pk;
	private Object defaultValue;
	private Boolean identity;
	private int type;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Boolean getIdentity() {
		return identity;
	}

	public void setIdentity(Boolean identity) {
		this.identity = identity;
	}

	public Object getDefaultValue() {
		if (defaultValue != null && SqlUtils.isDateTime(this.type)) {
			if ((String.valueOf(defaultValue)).equalsIgnoreCase("now()")) {
				return new Date();
			} else {
				return defaultValue;
			}
		} else {
			return defaultValue;
		}
	}

	public void setDefaultValue(Object defaultValue) {
		if (SqlUtils.isDateTime(this.type)) {			
			if ((String.valueOf(defaultValue)).equalsIgnoreCase("now()")) {
				this.defaultValue = defaultValue;
			} else {
				this.defaultValue = DateTimeComm.yyyyMMddHHmmssToDate(String.valueOf(defaultValue));
			}
		} else {
			this.defaultValue = SqlUtils.getDefaultOfType(this.type, defaultValue);
		}
	}

	public Boolean getPk() {
		return pk;
	}

	public void setPk(Boolean pk) {
		this.pk = pk;
	}

//	public ColumnPro() {
//		initial();
//	}

	public ColumnPro(Boolean pk) {
		initial();
		this.pk=pk;
	}
	
	public void initial() {
		name="";
		column="";
		length=0;
		generator=null;
		notNull=true;
		pk=false;
		defaultValue=null;
		identity=true;
		type=Types.VARCHAR;
	}
	
	public void setType(String type) {
		this.type = SqlUtils.getObjectType(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
		this.identity = StringUtils.equals(this.generator, "identity");
	}

}
