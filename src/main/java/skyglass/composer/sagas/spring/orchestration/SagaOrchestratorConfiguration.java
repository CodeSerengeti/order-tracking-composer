package skyglass.composer.sagas.spring.orchestration;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.commands.producer.CommandProducer;
import skyglass.composer.common.id.IdGenerator;
import skyglass.composer.common.jdbc.EventuateJdbcStatementExecutor;
import skyglass.composer.common.jdbc.EventuateSchema;
import skyglass.composer.messaging.consumer.MessageConsumer;
import skyglass.composer.sagas.common.SagaLockManager;
import skyglass.composer.sagas.orchestration.Saga;
import skyglass.composer.sagas.orchestration.SagaCommandProducer;
import skyglass.composer.sagas.orchestration.SagaInstanceFactory;
import skyglass.composer.sagas.orchestration.SagaInstanceRepository;
import skyglass.composer.sagas.orchestration.SagaInstanceRepositoryJdbc;
import skyglass.composer.sagas.orchestration.SagaManagerFactory;
import skyglass.composer.sagas.spring.common.EventuateTramSagaCommonConfiguration;
import skyglass.composer.spring.commands.producer.TramCommandProducerConfiguration;

@Configuration
@Import({ TramCommandProducerConfiguration.class, EventuateTramSagaCommonConfiguration.class })
public class SagaOrchestratorConfiguration {

	@Bean
	public SagaInstanceRepository sagaInstanceRepository(EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
			IdGenerator idGenerator,
			EventuateSchema eventuateSchema) {
		return new SagaInstanceRepositoryJdbc(eventuateJdbcStatementExecutor, idGenerator, eventuateSchema);
	}

	@Bean
	public SagaCommandProducer sagaCommandProducer(CommandProducer commandProducer) {
		return new SagaCommandProducer(commandProducer);
	}

	@Bean
	public SagaInstanceFactory sagaInstanceFactory(SagaInstanceRepository sagaInstanceRepository, CommandProducer commandProducer, MessageConsumer messageConsumer,
			SagaLockManager sagaLockManager, SagaCommandProducer sagaCommandProducer, Collection<Saga<?>> sagas) {
		SagaManagerFactory smf = new SagaManagerFactory(sagaInstanceRepository, commandProducer, messageConsumer,
				sagaLockManager, sagaCommandProducer);
		return new SagaInstanceFactory(smf, sagas);
	}
}
