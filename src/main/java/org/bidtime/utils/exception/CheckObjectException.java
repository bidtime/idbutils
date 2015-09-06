package org.bidtime.utils.exception;

/*
 * 定义非检查型错误
 */
public class CheckObjectException extends RuntimeException {
	//此为非检查型错误
	private static final long serialVersionUID = 0L;
	private Object result = null;

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public CheckObjectException(String message) {
		super(message);
	}
	public CheckObjectException(String message, Object result) {
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
