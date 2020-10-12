package skyglass.composer.spring.consumer.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.common.jdbc.EventuateJdbcStatementExecutor;
import skyglass.composer.common.jdbc.EventuateSchema;
import skyglass.composer.common.jdbc.EventuateTransactionTemplate;
import skyglass.composer.common.jdbc.sqldialect.SqlDialectSelector;
import skyglass.composer.common.spring.jdbc.EventuateSchemaConfiguration;
import skyglass.composer.common.spring.jdbc.sqldialect.SqlDialectConfiguration;
import skyglass.composer.consumer.common.DuplicateMessageDetector;
import skyglass.composer.consumer.jdbc.SqlTableBasedDuplicateMessageDetector;

@Configuration
@Import({ SqlDialectConfiguration.class,
		EventuateSchemaConfiguration.class })
@ConditionalOnMissingBean(DuplicateMessageDetector.class)
public class TramConsumerJdbcAutoConfiguration {

	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	@Bean
	public DuplicateMessageDetector duplicateMessageDetector(EventuateSchema eventuateSchema,
			SqlDialectSelector sqlDialectSelector,
			EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
			EventuateTransactionTemplate eventuateTransactionTemplate) {
		return new SqlTableBasedDuplicateMessageDetector(eventuateSchema,
				sqlDialectSelector.getDialect(driver).getCurrentTimeInMillisecondsExpression(),
				eventuateJdbcStatementExecutor,
				eventuateTransactionTemplate);
	}

}
