package skyglass.composer.messaging.kafka.common;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EventuateKafkaMultiMessages {

	private List<EventuateKafkaMultiMessage> messages;

	public EventuateKafkaMultiMessages(List<EventuateKafkaMultiMessage> messages) {
		this.messages = messages;
	}

	public List<EventuateKafkaMultiMessage> getMessages() {
		return messages;
	}

	public int estimateSize() {
		return KeyValue.estimateSize(messages);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(messages);
	}
}
