package skyglass.composer.sagas.spring.participant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.messaging.consumer.MessageConsumer;
import skyglass.composer.messaging.producer.MessageProducer;
import skyglass.composer.sagas.common.SagaLockManager;
import skyglass.composer.sagas.participant.SagaCommandDispatcherFactory;
import skyglass.composer.sagas.spring.common.EventuateTramSagaCommonConfiguration;

@Configuration
@Import(EventuateTramSagaCommonConfiguration.class)
public class SagaParticipantConfiguration {
	@Bean
	public SagaCommandDispatcherFactory sagaCommandDispatcherFactory(MessageConsumer messageConsumer,
			MessageProducer messageProducer,
			SagaLockManager sagaLockManager) {
		return new SagaCommandDispatcherFactory(messageConsumer, messageProducer, sagaLockManager);
	}
}
