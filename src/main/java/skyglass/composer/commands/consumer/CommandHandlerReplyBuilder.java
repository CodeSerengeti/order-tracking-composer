package skyglass.composer.commands.consumer;

import skyglass.composer.commands.common.CommandReplyOutcome;
import skyglass.composer.commands.common.Failure;
import skyglass.composer.commands.common.ReplyMessageHeaders;
import skyglass.composer.commands.common.Success;
import skyglass.composer.common.json.mapper.JSonMapper;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.producer.MessageBuilder;

public class CommandHandlerReplyBuilder {

	private static <T> Message with(T reply, CommandReplyOutcome outcome) {
		MessageBuilder messageBuilder = MessageBuilder
				.withPayload(JSonMapper.toJson(reply))
				.withHeader(ReplyMessageHeaders.REPLY_OUTCOME, outcome.name())
				.withHeader(ReplyMessageHeaders.REPLY_TYPE, reply.getClass().getName());
		return messageBuilder.build();
	}

	public static Message withSuccess(Object reply) {
		return with(reply, CommandReplyOutcome.SUCCESS);
	}

	public static Message withSuccess() {
		return withSuccess(new Success());
	}

	public static Message withFailure() {
		return withFailure(new Failure());
	}

	public static Message withFailure(Object reply) {
		return with(reply, CommandReplyOutcome.FAILURE);
	}

}
