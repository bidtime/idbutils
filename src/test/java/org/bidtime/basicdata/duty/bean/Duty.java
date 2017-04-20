package org.bidtime.basicdata.duty.bean;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class Duty {
	
	private Long id;
	
	private String code;

	private String name;

}