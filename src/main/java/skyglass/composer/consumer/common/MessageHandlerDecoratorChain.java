package skyglass.composer.consumer.common;

public interface MessageHandlerDecoratorChain {
  void invokeNext(SubscriberIdAndMessage subscriberIdAndMessage);
}
