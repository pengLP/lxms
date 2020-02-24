package lxms.exception;

public class UserAddressException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAddressException(String message) {
		super(message);
	}

	public UserAddressException(String message, Throwable cause) {
		super(message, cause);
	}
}
