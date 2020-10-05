package skyglass.composer.order;

import skyglass.composer.order.entity.model.AEntity;
import skyglass.composer.order.exceptions.NotAccessibleException;

public interface EntityRepository<E extends AEntity> {

	public E findByUuid(String uuid);

	public E findByUuidSecure(String uuid) throws NotAccessibleException;

}
