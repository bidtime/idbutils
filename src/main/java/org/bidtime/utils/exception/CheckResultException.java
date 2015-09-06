package org.bidtime.utils.exception;

/*
 * 定义非检查型错误
 */
public class CheckResultException extends RuntimeException {
	//此为非检查型错误
	private static final long serialVersionUID = 0L;
	private Integer result = null;
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public CheckResultException(String message) {
		super(message);
	}
	public CheckResultException(String message, Integer result) {
		super(message);
		this.result = result;
	}
	/*
	检查型异常checked
	public class AhDataBaseException extends Exception {
    	public AhDataBaseException(String message, Throwable cause) {
        	super(message,cause);
        }
    }
	*/
}
