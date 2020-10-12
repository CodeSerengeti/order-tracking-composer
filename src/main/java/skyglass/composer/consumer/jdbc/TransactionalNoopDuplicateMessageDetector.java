package skyglass.composer.consumer.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import skyglass.composer.consumer.common.DuplicateMessageDetector;
import skyglass.composer.consumer.common.SubscriberIdAndMessage;

public class TransactionalNoopDuplicateMessageDetector implements DuplicateMessageDetector {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private TransactionTemplate transactionTemplate;

	public TransactionalNoopDuplicateMessageDetector(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	@Override
	public boolean isDuplicate(String consumerId, String messageId) {
		return false;
	}

	@Override
	public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
		transactionTemplate.execute(ts -> {
			try {
				callback.run();
				return null;
			} catch (Throwable e) {
				logger.error("Got exception - marking for rollback only", e);
				ts.setRollbackOnly();
				throw e;
			}
		});

	}
}
