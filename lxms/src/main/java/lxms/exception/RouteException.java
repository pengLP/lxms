package lxms.exception;
/**
 * 行程业务异常
 * @author huisir  2016 7 10
 *
 */
public class RouteException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RouteException(String message) {
		super(message);
	}

	public RouteException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
