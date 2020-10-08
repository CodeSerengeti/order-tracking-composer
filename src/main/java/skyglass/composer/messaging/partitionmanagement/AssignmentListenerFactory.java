package skyglass.composer.messaging.partitionmanagement;

import java.util.function.Consumer;

public interface AssignmentListenerFactory {
  AssignmentListener create(String groupId, String memberId, Consumer<Assignment> assignmentUpdatedCallback);
}
