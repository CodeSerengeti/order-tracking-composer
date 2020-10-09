package skyglass.composer.commands.producer;

import java.util.Map;

import skyglass.composer.commands.common.Command;

public interface CommandProducer {

	/**
	 * Sends a command
	 * 
	 * @param channel
	 * @param command the command to doSend
	 * @param replyTo
	 * @param headers additional headers @return the id of the sent command
	 */
	String send(String channel, Command command, String replyTo,
			Map<String, String> headers);

	/**
	 * Sends a command
	 * 
	 * @param channel
	 * @param resource
	 * @param command the command to doSend
	 * @param replyTo
	 * @param headers additional headers @return the id of the sent command
	 */
	String send(String channel, String resource, Command command, String replyTo, Map<String, String> headers);
}
