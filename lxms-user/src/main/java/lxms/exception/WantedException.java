package lxms.exception;
/**
 * ������ҵ���쳣
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
