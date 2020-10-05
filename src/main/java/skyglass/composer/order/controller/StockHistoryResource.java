package skyglass.composer.order.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import skyglass.composer.order.domain.model.NotFoundException;
import skyglass.composer.order.domain.model.StockHistory;
import skyglass.composer.order.entity.service.StockHistoryService;

@RestController
@RequestMapping("/stock-history")
class StockHistoryResource {

	private final StockHistoryService stockHistoryService;

	StockHistoryResource(StockHistoryService stockHistoryService) {
		this.stockHistoryService = stockHistoryService;
	}

	@GetMapping("/{uuid}")
	@ApiOperation(value = "Find stock history by uuid", notes = "Also returns a link to retrieve all stock histories with rel - all-stock histories")
	public EntityModel<StockHistory> retrieveStockHistory(@PathVariable String uuid) {
		StockHistory stockHistory = stockHistoryService.findByUuid(uuid);

		if (stockHistory == null)
			throw new NotFoundException("uuid-" + uuid);

		EntityModel<StockHistory> resource = EntityModel.of(stockHistory);

		return resource;
	}

}
