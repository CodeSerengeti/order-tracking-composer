package skyglass.composer.common.spring.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.common.jdbc.EventuateSchema;

@Configuration
public class EventuateSchemaConfiguration {

	@Bean
	public EventuateSchema eventuateSchema(@Value("${eventuate.database.schema:#{null}}") String eventuateDatabaseSchema) {
		return new EventuateSchema(eventuateDatabaseSchema);
	}

}
