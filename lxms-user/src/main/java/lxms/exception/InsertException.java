package lxms.exception;
/**
 * ≤Â»Î“Ï≥£
 * @author yanlihui 2016.7.10
 *
 */
public class InsertException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InsertException(String message) {
		super(message);
	}
	public InsertException(String message,Throwable cause) {
		super(message, cause);
	}
}
