package skyglass.composer.consumer.common;

public interface DuplicateMessageDetector {
  boolean isDuplicate(String consumerId, String messageId);
  void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback);
}
