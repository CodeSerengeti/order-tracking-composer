package skyglass.composer.sagas.spring.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.common.jdbc.EventuateJdbcStatementExecutor;
import skyglass.composer.common.jdbc.EventuateSchema;
import skyglass.composer.common.spring.jdbc.EventuateCommonJdbcOperationsConfiguration;
import skyglass.composer.sagas.common.SagaLockManager;
import skyglass.composer.sagas.common.SagaLockManagerImpl;

@Configuration
@Import(EventuateCommonJdbcOperationsConfiguration.class)
public class EventuateTramSagaCommonConfiguration {

	@Bean
	public SagaLockManager sagaLockManager(EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
			EventuateSchema eventuateSchema) {
		return new SagaLockManagerImpl(eventuateJdbcStatementExecutor, eventuateSchema);
	}
}
