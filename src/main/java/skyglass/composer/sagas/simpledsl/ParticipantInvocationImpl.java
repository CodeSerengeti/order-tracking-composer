package skyglass.composer.sagas.simpledsl;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import skyglass.composer.commands.common.Command;
import skyglass.composer.commands.common.CommandReplyOutcome;
import skyglass.composer.commands.common.ReplyMessageHeaders;
import skyglass.composer.commands.consumer.CommandWithDestination;
import skyglass.composer.messaging.common.Message;

public class ParticipantInvocationImpl<Data, C extends Command> extends AbstractParticipantInvocation<Data> {
	private Function<Data, CommandWithDestination> commandBuilder;

	public ParticipantInvocationImpl(Optional<Predicate<Data>> invocablePredicate, Function<Data, CommandWithDestination> commandBuilder) {
		super(invocablePredicate);
		this.commandBuilder = commandBuilder;
	}

	@Override
	public boolean isSuccessfulReply(Message message) {
		return CommandReplyOutcome.SUCCESS.name().equals(message.getRequiredHeader(ReplyMessageHeaders.REPLY_OUTCOME));
	}

	@Override
	public CommandWithDestination makeCommandToSend(Data data) {
		return commandBuilder.apply(data);
	}
}
