package skyglass.composer.util.test.async;

public class EventuallyException extends RuntimeException {

	private static final long serialVersionUID = 8407953978726711904L;

	public EventuallyException(String message, Throwable t) {
		super(message, t);
	}
}
