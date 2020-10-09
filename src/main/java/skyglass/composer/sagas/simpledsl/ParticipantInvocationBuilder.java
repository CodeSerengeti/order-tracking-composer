package skyglass.composer.sagas.simpledsl;

import static java.util.Collections.singletonMap;

import java.util.Map;

import skyglass.composer.commands.common.Command;

public class ParticipantInvocationBuilder {
	private Map<String, String> params;

	public ParticipantInvocationBuilder(String key, String value) {
		this.params = singletonMap(key, value);
	}

	public <C extends Command> ParticipantParamsAndCommand<C> withCommand(C command) {
		return new ParticipantParamsAndCommand<>(params, command);
	}
}
