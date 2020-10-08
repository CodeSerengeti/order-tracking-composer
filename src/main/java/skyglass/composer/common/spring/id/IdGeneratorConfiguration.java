package skyglass.composer.common.spring.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.common.id.IdGenerator;
import skyglass.composer.common.id.IdGeneratorImpl;

@Configuration
public class IdGeneratorConfiguration {

	@Bean
	public IdGenerator idGenerator() {
		return new IdGeneratorImpl();
	}

}
