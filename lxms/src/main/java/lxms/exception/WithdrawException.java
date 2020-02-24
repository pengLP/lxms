package lxms.exception;

public class WithdrawException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WithdrawException(String message) {
		super(message);
	}

	public WithdrawException(String message, Throwable cause) {
		super(message, cause);
	}
}
