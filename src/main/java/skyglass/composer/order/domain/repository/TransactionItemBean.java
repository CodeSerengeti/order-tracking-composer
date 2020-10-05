package skyglass.composer.order.domain.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import skyglass.composer.order.AEntityBean;
import skyglass.composer.order.domain.model.TransactionItem;
import skyglass.composer.order.domain.model.TransactionType;
import skyglass.composer.order.entity.model.EntityUtil;
import skyglass.composer.order.entity.model.StockTransactionEntity;
import skyglass.composer.order.entity.model.TransactionItemEntity;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class TransactionItemBean extends AEntityBean<TransactionItemEntity> {

	public List<TransactionItemEntity> findByTransaction(StockTransactionEntity transaction) {
		String queryStr = "SELECT ti FROM TransactionItemEntity ti WHERE ti.transaction.uuid = :transactionUuid";
		TypedQuery<TransactionItemEntity> query = entityBeanUtil.createQuery(queryStr, TransactionItemEntity.class);
		query.setParameter("transactionUuid", transaction.getUuid());
		return EntityUtil.getListResultSafely(query);
	}

	public List<TransactionItemEntity> findByKey(String key) {
		String queryStr = "SELECT ti FROM TransactionItemEntity ti WHERE ti.key = :key";
		TypedQuery<TransactionItemEntity> query = entityBeanUtil.createQuery(queryStr, TransactionItemEntity.class);
		query.setParameter("key", key);
		return EntityUtil.getListResultSafely(query);
	}

	public TransactionItemEntity findByTransactionType(StockTransactionEntity transaction, TransactionType transactionType) {
		String queryStr = "SELECT ti FROM TransactionItemEntity ti WHERE ti.transaction.uuid = :transactionUuid AND ti.transactionType = :transactionType";
		TypedQuery<TransactionItemEntity> query = entityBeanUtil.createQuery(queryStr, TransactionItemEntity.class);
		query.setParameter("transactionUuid", transaction.getUuid());
		query.setParameter("transactionType", transactionType);
		return EntityUtil.getSingleResultSafely(query);
	}

	public TransactionItemEntity create(StockTransactionEntity transaction, String key, TransactionType transactionType) {
		return create(TransactionItem.create(transaction, key, transactionType));
	}

}
