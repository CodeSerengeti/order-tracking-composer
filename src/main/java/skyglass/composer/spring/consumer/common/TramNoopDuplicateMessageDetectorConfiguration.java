package skyglass.composer.spring.consumer.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import skyglass.composer.consumer.common.DuplicateMessageDetector;
import skyglass.composer.consumer.common.NoopDuplicateMessageDetector;

@Configuration
public class TramNoopDuplicateMessageDetectorConfiguration {

	@Bean
	public DuplicateMessageDetector duplicateMessageDetector() {
		return new NoopDuplicateMessageDetector();
	}
}
