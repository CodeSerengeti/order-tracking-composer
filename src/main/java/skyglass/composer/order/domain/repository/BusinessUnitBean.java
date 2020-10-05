package skyglass.composer.order.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import skyglass.composer.order.AEntityBean;
import skyglass.composer.order.entity.model.BusinessUnitEntity;

@Repository
@Transactional
public class BusinessUnitBean extends AEntityBean<BusinessUnitEntity> {

}
