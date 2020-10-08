package skyglass.composer.messaging.partitionmanagement;

public interface GroupMemberFactory {
  GroupMember create(String groupId, String memberId);
}
