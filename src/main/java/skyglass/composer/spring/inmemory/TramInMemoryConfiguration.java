package skyglass.composer.spring.inmemory;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import skyglass.composer.spring.consumer.jdbc.TransactionalNoopDuplicateMessageDetectorConfiguration;

@Configuration
@Import({ TramInMemoryCommonConfiguration.class, TransactionalNoopDuplicateMessageDetectorConfiguration.class, })
public class TramInMemoryConfiguration {
}
