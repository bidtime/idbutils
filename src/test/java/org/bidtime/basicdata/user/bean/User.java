package org.bidtime.basicdata.user.bean;

import lombok.Data;

@Data
public class User {
	
	private Long userId;
	
	private String userCode;

	private String userName;
	
	private String bmi_scope;

}