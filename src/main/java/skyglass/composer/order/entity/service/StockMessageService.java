package skyglass.composer.order.entity.service;

import skyglass.composer.order.domain.model.StockMessage;

public interface StockMessageService {

	Iterable<StockMessage> getAll();

	StockMessage getByUuid(String uuid);

}
