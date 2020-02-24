package lxms.exception;

public class RequestLimitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RequestLimitException() {
		super("HTTP请求 超出设定限制");
	}
	public RequestLimitException(String message) {
		super(message);
	}
}
