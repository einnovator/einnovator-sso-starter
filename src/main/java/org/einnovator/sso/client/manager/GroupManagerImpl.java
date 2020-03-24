package org.einnovator.sso.client.manager;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.SsoClient;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.UserOptions;
import org.einnovator.util.security.SecurityUtil;
import org.einnovator.util.web.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class GroupManagerImpl extends ManagerBase implements GroupManager {

	public static final String CACHE_GROUP_MEMBER_COUNT = "GroupMember:count";

	public static final String CACHE_GROUP = "Group";
	public static final String CACHE_GROUP_MEMBERS = "GroupMembers";
	public static final String CACHE_GROUP_MEMBER = "GroupMember";


	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SsoClient client;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private UserManager userManager;
	
	public GroupManagerImpl(SsoClient client, CacheManager cacheManager) {
		this.client = client;
		this.cacheManager = cacheManager;
	}

	public GroupManagerImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public Group getLocalGroup(String id, boolean remote) {
		String principalName = SecurityUtil.getPrincipalName();

		if (id==null || principalName==null || SecurityUtil.isAnonymous()) {
			return null;
		}
		User user = SsoClient.getPrincipalUser();
		if (user!=null) {
			Group group = Group.findByUuid(id, user.getGroups());
			if (group!=null) {
				return group;
			}
		}
		if (remote) {
			return getGroup(id);							
		}
		return null;
	}
	
	@Override
	public URI createGroup(Group group, RequestOptions options) {
		try {
			URI uri = client.createGroup(group, options);
			if (uri == null) {
				logger.error("createGroup: " + group);
			}
			return uri;
		} catch (RuntimeException e) {
			logger.error("createGroup: " + group + " " + e);
			return null;
		}
	}

	@Override
	public Group getGroup(String groupId) {
		return getGroup(groupId, null);
	}

	@Override
	public Group getGroup(String groupId, GroupFilter filter) {
		if (groupId==null) {
			return null;
		}
		try {
			if (cacheable(filter)) {
				Group group = getCacheValue(Group.class, getGroupCache(), groupId, filter);
				if (group!=null) {
					return group;
				}			
			}
			Group group = client.getGroup(groupId, filter);
			if (cacheable(filter)) {
				putCacheValue(group, getGroupCache(), groupId, filter);
			}
			return group;
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				logger.error("getGroup:" + groupId + "  " + e);
			}
			return null;
		} catch (RuntimeException e) {
			logger.error("getGroup: " + groupId + "  " + e);
			return null;
		}
	}

	protected boolean cacheable(GroupFilter filter) {
		return filter==null || GroupFilter.TREE.equals(filter) || GroupFilter.OPERATIONS.equals(filter) || GroupFilter.TEAMS.equals(filter);
	}

	@Override
	public Group updateGroup(Group group, RequestOptions options) {
		try {
			client.updateGroup(group, options);
			if (group == null) {
				logger.error("updateGroup: " + group);
			}
			evictCaches(group);
			return group;
		} catch (RuntimeException e) {
			logger.error("updateGroup: " + group + "  " + e);
			return null;
		}
	}


	@Override
	public Page<Group> listGroups(GroupFilter filter, Pageable pageable) {
		try {
			Page<Group> groups = client.listGroups(filter, pageable);
			if (groups == null) {
				logger.error("listGroups: " + pageable);
			}
			return groups;
		} catch (RuntimeException e) {
			logger.error("listGroups: " + pageable + "  " + e);
			return null;
		}
	}

	@Override
	@CacheEvict(value = CACHE_GROUP, key = "#groupId")
	public boolean deleteGroup(String groupId, RequestOptions options) {
		try {
			client.deleteGroup(groupId, options);
			if (groupId == null) {
				logger.error("deleteGroup: " + groupId);
			}
			return true;
		} catch (RuntimeException e) {
			logger.error("deleteGroup: " + groupId);
			return false;
		}
	}

	@Override
	public Page<Member> listMembers(String groupId, MemberFilter filter, Pageable pageable) {
		try {
			Page<Member> members = client.listGroupMembers(groupId, filter, pageable);
			if (members == null) {
				logger.error("listGroupMembers: " + groupId);
			}
			return members;
		} catch (RuntimeException e) {
			logger.error("listGroupMembers: " + groupId + "  " + e);
			return null;
		}
	}

	@Override
	public Integer countMembers(String groupId, MemberFilter filter) {
		if (groupId==null) {
			return null;
		}		
		Integer count = null;
		if (filter==null) {
			count = getCacheValue(Integer.class, getGroupMemberCountCache(), groupId);
			if (count!=null) {
				return count;
			}
		}

		try {
			count = client.countGroupMembers(groupId, filter);
			if (count!=null && filter==null) {
				return putCacheValue(count, getGroupMemberCountCache(), groupId);
			}
			return count;
		} catch (RuntimeException e) {
			logger.error("countGroupMembers: " + groupId + "  " + e);
			return null;
		}
	}


	@Override
	public URI addMember(String userId, String groupId, RequestOptions options) {
		try {
			if (groupId == null) {
				logger.error("addToGroup: " + userId);
			}
			URI uri = client.addMemberToGroup(userId, groupId, options);
			evictCaches(groupId);
			userManager.evictCaches(userId);
			return uri;
		} catch (RuntimeException e) {
			logger.error("addToGroup: " + userId + " " + groupId);
			return null;
		}
	}
	
	@Override
	public URI addMember(Member member, String groupId, RequestOptions options) {
		try {
			if (groupId == null) {
				logger.error("addToGroup: " + member);
			}
			URI uri = client.addMemberToGroup(member, groupId, options);
			evictCaches(groupId);
			if (member.getUser()!=null && member.getUser().getUuid()!=null) {
				userManager.evictCaches(member.getUser().getUuid());				
			}
			return uri;
		} catch (RuntimeException e) {
			logger.error("addToGroup: " + member + " " + groupId);
			return null;
		}
	}

	@Override
	@CacheEvict(value = CACHE_GROUP_MEMBERS, key = "#groupId + #userId")
	public boolean removeMember(String userId, String groupId, RequestOptions options) {
		if (groupId == null) {
			logger.error("removeFromGroup: " + userId + " " + groupId);
			return false;
		}
		try {
			client.removeMemberFromGroup(userId, groupId, options);
			evictCaches(groupId);
			userManager.evictCaches(userId);
			return true;
		} catch (RuntimeException e) {
			logger.error("removeFromGroup: " + userId + " " + groupId);
			return false;
		}
	}

	@Override
	public Member getMember(String groupId, String userId, UserOptions options) {
		try {
			Member member = client.getGroupMember(groupId, userId, options);
			if (groupId == null) {
				logger.error("getGroupMember: " + groupId + " " + userId);
			}
			return member;
		} catch (RuntimeException e) {
			logger.error("getGroupMember: " + e + " " + groupId + " " + userId);
			return null;
		}

	}

	@Override
	@Cacheable(value = CACHE_GROUP_MEMBERS, key = "#groupId + ':' + #userId")
	public boolean isMember(String groupId, String userId, UserOptions options) {
		return getMember(groupId, userId, options) != null;
	}

	@Override
	public Page<Group> listGroupsForUser(String userId, GroupFilter filter, Pageable pageable) {
		try {
			return client.listGroupsForUser(userId, filter, pageable);
		} catch (RuntimeException e) {
			logger.error("listGroupsForUser: " + e + " " + userId + " " + filter + " " + pageable);
			return null;
		}
	}
	
	@Override
	public List<Group> listLocalGroupsForUser(String username, GroupFilter filter, boolean remote) {
		if (username==null) {
			if (SecurityUtil.isAnonymous()) {
				logger.warn("getGroupsIds: anonymous");
				return null;
			}
			username = SecurityUtil.getPrincipalName();
		}
		if (username==null) {
			logger.warn("getGroupsIds: missing username");
			return null;
		}
		String principalName = SecurityUtil.getPrincipalName();
		if (principalName!=null && username.equals(principalName)) {
			User user = SsoClient.getPrincipalUser();
			if (user!=null) {
				return Member.getGroups(user.getMembership());
			}
		}
		if (remote) {
			Page<Group> page = listGroupsForUser(username, filter, new PageRequest(0, Integer.MAX_VALUE));
			if (page!=null) {
				return page.getContent();
			}
		}
		return null;
	}

	@Override
	public Page<Group> listSubGroups(String groupId, boolean direct, GroupFilter filter, Pageable pageable) {
		try {
			return client.listSubGroups(groupId, direct, filter, pageable);
		} catch (RuntimeException e) {
			logger.error("listSubGroups: " + e + " " + groupId + " " + filter + " " + direct + " " + pageable);
			return null;
		}
	}

	@Override
	public Integer countSubGroups(String groupId, boolean direct, GroupFilter filter) {
		try {
			return client.countSubGroups(groupId, direct, filter);
		} catch (RuntimeException e) {
			logger.error("countSubGroups: " + e + " " + groupId + " " + filter + " " + direct);
			return null;
		}
	}


	@Override
	public void onGroupUpdate(String id, Map<String, Object> details) {
		if (id == null || details == null) {
			return;
		}
		try {
			Cache cache = getGroupCache();
			if (cache != null) {
				Group group = (Group) cache.get(id);
				if (group != null) {
					// group.update(details); //TODO
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("onGroupUpdate: " + e);
		}
	}

	@Override
	public boolean isMember(String userId,  UserOptions options, String... groups) {
		if (groups != null) {
			for (String groupId : groups) {
				if (groupId==null || groupId.isEmpty()) {
					continue;
				}
				if (isMember(userId, groupId, options)) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public void clearCache() {
		Cache cache = getGroupCache();
		if (cache != null) {
			cache.clear();
		}
	}

	@Override
	public Cache getGroupCache() {
		Cache cache = cacheManager.getCache(CACHE_GROUP);
		return cache;
	}

	@Override
	public Cache getGroupMemberCountCache() {
		Cache cache = cacheManager.getCache(CACHE_GROUP_MEMBER_COUNT);
		return cache;
	}

	@Override
	public Cache getGroupMembersCache() {
		Cache cache = cacheManager.getCache(CACHE_GROUP_MEMBER);
		return cache;
	}

	@Override
	public void onGroupMemberUpdate(String id, String userId) {
		Cache cache = getGroupMembersCache();
		cache.evict(id + ":" + userId);
		Cache cache2 = getGroupMemberCountCache();
		cache2.evict(id);
	}

	@EventListener
	@Override
	public void onEvent(ApplicationEvent event) {
		if (!(event instanceof PayloadApplicationEvent<?>)) {
			return;
		}
		Group group = getNotificationSource(((PayloadApplicationEvent<?>)event).getPayload(), Group.class);
		logger.debug("onEvent:" + group + " " + ((PayloadApplicationEvent<?>)event).getPayload());
		if (group!=null) {
			evictCaches(group);				
		}		
	}

	private void evictCaches(Group group) {
		Cache cache = getGroupCache();
		if (cache!=null && group.getUuid()!=null) {
			evictCaches(group.getUuid());
		}
	}

	private void evictCaches(String groupId) {
		if (groupId==null) {
			return;
		}
		Cache cache = getGroupCache();
		if (cache!=null) {
			cache.evict(groupId);
			cache.evict(makeKey(groupId, true, true, true));
			cache.evict(makeKey(groupId, false, false, false));
			cache.evict(makeKey(groupId, true, false, false));			
			cache.evict(makeKey(groupId, true, true, false));
		}
		Cache cache2 = getGroupMembersCache();
		if (cache2!=null) {
			cache2.evict(groupId);
		}
		Cache cache3 = getGroupMemberCountCache();
		if (cache3!=null) {
			cache3.evict(groupId);
		}
	}


}
