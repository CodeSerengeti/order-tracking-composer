package skyglass.composer.order.domain.repository;

import skyglass.composer.order.entity.model.UserEntity;

public interface PermissionApi {

	public String getUsernameFromContext();

	public UserEntity getUserFromContext();

	public UserEntity getUser(String userId);

	public void checkAdmin();

	public UserEntity findByName(String name);

}
