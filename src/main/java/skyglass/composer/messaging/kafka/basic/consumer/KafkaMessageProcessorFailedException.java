package skyglass.composer.messaging.kafka.basic.consumer;

public class KafkaMessageProcessorFailedException extends RuntimeException {
  public KafkaMessageProcessorFailedException(Throwable t) {
    super(t);
  }
}
