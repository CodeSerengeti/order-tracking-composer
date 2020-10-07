package skyglass.composer.commands.consumer;

import java.util.List;

import skyglass.composer.messaging.common.Message;

public class CommandExceptionHandler {
	public List<Message> invoke(Throwable cause) {
		throw new UnsupportedOperationException();
	}
}
