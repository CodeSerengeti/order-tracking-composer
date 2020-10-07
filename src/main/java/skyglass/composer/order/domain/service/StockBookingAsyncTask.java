package skyglass.composer.order.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import skyglass.composer.order.domain.model.StockMessage;

@Component
public class StockBookingAsyncTask {

	@Autowired
	private StockUpdateService stockUpdateService;

	@Async
	public void task(StockMessage stockMessage) {
		stockUpdateService.replayTransaction(stockMessage);
	}

}
