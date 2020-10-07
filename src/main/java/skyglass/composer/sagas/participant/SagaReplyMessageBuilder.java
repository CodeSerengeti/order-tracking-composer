package skyglass.composer.sagas.participant;

import java.util.Optional;

import skyglass.composer.commands.common.CommandReplyOutcome;
import skyglass.composer.commands.common.ReplyMessageHeaders;
import skyglass.composer.commands.common.Success;
import skyglass.composer.common.json.mapper.JSonMapper;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.producer.MessageBuilder;
import skyglass.composer.sagas.common.LockTarget;

public class SagaReplyMessageBuilder extends MessageBuilder {

	private Optional<LockTarget> lockTarget = Optional.empty();

	public SagaReplyMessageBuilder(LockTarget lockTarget) {
		this.lockTarget = Optional.of(lockTarget);
	}

	public static SagaReplyMessageBuilder withLock(Class type, Object id) {
		return new SagaReplyMessageBuilder(new LockTarget(type, id));
	}

	private <T> Message with(T reply, CommandReplyOutcome outcome) {
		this.body = JSonMapper.toJson(reply);
		withHeader(ReplyMessageHeaders.REPLY_OUTCOME, outcome.name());
		withHeader(ReplyMessageHeaders.REPLY_TYPE, reply.getClass().getName());
		return new SagaReplyMessage(body, headers, lockTarget);
	}

	public Message withSuccess(Object reply) {
		return with(reply, CommandReplyOutcome.SUCCESS);
	}

	public Message withSuccess() {
		return withSuccess(new Success());
	}

}
