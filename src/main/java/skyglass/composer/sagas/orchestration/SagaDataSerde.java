package skyglass.composer.sagas.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import skyglass.composer.common.json.mapper.JSonMapper;

public class SagaDataSerde {
	private static Logger logger = LoggerFactory.getLogger(SagaDataSerde.class);

	public static <Data> SerializedSagaData serializeSagaData(Data sagaData) {
		return new SerializedSagaData(sagaData.getClass().getName(), JSonMapper.toJson(sagaData));
	}

	public static <Data> Data deserializeSagaData(SerializedSagaData serializedSagaData) {
		Class<?> clasz = null;
		try {
			clasz = Thread.currentThread().getContextClassLoader().loadClass(serializedSagaData.getSagaDataType());
		} catch (ClassNotFoundException e) {
			logger.error("Class not found", e);
			throw new RuntimeException("Class not found", e);
		}
		Object x = JSonMapper.fromJson(serializedSagaData.getSagaDataJSON(), clasz);
		return (Data) x;
	}
}
