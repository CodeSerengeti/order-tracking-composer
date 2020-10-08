package skyglass.composer.messaging.producer.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.common.id.IdGenerator;
import skyglass.composer.common.jdbc.EventuateCommonJdbcOperations;
import skyglass.composer.common.jdbc.EventuateSchema;
import skyglass.composer.common.jdbc.sqldialect.SqlDialectSelector;
import skyglass.composer.common.spring.id.IdGeneratorConfiguration;
import skyglass.composer.common.spring.jdbc.EventuateCommonJdbcOperationsConfiguration;
import skyglass.composer.common.spring.jdbc.sqldialect.SqlDialectConfiguration;
import skyglass.composer.messaging.producer.common.MessageProducerImplementation;
import skyglass.composer.messaging.producer.common.TramMessagingCommonProducerConfiguration;

@Configuration
@Import({ SqlDialectConfiguration.class,
		TramMessagingCommonProducerConfiguration.class,
		EventuateCommonJdbcOperationsConfiguration.class,
		IdGeneratorConfiguration.class })
public class TramMessageProducerJdbcConfiguration {

	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	@Bean
	@ConditionalOnMissingBean(MessageProducerImplementation.class)
	public MessageProducerImplementation messageProducerImplementation(EventuateCommonJdbcOperations eventuateCommonJdbcOperations,
			IdGenerator idGenerator,
			EventuateSchema eventuateSchema,
			SqlDialectSelector sqlDialectSelector) {
		return new MessageProducerJdbcImpl(eventuateCommonJdbcOperations,
				idGenerator,
				eventuateSchema,
				sqlDialectSelector.getDialect(driver).getCurrentTimeInMillisecondsExpression());
	}
}
