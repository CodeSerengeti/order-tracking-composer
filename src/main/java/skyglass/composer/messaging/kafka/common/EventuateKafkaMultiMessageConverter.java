package skyglass.composer.messaging.kafka.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventuateKafkaMultiMessageConverter {
	public static final String MAGIC_ID = "a8c79db675e14c4cbf1eb77d0d6d0f00"; // generated UUID

	public static final byte[] MAGIC_ID_BYTES = EventuateBinaryMessageEncoding.stringToBytes(MAGIC_ID);

	public byte[] convertMessagesToBytes(List<EventuateKafkaMultiMessageKeyValue> messages) {

		MessageBuilder builder = new MessageBuilder();

		for (EventuateKafkaMultiMessageKeyValue message : messages) {
			builder.addMessage(message);
		}

		return builder.toBinaryArray();
	}

	public EventuateKafkaMultiMessages convertBytesToMessages(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

		if (!isMagicIdPresent(byteBuffer)) {
			throw new RuntimeException("WRONG MAGIC NUMBER!");
		}

		List<EventuateKafkaMultiMessage> messages = new ArrayList<>();

		while (byteBuffer.hasRemaining()) {
			String key = null;
			String value = null;

			int keyLength = byteBuffer.getInt();

			if (keyLength > 0) {
				byte[] keyBytes = new byte[keyLength];
				byteBuffer.get(keyBytes);
				key = EventuateBinaryMessageEncoding.bytesToString(keyBytes);
			}

			int valueLength = byteBuffer.getInt();
			if (valueLength > 0) {
				byte[] valueBytes = new byte[valueLength];
				byteBuffer.get(valueBytes);
				value = EventuateBinaryMessageEncoding.bytesToString(valueBytes);
			}

			messages.add(new EventuateKafkaMultiMessage(key, value));
		}

		return new EventuateKafkaMultiMessages(messages);
	}

	public List<String> convertBytesToValues(byte[] bytes) {
		if (isMultiMessage(bytes)) {
			return convertBytesToMessages(bytes)
					.getMessages()
					.stream()
					.map(EventuateKafkaMultiMessage::getValue)
					.collect(Collectors.toList());
		} else {
			return Collections.singletonList(EventuateBinaryMessageEncoding.bytesToString(bytes));
		}
	}

	public boolean isMultiMessage(byte[] message) {
		if (message.length < MAGIC_ID_BYTES.length)
			return false;

		for (int i = 0; i < MAGIC_ID_BYTES.length; i++)
			if (message[i] != MAGIC_ID_BYTES[i])
				return false;

		return true;
	}

	private boolean isMagicIdPresent(ByteBuffer byteBuffer) {
		if (byteBuffer.remaining() < MAGIC_ID_BYTES.length)
			return false;

		for (int i = 0; i < MAGIC_ID_BYTES.length; i++) {
			if (MAGIC_ID_BYTES[i] != byteBuffer.get())
				return false;
		}

		return true;
	}

	public static class MessageBuilder {
		private Optional<Integer> maxSize;

		private int size;

		private ByteArrayOutputStream binaryStream = new ByteArrayOutputStream();

		public MessageBuilder(int maxSize) {
			this(Optional.of(maxSize));
		}

		public MessageBuilder() {
			this(Optional.empty());
		}

		public MessageBuilder(Optional<Integer> maxSize) {
			this.maxSize = maxSize;

			try {
				binaryStream.write(MAGIC_ID_BYTES);
				size += MAGIC_ID_BYTES.length;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public int getSize() {
			return size;
		}

		public boolean addMessage(EventuateKafkaMultiMessageKeyValue message) {
			try {
				byte[] keyBytes = Optional.ofNullable(message.getKey()).map(EventuateBinaryMessageEncoding::stringToBytes).orElse(new byte[0]);
				byte[] valueBytes = Optional.ofNullable(message.getValue()).map(EventuateBinaryMessageEncoding::stringToBytes).orElse(new byte[0]);

				int additionalSize = 2 * 4 + keyBytes.length + valueBytes.length;

				if (maxSize.map(ms -> size + additionalSize > ms).orElse(false)) {
					return false;
				}

				binaryStream.write(intToBytes(keyBytes.length));
				binaryStream.write(keyBytes);
				binaryStream.write(intToBytes(valueBytes.length));
				binaryStream.write(valueBytes);

				size += additionalSize;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			return true;
		}

		private static byte[] intToBytes(int value) {
			return ByteBuffer.allocate(4).putInt(value).array();
		}

		public byte[] toBinaryArray() {
			return binaryStream.toByteArray();
		}
	}
}
