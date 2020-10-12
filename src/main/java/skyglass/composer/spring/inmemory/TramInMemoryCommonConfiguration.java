package skyglass.composer.spring.inmemory;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.common.id.IdGenerator;
import skyglass.composer.common.inmemorydatabase.EventuateDatabaseScriptSupplier;
import skyglass.composer.common.spring.id.IdGeneratorConfiguration;
import skyglass.composer.common.spring.inmemorydatabase.EventuateCommonInMemoryDatabaseConfiguration;
import skyglass.composer.inmemory.InMemoryMessageConsumer;
import skyglass.composer.inmemory.InMemoryMessageProducer;
import skyglass.composer.messaging.producer.common.TramMessagingCommonProducerConfiguration;
import skyglass.composer.spring.consumer.common.TramConsumerCommonConfiguration;

@Configuration
@Import({ TramConsumerCommonConfiguration.class,
		TramMessagingCommonProducerConfiguration.class,
		EventuateCommonInMemoryDatabaseConfiguration.class,
		IdGeneratorConfiguration.class })
public class TramInMemoryCommonConfiguration {

	@Bean
	public InMemoryMessageConsumer inMemoryMessageConsumer() {
		return new InMemoryMessageConsumer();
	}

	@Bean
	public InMemoryMessageProducer inMemoryMessageProducer(InMemoryMessageConsumer messageConsumer, IdGenerator idGenerator) {
		return new InMemoryMessageProducer(messageConsumer, idGenerator);
	}

	@Bean
	public EventuateDatabaseScriptSupplier eventuateCommonInMemoryScriptSupplierForTram() {
		return () -> Collections.singletonList("eventuate-tram-embedded-schema.sql");
	}
}
