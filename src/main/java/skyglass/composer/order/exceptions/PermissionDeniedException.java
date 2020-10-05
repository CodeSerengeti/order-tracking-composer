package skyglass.composer.order.exceptions;

import skyglass.composer.order.domain.model.CrudAction;
import skyglass.composer.order.domain.model.IdObject;

public class PermissionDeniedException extends NotAllowedException {

	private static final long serialVersionUID = -3993014845712597385L;

	public PermissionDeniedException(Class<? extends IdObject> entityType, CrudAction action) {
		super(entityType, action);
	}

	public PermissionDeniedException(Class<? extends IdObject> entityType, CrudAction action, Throwable cause) {
		super(entityType, action, cause);
	}

	public PermissionDeniedException(Class<? extends IdObject> entityType, String uuid) {
		super(entityType, uuid);
	}

	public PermissionDeniedException(Class<? extends IdObject> entityType, String uuid, Throwable cause) {
		super(entityType, uuid, cause);
	}

}
