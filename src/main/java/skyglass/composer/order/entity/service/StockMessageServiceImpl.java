package skyglass.composer.order.entity.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import skyglass.composer.order.domain.model.StockMessage;
import skyglass.composer.order.domain.repository.StockMessageBean;
import skyglass.composer.order.entity.model.StockMessageEntity;

@Service
@Transactional
public class StockMessageServiceImpl implements StockMessageService {

	private final StockMessageBean stockMessageBean;

	@PersistenceContext
	private EntityManager entityManager;

	StockMessageServiceImpl(StockMessageBean stockMessageBean) {
		this.stockMessageBean = stockMessageBean;
	}

	@Override
	public Collection<StockMessage> getAll() {
		return StreamSupport.stream(stockMessageBean.findAll().spliterator(), false)
				.map(sm -> StockMessage.mapEntity(sm))
				.collect(Collectors.toList());
	}

	@Override
	public StockMessage getByUuid(String uuid) {
		StockMessageEntity entity = this.stockMessageBean.findByUuid(uuid);
		if (entity == null) {
			return null;
		}

		return StockMessage.mapEntity(entity);
	}

}
