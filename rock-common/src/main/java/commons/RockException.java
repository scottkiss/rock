package commons;

/**
 * describe: Rock Exception
 *
 * @author sirk
 * @date 2020/11/25
 */
public class RockException extends RuntimeException {

	private static final long serialVersionUID = 6194293658268960882L;

	public RockException() {
		super();
	}

	public RockException(String message) {
		super(message);
	}

	public RockException(Throwable cause) {
		super(cause);
	}

	public RockException(String message, Throwable cause) {
		super(message, cause);
	}

}
