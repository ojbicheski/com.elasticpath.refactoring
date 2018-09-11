package com.elasticpath.refactoring;

import com.elasticpath.refactoring.enums.FulfillmentPermissions;

/**
 * Stub class for AuthorizationService which is dependent on user's permissions.
 */
public class AuthorizationService {
	public static AuthorizationService getInstance() {
		return new AuthorizationService();
	}

	public boolean isAuthorized(final FulfillmentPermissions fulfillmentPermission) {
		// In a real implementation, this would return true or false depending on user's rights.
		return true;
	}
}
