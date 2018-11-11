package org.einnovator.sso.client.manager;

import java.net.URI;

import org.einnovator.sso.client.model.Permission;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionManager {

	URI createPermission(Permission permission);

	Permission getPermission(String id);
	
	Page<Permission> listPermissions(Pageable pageable);
	
	boolean deletePermission(String id);
	
	Page<User> listPermissionMembers(String permissionId, Pageable pageable, UserFilter filter);
	
	Page<User> listPermissionMembers(String permissionId, String groupId, Pageable pageable, UserFilter filter);
}
