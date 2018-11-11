package org.einnovator.sso.client.model;

import java.util.List;

import org.einnovator.util.model.EntityBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Permission extends EntityBase {	

	public static final String PERMISSION_ADD_EMPLOYEE = "add_employee";
	public static final String PERMISSION_DELETE_EMPLOYEE = "delete_employee";
	public static final String PERMISSION_EDIT_PERSONAL_PROFILE = "edit_personal_profile";
	public static final String PERMISSION_EDIT_ORGANIZATION_PROFILE = "edit_organization_profile";
	public static final String PERMISSION_CREATE_OPERATION_PROFILE = "create_operation_profile";
	public static final String PERMISSION_EDIT_OPERATION_PROFILE = "edit_operation_profile";
	public static final String PERMISSION_DELETE_OPERATION = "delete_operation";
	public static final String PERMISSION_MANAGE_ORGANIZATION_STRUCTURE = "manage_organization_structure";
	public static final String PERMISSION_ASSOCIATE_PRODUCT = "associate_product";
	public static final String PERMISSION_ADD_PRODUCT = "add_product";
	public static final String PERMISSION_UPDATE_PRODUCT = "update_product";
	public static final String PERMISSION_DELETE_PRODUCT =  "delete_product";
	public static final String PERMISSION_ADD_SERVICE = "add_service";
	public static final String PERMISSION_UPDATE_SERVICE = "update_service";
	public static final String PERMISSION_DELETE_SERVICE = "delete_service";
	public static final String PERMISSION_CREATE_USER_FOLDER = "create_user_folder";
	public static final String PERMISSION_UPDATE_USER_FOLDER = "update_user_folder";
	public static final String PERMISSION_CREATE_ORGANIZATION_FOLDER = "create_organization_folder";
	public static final String PERMISSION_UPDATE_ORGANIZATION_FOLDER = "update_organization_folder";
	public static final String PERMISSION_DELETE_ORGANIZATION_FOLDER = "delete_organization_folder";
	public static final String PERMISSION_VIEW_USER_PERMISSION = "view_user_permission";
	public static final String PERMISSION_CHANGE_USER_PERMISSION = "change_user_permission";
	public static final String PERMISSION_CREATE_ORGANIZATION_DOCUMENT = "create_organization_document";
	public static final String PERMISSION_EDIT_ORGANIZATION_DOCUMENT = "edit_organization_document";
	public static final String PERMISSION_VIEW_ORGANIZATION_DOCUMENT = "view_organization_document";
	public static final String PERMISSION_DELETE_ORGANIZATION_DOCUMENT = "delete_organization_document";
	public static final String PERMISSION_CREATE_USER_DOCUMENT = "create_user_document";
	public static final String PERMISSION_EDIT_USER_DOCUMENT = "edit_user_document";
	public static final String PERMISSION_VIEW_USER_DOCUMENT = "view_user_document";
	public static final String PERMISSION_CREATE_EDIT_RESOURCE = "create_edit_resource";
	public static final String PERMISSION_VIEW_RESOURCE = "view_resource";
	public static final String PERMISSION_EDIT_SHARED_DOCUMENT = "edit_shared_document";
	public static final String PERMISSION_ADD_DOCUMENT_TEMPLATE = "add_document_template";
	public static final String PERMISSION_EDIT_DOCUMENT_TEMPLATE = "edit_document_template";
	public static final String PERMISSION_DELETE_DOCUMENT_TEMPLATE = "delete_document_template";
	public static final String PERMISSION_VIEW_ORGANIZATION_REPORTS = "view_organization_reports";
	public static final String PERMISSION_BULK_INVITE_SUPPLIERS = "bulk_invite_suppliers";
	public static final String PERMISSION_MANAGE_SUPPLY_CHAIN_VISIBILITY = "manage_supply_chain_visibility";
	public static final String PERMISSION_VIEW_SUPPLY_CHAIN = "view_supply_chain";
	public static final String PERMISSION_MANAGE_SUPPLY_CHAIN = "manage_supply_chain";
	public static final String PERMISSION_VIEW_SUPPLY_CHAIN_REQUIREMENT = "view_supply_chain_requirement";
	public static final String PERMISSION_FULFILL_SUPPLY_CHAIN_REQUIREMENT = "fulfill_supply_chain_requirement";
	public static final String PERMISSION_ASSIGN_SUPPLY_CHAIN_REQUIREMENT = "assign_supply_chain_requirement";
	public static final String PERMISSION_AUTHENTICATE_AUDIT_DOCUMENT = "authenticate_audit_document";
	public static final String PERMISSION_AUTHENTICATE_LAB_DOCUMENT = "authenticate_lab_document";
	public static final String PERMISSION_AUTHENTICATE_LAB_REQUIREMENTS = "authenticate_lab_requirements";
	public static final String PERMISSION_ASSIGN_PROCTOR_ROLE = "assign_proctor_role";
	public static final String PERMISSION_EXAM_ORGANIZER = "exam_organizer";
	public static final String PERMISSION_UPLOAD_TRAINING = "upload_training";
	public static final String PERMISSION_EXAM_CREATOR = "exam_creator";
	public static final String PERMISSION_EXAM_REATOR = "exam_reator";
	public static final String PERMISSION_EXAM_ADMIN = "exam_admin";
	public static final String PERMISSION_MAKE_PAYMENTS = "make_payments";
	public static final String PERMISSION_EDIT_CARDS = "edit_cards";
	public static final String PERMISSION_EDIT_BANKACCOUNTS = "edit_bankaccounts";
	public static final String PERMISSION_CONNECTION_MANAGER = "connection_manager";
	public static final String PERMISSION_MANAGE_TAGS = "manage_tags";
	public static final String PERMISSION_MANAGE_AUDIT_REPORTS = "manage_audit_reports";
	public static final String PERMISSION_MANAGE_AUDITS = "manage_audits";
	public static final String PERMISSION_MANAGE_PAYMENTS = "manage_payments";
	public static final String PERMISSION_ADMIN_PAYMENTS = "admin_payments";

		
	private String key;

	private String name;
	
	private String app;

	private String description;

	private String category;
	
	private String imgUri;

	private String icon;
	
	public Permission() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public static Permission find(String id, List<Permission> perms) {
		if (perms!=null) {
			for (Permission perm: perms) {
				if (id.equalsIgnoreCase(perm.getKey())) {
					return perm;
				}
			}
		}
		return null;
	}
	

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" 
				+ (id != null ? "id=" + id + ", " : "")
				+ (key != null ? "key=" + key + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (category != null ? "category=" + category : "")
				+ (icon != null ? "icon=" + icon + ", " : "")
				+ (imgUri != null ? "imgUri=" + imgUri + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (app != null ? "app=" + app + ", " : "")
				+ "]";
	}

}
