package skyglass.composer.sagas.simpledsl;

import skyglass.composer.commands.consumer.CommandWithDestination;
import skyglass.composer.messaging.common.Message;

public interface ParticipantInvocation<Data> {

	boolean isSuccessfulReply(Message message);

	boolean isInvocable(Data data);

	CommandWithDestination makeCommandToSend(Data data);
}
