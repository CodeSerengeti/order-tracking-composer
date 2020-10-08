package skyglass.composer.messaging.partitionmanagement;

import java.util.Set;
import java.util.function.Consumer;

import skyglass.composer.coordination.leadership.LeaderSelectedCallback;

public interface CoordinatorFactory {
	Coordinator makeCoordinator(String subscriberId,
			Set<String> channels,
			String subscriptionId,
			Consumer<Assignment> assignmentUpdatedCallback,
			String lockId,
			LeaderSelectedCallback leaderSelected,
			Runnable leaderRemoved);
}
