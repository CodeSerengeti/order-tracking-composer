package skyglass.composer.common.spring.inmemorydatabase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.common.inmemorydatabase.EventuateDatabaseScriptSupplier;
import skyglass.composer.common.inmemorydatabase.EventuateInMemoryDataSourceBuilder;

@Configuration
public class EventuateCommonInMemoryDatabaseConfiguration {
	@Bean
	public DataSource dataSource(@Autowired(required = false) List<EventuateDatabaseScriptSupplier> scripts) {
		return new EventuateInMemoryDataSourceBuilder(Optional.ofNullable(scripts).orElse(Collections.emptyList())).build();
	}
}
