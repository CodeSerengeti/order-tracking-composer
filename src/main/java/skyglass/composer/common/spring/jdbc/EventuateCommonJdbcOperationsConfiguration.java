package skyglass.composer.common.spring.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import skyglass.composer.common.jdbc.EventuateCommonJdbcOperations;
import skyglass.composer.common.jdbc.EventuateJdbcStatementExecutor;
import skyglass.composer.common.jdbc.EventuateTransactionTemplate;
import skyglass.composer.common.jdbc.sqldialect.SqlDialectSelector;
import skyglass.composer.common.spring.jdbc.sqldialect.SqlDialectConfiguration;

@Configuration
@Import({ EventuateSchemaConfiguration.class, SqlDialectConfiguration.class })
public class EventuateCommonJdbcOperationsConfiguration {

	@Bean
	public EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor(JdbcTemplate jdbcTemplate) {
		return new EventuateSpringJdbcStatementExecutor(jdbcTemplate);
	}

	@Bean
	public EventuateTransactionTemplate eventuateTransactionTemplate(TransactionTemplate transactionTemplate) {
		return new EventuateSpringTransactionTemplate(transactionTemplate);
	}

	@Bean
	public EventuateCommonJdbcOperations eventuateCommonJdbcOperations(EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
			SqlDialectSelector sqlDialectSelector,
			@Value("${spring.datasource.driver-class-name}") String driver) {
		return new EventuateCommonJdbcOperations(eventuateJdbcStatementExecutor, sqlDialectSelector.getDialect(driver));
	}
}
