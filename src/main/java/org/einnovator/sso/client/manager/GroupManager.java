package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.Map;

import org.einnovator.sso.client.config.SsoClientContext;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.web.RequestOptions;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupManager {

	Group getGroup(String groupId, SsoClientContext context);

	Group getGroup(String groupId, GroupFilter filter, SsoClientContext context);

	Page<Group> listGroups(GroupFilter filter, Pageable pageable, SsoClientContext context);

	URI createGroup(Group group, RequestOptions options, SsoClientContext context);

	Group updateGroup(Group group, RequestOptions options, SsoClientContext context);

	void deleteGroup(String groupId, RequestOptions options, SsoClientContext context);

	
	Page<Member> listMembers(String groupId, MemberFilter filter, Pageable pageable, SsoClientContext context);

	Integer countMembers(String groupId, MemberFilter filter, SsoClientContext context);

	URI addMember(String userId, String groupId, RequestOptions options, SsoClientContext context);


	public URI addMember(Member member, String groupId, RequestOptions options, SsoClientContext context);
	
	void removeMember(String userId, String groupId, RequestOptions options, SsoClientContext context);

	Member getMember(String groupId, String userId, UserOptions options, SsoClientContext context);

	boolean isMember(String userId, String groupId, UserOptions options, SsoClientContext context);

	Page<Group> listGroupsForUser(String userId, GroupFilter filter, Pageable pageable, SsoClientContext context);

	Page<Group> listSubGroups(String groupId, boolean direct, GroupFilter filter, Pageable pageable, SsoClientContext context);

	Integer countSubGroups(String groupId, boolean direct, GroupFilter filter, SsoClientContext context);

	void clearCache();

	Cache getGroupCache();

	void onGroupUpdate(String id, Map<String, Object> details, SsoClientContext context);
	void onGroupMemberUpdate(String id, String userId, SsoClientContext context);

	Cache getGroupMembersCache();
	Cache getGroupMemberCountCache();

	void onEvent(ApplicationEvent event);

}
