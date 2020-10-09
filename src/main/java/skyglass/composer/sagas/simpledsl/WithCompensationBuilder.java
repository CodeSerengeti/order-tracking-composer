package skyglass.composer.sagas.simpledsl;

import java.util.function.Function;
import java.util.function.Predicate;

import skyglass.composer.commands.common.Command;
import skyglass.composer.commands.consumer.CommandWithDestination;

public interface WithCompensationBuilder<Data> {

	InvokeParticipantStepBuilder<Data> withCompensation(Function<Data, CommandWithDestination> compensation);

	InvokeParticipantStepBuilder<Data> withCompensation(Predicate<Data> compensationPredicate, Function<Data, CommandWithDestination> compensation);

	<C extends Command> InvokeParticipantStepBuilder<Data> withCompensation(CommandEndpoint<C> commandEndpoint, Function<Data, C> commandProvider);

	<C extends Command> InvokeParticipantStepBuilder<Data> withCompensation(Predicate<Data> compensationPredicate, CommandEndpoint<C> commandEndpoint, Function<Data, C> commandProvider);
}
