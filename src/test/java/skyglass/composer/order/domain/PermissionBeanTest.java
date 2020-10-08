package skyglass.composer.order.domain;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import skyglass.composer.order.test.bean.MockHelper;
import skyglass.composer.order.test.reset.AbstractBaseTest;
import skyglass.composer.stock.domain.repository.PermissionApi;
import skyglass.composer.stock.entity.model.UserEntity;

// @ActiveProfiles({ AbstractBaseTest.PROFILE_PSQL })
public class PermissionBeanTest extends AbstractBaseTest {

	@Autowired
	private MockHelper mockHelper;

	@Autowired
	private PermissionApi permissionService;

	@Test
	public void getCurrentUser() throws Exception {
		mockHelper.logout();
		UserEntity user = permissionService.getUserFromContext();
		Assert.assertTrue(user == null);
	}

}
