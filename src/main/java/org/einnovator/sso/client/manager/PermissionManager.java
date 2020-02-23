package org.einnovator.sso.client.manager;

import java.net.URI;

import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Permission;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionManager {

	URI createPermission(Permission permission, SsoClientContext context);

	Permission getPermission(String id, SsoClientContext context);
	
	Page<Permission> listPermissions(Pageable pageable, SsoClientContext context);
	
	boolean deletePermission(String id, SsoClientContext context);
	
	Page<User> listPermissionMembers(String permissionId, Pageable pageable, UserFilter filter, SsoClientContext context);
	
	Page<User> listPermissionMembers(String permissionId, String groupId, Pageable pageable, UserFilter filter, SsoClientContext context);
}
