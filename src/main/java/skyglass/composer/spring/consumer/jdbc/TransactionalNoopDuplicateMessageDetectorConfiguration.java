package skyglass.composer.spring.consumer.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import skyglass.composer.consumer.common.DuplicateMessageDetector;
import skyglass.composer.consumer.jdbc.TransactionalNoopDuplicateMessageDetector;

@Configuration
public class TransactionalNoopDuplicateMessageDetectorConfiguration {

	@Bean
	public DuplicateMessageDetector duplicateMessageDetector(TransactionTemplate transactionTemplate) {
		return new TransactionalNoopDuplicateMessageDetector(transactionTemplate);
	}
}
