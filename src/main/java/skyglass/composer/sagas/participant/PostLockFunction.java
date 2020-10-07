package skyglass.composer.sagas.participant;

import skyglass.composer.commands.consumer.CommandMessage;
import skyglass.composer.commands.consumer.PathVariables;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.sagas.common.LockTarget;

public interface PostLockFunction<C> {

	public LockTarget apply(CommandMessage<C> cm, PathVariables pvs, Message reply);
}
