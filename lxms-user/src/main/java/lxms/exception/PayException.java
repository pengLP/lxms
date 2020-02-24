package lxms.exception;

public class PayException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PayException(String message) {
		super(message);
	}
	public PayException(String message,Throwable cause) {
		super(message, cause);
	}
}
