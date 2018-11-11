package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.Map;

import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.GroupType;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupManager {
	URI createGroup(Group group);

	Group getGroup(String groupId);

	Group updateGroup(Group group);

	Page<Group> listGroups(Pageable pageable);

	Page<Group> listGroups(GroupFilter filter, Pageable pageable);

	void deleteGroup(String groupId);

	Page<Member> listGroupMembers(String groupId, Pageable pageable, UserFilter filter);

	Integer countGroupMembers(String groupId, UserFilter filter);

	void addToGroup(String userId, String groupId);

	void removeFromGroup(String userId, String groupId);

	Member getGroupMember(String groupId, String userId);

	boolean isMember(String userId, String groupId);

	Page<Group> listGroupsForUser(String userId, GroupType type, Pageable pageable);

	Page<Group> listSubGroups(String groupId, GroupType type, boolean direct, Pageable pageable);

	Integer countSubGroups(String groupId, GroupType type, boolean direct);

	void clearCache();

	Cache getGroupCache();

	void onGroupUpdate(String id, Map<String, Object> details);
	void onGroupMemberUpdate(String id, String userId);

	Cache getGroupMembersCache();
	Cache getGroupMemberCache();

}
