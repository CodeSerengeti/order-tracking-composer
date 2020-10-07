package skyglass.composer.sagas.participant;

import java.util.Map;
import java.util.Optional;

import skyglass.composer.messaging.common.MessageImpl;
import skyglass.composer.sagas.common.LockTarget;

public class SagaReplyMessage extends MessageImpl {
	private Optional<LockTarget> lockTarget;

	public SagaReplyMessage(String body, Map<String, String> headers, Optional<LockTarget> lockTarget) {
		super(body, headers);
		this.lockTarget = lockTarget;
	}

	public Optional<LockTarget> getLockTarget() {
		return lockTarget;
	}

	public boolean hasLockTarget() {
		return lockTarget.isPresent();
	}
}
