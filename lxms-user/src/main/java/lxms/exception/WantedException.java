package lxms.exception;
/**
 * –¸…Õ¡Ó“µŒÒ“Ï≥£
 * @author Administrator
 *
 */
public class WantedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WantedException(String message) {
		super(message);
	}

	public WantedException(String message, Throwable cause) {
		super(message, cause);
	}
}
