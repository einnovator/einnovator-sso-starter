package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.Map;

import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.GroupType;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupManager {

	Group getGroup(String groupId);

	Group getGroup(String groupId, GroupFilter filter);

	URI createGroup(Group group);

	Group updateGroup(Group group);

	Page<Group> listGroups(Pageable pageable);

	Page<Group> listGroups(GroupFilter filter, Pageable pageable);

	void deleteGroup(String groupId);

	Page<Member> listMembers(String groupId, MemberFilter filter, Pageable pageable);

	Integer countMembers(String groupId, MemberFilter filter);

	void addMember(String userId, String groupId);

	void removeMember(String userId, String groupId);

	Member getMember(String groupId, String userId);

	boolean isMember(String userId, String groupId);

	Page<Group> listGroupsForUser(String userId, GroupType type, Pageable pageable);

	Page<Group> listSubGroups(String groupId, GroupType type, boolean direct, Pageable pageable);

	Integer countSubGroups(String groupId, GroupType type, boolean direct);

	void clearCache();

	Cache getGroupCache();

	void onGroupUpdate(String id, Map<String, Object> details);
	void onGroupMemberUpdate(String id, String userId);

	Cache getGroupMembersCache();
	Cache getGroupMemberCountCache();

	/**
	 * @param event
	 */
	void onEvent(ApplicationEvent event);

}
