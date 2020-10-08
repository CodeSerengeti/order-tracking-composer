package skyglass.composer.messaging.partitionmanagement;

public interface SubscriptionLeaderHook {
  void leaderUpdated(Boolean leader, String subscriptionId);
}
