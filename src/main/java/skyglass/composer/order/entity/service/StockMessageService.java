package skyglass.composer.order.entity.service;

import java.util.Collection;

import skyglass.composer.order.domain.model.StockMessage;

public interface StockMessageService {

	Collection<StockMessage> getAll();

	StockMessage getByUuid(String uuid);

}
