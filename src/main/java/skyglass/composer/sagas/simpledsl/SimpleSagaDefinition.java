package skyglass.composer.sagas.simpledsl;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import skyglass.composer.commands.common.ReplyMessageHeaders;
import skyglass.composer.common.json.mapper.JSonMapper;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.sagas.orchestration.SagaActions;
import skyglass.composer.sagas.orchestration.SagaDefinition;

public class SimpleSagaDefinition<Data> implements SagaDefinition<Data> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<SagaStep<Data>> sagaSteps;

	public SimpleSagaDefinition(List<SagaStep<Data>> sagaSteps) {
		this.sagaSteps = sagaSteps;
	}

	@Override
	public SagaActions<Data> start(Data sagaData) {
		SagaExecutionState currentState = new SagaExecutionState(-1, false);

		StepToExecute<Data> stepToExecute = nextStepToExecute(currentState, sagaData);

		if (stepToExecute.isEmpty()) {
			return makeEndStateSagaActions(currentState);
		} else
			return stepToExecute.executeStep(sagaData, currentState);
	}

	@Override
	public SagaActions<Data> handleReply(String currentState, Data sagaData, Message message) {

		SagaExecutionState state = SagaExecutionStateJsonSerde.decodeState(currentState);
		SagaStep<Data> currentStep = sagaSteps.get(state.getCurrentlyExecuting());
		boolean compensating = state.isCompensating();

		currentStep.getReplyHandler(message, compensating).ifPresent(handler -> {
			invokeReplyHandler(message, sagaData, handler);
		});

		if (currentStep.isSuccessfulReply(compensating, message)) {
			return executeNextStep(sagaData, state);
		} else if (compensating) {
			throw new UnsupportedOperationException("Failure when compensating");
		} else {
			return executeNextStep(sagaData, state.startCompensating());
		}
	}

	private StepToExecute<Data> nextStepToExecute(SagaExecutionState state, Data data) {
		int skipped = 0;
		boolean compensating = state.isCompensating();
		int direction = compensating ? -1 : +1;
		for (int i = state.getCurrentlyExecuting() + direction; i >= 0 && i < sagaSteps.size(); i = i + direction) {
			SagaStep<Data> step = sagaSteps.get(i);
			if ((compensating ? step.hasCompensation(data) : step.hasAction(data))) {
				return new StepToExecute<>(Optional.of(step), skipped, compensating);
			} else
				skipped++;
		}
		return new StepToExecute<>(Optional.empty(), skipped, compensating);
	}

	private SagaActions<Data> executeNextStep(Data data, SagaExecutionState state) {
		StepToExecute<Data> stepToExecute = nextStepToExecute(state, data);
		if (stepToExecute.isEmpty()) {
			return makeEndStateSagaActions(state);
		} else {
			// do something
			return stepToExecute.executeStep(data, state);
		}
	}

	private void invokeReplyHandler(Message message, Data data, BiConsumer<Data, Object> handler) {
		Class m;
		try {
			String className = message.getRequiredHeader(ReplyMessageHeaders.REPLY_TYPE);
			m = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
		} catch (ClassNotFoundException e) {
			logger.error("Class not found", e);
			throw new RuntimeException("Class not found", e);
		}
		Object reply = JSonMapper.fromJson(message.getPayload(), m);
		handler.accept(data, reply);
	}

	private SagaActions<Data> makeEndStateSagaActions(SagaExecutionState state) {
		return SagaActions.<Data> builder()
				.withUpdatedState(SagaExecutionStateJsonSerde.encodeState(SagaExecutionState.makeEndState()))
				.withIsEndState(true)
				.withIsCompensating(state.isCompensating())
				.build();
	}

}
