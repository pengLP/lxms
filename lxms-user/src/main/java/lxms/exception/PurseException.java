package lxms.exception;

public class PurseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PurseException(String message) {
		super(message);
	}

	public PurseException(String message, Throwable cause) {
		super(message, cause);
	}
}
