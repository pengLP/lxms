package lxms.exception;
/**
 * ����ʱ�쳣
 * @author Yanlihui 2016 7 10 
 *
 */
public class UpdateException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UpdateException(String message) {
		super(message);
	}
	public UpdateException(String message,Throwable cause) {
		super(message, cause);
	}
}
