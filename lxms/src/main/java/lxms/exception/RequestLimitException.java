package lxms.exception;

public class RequestLimitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RequestLimitException() {
		super("HTTP���� �����趨����");
	}
	public RequestLimitException(String message) {
		super(message);
	}
}
