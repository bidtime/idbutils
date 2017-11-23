package org.bidtime.basicdata.duty.bean;

import org.bidtime.dbutils.base.entity.DataEntity;

public class Duty extends DataEntity {
	
	private Long id;
	
	private String code;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

}