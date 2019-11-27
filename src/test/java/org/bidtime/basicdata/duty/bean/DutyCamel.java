package org.bidtime.basicdata.duty.bean;

import org.bidtime.dbutils.base.entity.DataEntity;

public class DutyCamel extends DataEntity {
	
	private Long dutyId;
	
	private String dutyCode;

	private String dutyName;

  
  public Long getDutyId() {
    return dutyId;
  }

  
  public void setDutyId(Long dutyId) {
    this.dutyId = dutyId;
  }

  
  public String getDutyCode() {
    return dutyCode;
  }

  
  public void setDutyCode(String dutyCode) {
    this.dutyCode = dutyCode;
  }

  
  public String getDutyName() {
    return dutyName;
  }

  
  public void setDutyName(String dutyName) {
    this.dutyName = dutyName;
  }


}