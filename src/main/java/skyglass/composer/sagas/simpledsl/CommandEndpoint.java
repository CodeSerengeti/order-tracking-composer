package skyglass.composer.sagas.simpledsl;

import java.util.Set;

import skyglass.composer.commands.common.Command;

public class CommandEndpoint<C extends Command> {

	private String commandChannel;

	private Class<C> commandClass;

	private Set<Class> replyClasses;

	public CommandEndpoint(String commandChannel, Class<C> commandClass, Set<Class> replyClasses) {
		this.commandChannel = commandChannel;
		this.commandClass = commandClass;
		this.replyClasses = replyClasses;
	}

	public String getCommandChannel() {
		return commandChannel;
	}

	public Class<C> getCommandClass() {
		return commandClass;
	}

	public Set<Class> getReplyClasses() {
		return replyClasses;
	}
}
