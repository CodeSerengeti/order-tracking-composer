package skyglass.composer.commands.producer;

import java.util.Map;

import skyglass.composer.commands.common.Command;
import skyglass.composer.commands.common.CommandMessageHeaders;
import skyglass.composer.common.json.mapper.JSonMapper;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.producer.MessageBuilder;
import skyglass.composer.messaging.producer.MessageProducer;

public class CommandProducerImpl implements CommandProducer {

	private MessageProducer messageProducer;

	public CommandProducerImpl(MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

	@Override
	public String send(String channel, Command command, String replyTo, Map<String, String> headers) {
		return send(channel, null, command, replyTo, headers);
	}

	@Override
	public String send(String channel, String resource, Command command, String replyTo, Map<String, String> headers) {
		Message message = makeMessage(channel, resource, command, replyTo, headers);
		messageProducer.send(channel, message);
		return message.getId();
	}

	public static Message makeMessage(String channel, String resource, Command command, String replyTo, Map<String, String> headers) {
		MessageBuilder builder = MessageBuilder.withPayload(JSonMapper.toJson(command))
				.withExtraHeaders("", headers) // TODO should these be prefixed??!
				.withHeader(CommandMessageHeaders.DESTINATION, channel)
				.withHeader(CommandMessageHeaders.COMMAND_TYPE, command.getClass().getName())
				.withHeader(CommandMessageHeaders.REPLY_TO, replyTo);

		if (resource != null)
			builder.withHeader(CommandMessageHeaders.RESOURCE, resource);

		return builder
				.build();
	}
}
