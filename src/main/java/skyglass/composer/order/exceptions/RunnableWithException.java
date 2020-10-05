package skyglass.composer.order.exceptions;

@FunctionalInterface
public interface RunnableWithException<E extends Exception> {

	void run() throws E;

}
