package org.bidtime.utils.exception;

/*
 * 定义非检查型错误
 */
public class CheckMessageException extends RuntimeException {
	//此为非检查型错误
	private static final long serialVersionUID = 0L;
	public CheckMessageException(String message) {
		super(message);
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
