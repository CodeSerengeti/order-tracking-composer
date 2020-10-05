package skyglass.composer.order.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import skyglass.composer.order.AEntityBean;
import skyglass.composer.order.entity.model.ItemEntity;

@Repository
@Transactional
public class ItemBean extends AEntityBean<ItemEntity> {

}
