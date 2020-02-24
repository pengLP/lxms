package lxms.exception;

/**
 * 进行user业务操作的一些异常
 * 
 * @author yanlihui
 *
 */
public class UserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}
}
