package org.einnovator.sso.client.manager;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Permission;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.UserFilter;

public class PermissionManagerImpl implements PermissionManager {

	private final Log logger = LogFactory.getLog(getClass());


	@Autowired
	private SsoClient client;

	@Autowired
	public PermissionManagerImpl() {
	}
	
	public PermissionManagerImpl(SsoClient client) {
		this.client = client;
	}

	
	@Override
	public URI createPermission(Permission permission) {
		try {
			URI uri = client.createPermission(permission);
			if (uri == null) {
				logger.error("createPermission: " + permission);
			}
			return uri;
		} catch (RuntimeException e) {
			logger.error("createPermission: " + permission + " " + e);
			return null;
		}
	}

	@Override
	public Permission getPermission(String id) {
		try {
			Permission group = client.getPermission(id);
			if (group == null) {
				logger.error("getPermission: " + group);
			}
			return group;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				logger.error("getPermission:" + id + "  " + e);
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getPermission: " + id + "  " + e);
			return null;
		}
	}



	@Override
	public Page<Permission> listPermissions(Pageable pageable) {
		try {
			return client.listPermissions(pageable);
		} catch (RuntimeException e) {
			logger.error("listPermissions: " + e + " " + pageable);
			return null;
		}
	}




	@Override
	public boolean deletePermission(String permissionId) {
		try {
			client.deletePermission(permissionId);
			if (permissionId == null) {
				logger.error("deletePermission: " + permissionId);
			}
			return true;
		} catch (RuntimeException e) {
			logger.error("deletePermission: " + permissionId);
			return false;
		}
	}



	@Override
	public Page<User> listPermissionMembers(String permissionId, Pageable pageable, UserFilter filter) {
		try {
			return client.listPermissionMembers(permissionId, pageable, filter);
		} catch (RuntimeException e) {
			logger.error("listPermissions: " + e + " " + permissionId + " " + pageable + " " + filter);
			return null;
		}
	}
	
	public Page<User> listPermissionMembers(String permissionId, String groupId, Pageable pageable, UserFilter filter) {
		try {
			return client.listPermissionMembers(permissionId, groupId, pageable, filter);
		} catch (RuntimeException e) {
			logger.error("listPermissions: " + e + " " + permissionId + " " + groupId + " " + pageable + " " + filter);
			return null;
		}
	}

}
