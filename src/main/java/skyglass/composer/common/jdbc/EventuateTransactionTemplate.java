package skyglass.composer.common.jdbc;

import java.util.function.Supplier;

public interface EventuateTransactionTemplate {
  <T> T executeInTransaction(Supplier<T> callback);
}
