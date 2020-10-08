package skyglass.composer.messaging.producer.jdbc;

import skyglass.composer.common.id.IdGenerator;
import skyglass.composer.common.jdbc.EventuateCommonJdbcOperations;
import skyglass.composer.common.jdbc.EventuateSchema;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.producer.common.MessageProducerImplementation;

public class MessageProducerJdbcImpl implements MessageProducerImplementation {

	private EventuateCommonJdbcOperations eventuateCommonJdbcOperations;

	private IdGenerator idGenerator;

	private EventuateSchema eventuateSchema;

	private String currentTimeInMillisecondsSql;

	public MessageProducerJdbcImpl(EventuateCommonJdbcOperations eventuateCommonJdbcOperations,
			IdGenerator idGenerator,
			EventuateSchema eventuateSchema,
			String currentTimeInMillisecondsSql) {

		this.eventuateCommonJdbcOperations = eventuateCommonJdbcOperations;
		this.idGenerator = idGenerator;
		this.eventuateSchema = eventuateSchema;
		this.currentTimeInMillisecondsSql = currentTimeInMillisecondsSql;
	}

	@Override
	public String generateMessageId() {
		return idGenerator.genId().asString();
	}

	@Override
	public void send(Message message) {
		eventuateCommonJdbcOperations.insertIntoMessageTable(message.getId(),
				message.getPayload(),
				message.getRequiredHeader(Message.DESTINATION),
				currentTimeInMillisecondsSql,
				message.getHeaders(),
				eventuateSchema);
	}
}
