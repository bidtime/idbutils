package org.bidtime.utils.exception;

/*
 * 定义非检查型错误
 */
@SuppressWarnings("serial")
public class TipMsgException extends Exception {
	//此为非检查型错误
	public TipMsgException(String message) {
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
