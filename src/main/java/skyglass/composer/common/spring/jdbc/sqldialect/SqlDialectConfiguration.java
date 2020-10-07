package skyglass.composer.common.spring.jdbc.sqldialect;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.common.jdbc.sqldialect.DefaultEventuateSqlDialect;
import skyglass.composer.common.jdbc.sqldialect.EventuateSqlDialect;
import skyglass.composer.common.jdbc.sqldialect.MsSqlDialect;
import skyglass.composer.common.jdbc.sqldialect.MySqlDialect;
import skyglass.composer.common.jdbc.sqldialect.PostgresDialect;
import skyglass.composer.common.jdbc.sqldialect.SqlDialectSelector;

@Configuration
public class SqlDialectConfiguration {

	@Bean
	public MySqlDialect mySqlDialect() {
		return new MySqlDialect();
	}

	@Bean
	public PostgresDialect postgreSQLDialect() {
		return new PostgresDialect();
	}

	@Bean
	public MsSqlDialect msSqlDialect() {
		return new MsSqlDialect();
	}

	@Bean
	public DefaultEventuateSqlDialect defaultSqlDialect(@Value("${eventuate.current.time.in.milliseconds.sql:#{null}}") String customCurrentTimeInMillisecondsExpression) {
		return new DefaultEventuateSqlDialect(customCurrentTimeInMillisecondsExpression);
	}

	@Bean
	public SqlDialectSelector sqlDialectSelector(Collection<EventuateSqlDialect> dialects) {
		return new SqlDialectSelector(dialects);
	}
}
