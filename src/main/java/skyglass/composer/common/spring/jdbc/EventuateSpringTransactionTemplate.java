package skyglass.composer.common.spring.jdbc;

import java.util.function.Supplier;

import org.springframework.transaction.support.TransactionTemplate;

import skyglass.composer.common.jdbc.EventuateTransactionTemplate;

public class EventuateSpringTransactionTemplate implements EventuateTransactionTemplate {

	private TransactionTemplate transactionTemplate;

	public EventuateSpringTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	@Override
	public <T> T executeInTransaction(Supplier<T> callback) {
		return transactionTemplate.execute(status -> callback.get());
	}
}
