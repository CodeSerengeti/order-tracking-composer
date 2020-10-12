package skyglass.composer.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import skyglass.composer.common.id.IdGenerator;
import skyglass.composer.messaging.common.Message;
import skyglass.composer.messaging.producer.common.MessageProducerImplementation;

public class InMemoryMessageProducer implements MessageProducerImplementation {

	private final InMemoryMessageConsumer messageConsumer;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private IdGenerator idGenerator;

	public InMemoryMessageProducer(InMemoryMessageConsumer messageConsumer, IdGenerator idGenerator) {
		this.messageConsumer = messageConsumer;
		this.idGenerator = idGenerator;
	}

	@Override
	public String generateMessageId() {
		return idGenerator.genId().asString();
	}

	@Override
	public void withContext(Runnable runnable) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			logger.info("Transaction active");
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
				@Override
				public void afterCommit() {
					runnable.run();
				}
			});
		} else {
			logger.info("No transaction active");
			runnable.run();
		}

	}

	@Override
	public void send(Message message) {
		messageConsumer.dispatchMessage(message);
	}

}
