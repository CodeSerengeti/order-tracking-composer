package skyglass.composer.sagas.simpledsl;

import java.util.LinkedList;
import java.util.List;

import skyglass.composer.sagas.orchestration.SagaDefinition;

public class SimpleSagaDefinitionBuilder<Data> {

	private List<SagaStep<Data>> sagaSteps = new LinkedList<>();

	public void addStep(SagaStep<Data> sagaStep) {
		sagaSteps.add(sagaStep);
	}

	public SagaDefinition<Data> build() {
		return new SimpleSagaDefinition<>(sagaSteps);
	}
}
