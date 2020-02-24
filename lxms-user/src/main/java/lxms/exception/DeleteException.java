package lxms.exception;
/**
 * É¾³ýÒì³£
 * @author yanlihui 2016.7.10
 *
 */
public class DeleteException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DeleteException(String message) {
		super(message);
	}
	public DeleteException(String message,Throwable cause) {
		super(message,cause);
	}
}
