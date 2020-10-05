package skyglass.composer.order.entity.service;

import skyglass.composer.order.domain.model.Stock;

public interface StockService {

	Iterable<Stock> getAll();

	Stock getByUuid(String uuid);

	Stock findByItemAndBusinessUnit(String itemUuid, String businessUnitUuid);

	Stock deactivate(String itemUuid, String businessUnitUuid);

}
