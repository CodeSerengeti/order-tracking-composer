package skyglass.composer.order.entity.service;

import skyglass.composer.order.domain.model.BusinessUnit;

public interface BusinessUnitService {

	Iterable<BusinessUnit> getAll();

	BusinessUnit getByUuid(String uuid);

}
