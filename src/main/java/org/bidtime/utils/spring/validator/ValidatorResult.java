package org.bidtime.utils.spring.validator;

/**
 * Created by libing on 2015/12/22. idbutils
 */

public class ValidatorResult {

	// 通过true;
	private Boolean result;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean isPass() {
		return ( result == null ) ? true : this.result;
	}

}
