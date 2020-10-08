package skyglass.composer.messaging.kafka.common;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EventuateKafkaMultiMessage extends KeyValue {

	private List<EventuateKafkaMultiMessageHeader> headers;

	public EventuateKafkaMultiMessage(String key, String value) {
		this(key, value, Collections.emptyList());
	}

	public EventuateKafkaMultiMessage(String key, String value, List<EventuateKafkaMultiMessageHeader> headers) {
		super(key, value);
		this.headers = headers;
	}

	public List<EventuateKafkaMultiMessageHeader> getHeaders() {
		return headers;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public int estimateSize() {
		int headerSize = KeyValue.VALUE_HEADER_SIZE;
		int messagesSize = KeyValue.estimateSize(headers);
		return super.estimateSize() + headerSize + messagesSize;
	}

}
