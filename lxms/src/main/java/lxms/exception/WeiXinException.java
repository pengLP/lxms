package lxms.exception;

public class WeiXinException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WeiXinException(String message) {
		super(message);
	}

	public WeiXinException(String message, Throwable cause) {
		super(message, cause);
	}
}
