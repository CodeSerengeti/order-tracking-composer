package skyglass.composer.sagas.simpledsl;

import skyglass.composer.common.json.mapper.JSonMapper;

public class SagaExecutionStateJsonSerde {
	static SagaExecutionState decodeState(String currentState) {
		return JSonMapper.fromJson(currentState, SagaExecutionState.class);
	}

	public static String encodeState(SagaExecutionState state) {
		return JSonMapper.toJson(state);
	}
}
