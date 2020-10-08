package skyglass.composer.consumer.common;

import java.util.function.BiConsumer;

public interface MessageHandlerDecorator extends BiConsumer<SubscriberIdAndMessage, MessageHandlerDecoratorChain> {
  int getOrder();
}
