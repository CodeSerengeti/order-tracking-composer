package skyglass.composer.util.test.async;


import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Useful to testing asynchronous code
 * Eventually has methods that periodically executes a Runnable or Callable until it returns successfully or the iteration limit has been reached.
 */
public class Eventually {

  private static final int defaultIntervalInMillis = Optional
          .ofNullable(System.getenv("EVENTUATE_TEST_UTIL_DEFAULT_INTERVAL_IN_MILLIS"))
          .map(Integer::parseInt)
          .orElse(500);

  static int defaultIterations = Optional
          .ofNullable(System.getenv("EVENTUATE_TEST_UTIL_DEFAULT_ITERATIONS"))
          .map(Integer::parseInt)
          .orElse(20);

  public static void eventually(Runnable body) {
    eventually(defaultIterations, defaultIntervalInMillis, TimeUnit.MILLISECONDS, body);
  }

  public static void eventually(String message, Runnable body) {
    eventually(message, defaultIterations, defaultIntervalInMillis, TimeUnit.MILLISECONDS, body);
  }

  public static void eventually(int iterations, int timeout, TimeUnit timeUnit, Runnable body) {
    eventually(null, iterations, timeout, timeUnit, body);
  }

  public static void eventually(String message, int iterations, int timeout, TimeUnit timeUnit, Runnable body) {
    eventuallyReturning(message, iterations, timeout, timeUnit, () -> {
      body.run();
      return null;
    });
  }

  public static <T> T eventuallyReturning(String message, Supplier<T> body) {
    return eventuallyReturning(message, defaultIterations, defaultIntervalInMillis, TimeUnit.MILLISECONDS, body);
  }

  public static <T> T eventuallyReturning(Supplier<T> body) {
    return eventuallyReturning(null, defaultIterations, defaultIntervalInMillis, TimeUnit.MILLISECONDS, body);
  }

  public static <T> T eventuallyReturning(int iterations, int timeout, TimeUnit timeUnit, Supplier<T> body) {
    return eventuallyReturning(null, iterations, timeout, timeUnit, body);
  }

  public static <T> T eventuallyReturning(String message, int iterations, int timeout, TimeUnit timeUnit, Supplier<T> body) {
    Throwable t = null;
    for (int i = 0; i < iterations; i++) {
      try {
        return body.get();
      } catch (Throwable t1) {
        t = t1;
        try {
          timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
    if (message == null)
      throw new EventuallyException(String.format("Failed after %s iterations every %s milliseconds", iterations, timeout), t);
    else
      throw new EventuallyException(String.format(message + " - " + "Failed after %s iterations every %s milliseconds", iterations, timeout), t);
  }
}