package skyglass.composer.order.entity.service;

import java.util.Date;
import java.util.List;

import skyglass.composer.order.domain.model.BusinessUnit;
import skyglass.composer.order.domain.model.Item;
import skyglass.composer.order.domain.model.StockHistory;

public interface StockHistoryService {

	StockHistory findByUuid(String uuid);

	List<StockHistory> find(Item item, BusinessUnit businessUnit);

	List<StockHistory> findForPeriod(Item item, BusinessUnit businessUnit, Date startDate, Date endDate);

}
