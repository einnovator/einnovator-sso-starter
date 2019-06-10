package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.einnovator.util.model.Address;
import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.ToStringCreator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group extends EntityBase {

	private String name;

	private GroupType type;

	private String email;

	private String email2;

	private String owner;

	private String parentId;

	private String rootId;
	
	private Phone phone;

	private Phone phone2;
	
	private Phone fax;

	private String avatar;

	private String cover;

	private String cover2;

	private String description;

	private String website;

	private String social;

	private String social2;

	private Address address;
	
	private Long memberCount;
	
	private List<Group> groups;

	private Integer level;

	private Integer groupCount;

	private Integer groupCount2;

	private Integer subLevelCount;
	
	private Boolean authenticated;

	private Integer onboarding;
	
	private Map<String, Object> profile = new LinkedHashMap<>();


	public Group() {
	}

	public Group(Group group) {
		super(group);
		if (group.getGroups()!=null) {
			groups = new ArrayList<>(group.getGroups());
		}
	}

	public Group(Object obj) {
		super(obj);
	}

	public Group(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public Long getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Long memberCount) {
		this.memberCount = memberCount;
	}
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public void addGroup(Group group) {
		if (this.groups == null) {
			this.groups = new ArrayList<>();
		}
		this.groups.add(group);
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	public Integer getGroupCount2() {
		return groupCount2;
	}

	public void setGroupCount2(Integer groupCount2) {
		this.groupCount2 = groupCount2;
	}

	public Integer getSubLevelCount() {
		return subLevelCount;
	}

	public void setSubLevelCount(Integer subLevelCount) {
		this.subLevelCount = subLevelCount;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public Phone getPhone2() {
		return phone2;
	}

	public void setPhone2(Phone phone2) {
		this.phone2 = phone2;
	}

	public Phone getFax() {
		return fax;
	}

	public void setFax(Phone fax) {
		this.fax = fax;
	}

	public String getCover2() {
		return cover2;
	}

	public void setCover2(String cover2) {
		this.cover2 = cover2;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	public String getSocial2() {
		return social2;
	}

	public void setSocial2(String social2) {
		this.social2 = social2;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Integer getOnboarding() {
		return onboarding;
	}

	public void setOnboarding(Integer onboarding) {
		this.onboarding = onboarding;
	}

	public Map<String, Object> getProfile() {
		return profile;
	}

	public void setProfile(Map<String, Object> profile) {
		this.profile = profile;
	}

	public Object getProfileAttribute(String name) {
		if (profile!=null) {
			return profile.get(name);
		}
		return null;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator builder) {
		return builder
				.append("name", name)
				.append("type", type)
				.append("parentId", parentId)
				.append("rootId", rootId)
				.append("owner", owner)
				.append("email", email)
				.append("email2", email2)
				.append("phone", phone)
				.append("phone2", phone2)
				.append("onboarding", onboarding)
				.append("authenticated", authenticated)
				.append("avatar", avatar)
				.append("cover", cover)
				.append("cover2", cover2)
				.append("description", description)
				.append("profile", profile)
				.append("address", address)
				.append("website", website)
				.append("social", social)
				.append("social2", social2)
				.append("memberCount", memberCount)
				;
	}

	public static Group find(String id, List<? extends Group> groups) {
		return find(id, groups, false);
	}

	public static Group find(String id, List<? extends Group> groups, boolean recurse) {
		if (groups != null && id != null) {
			for (Group group : groups) {
				if (id.equals(group.getUuid())) {
					return group;
				}
				if (recurse && group.getGroups()!=null) {
					Group group2 = find(id, group.getGroups(), true);
					if (group2!=null) {
						return group2;
					}
				}
			}
		}
		return null;
	}

	public static Group find(String id, Group group) {
		if (group != null && id != null) {
			if (id.equals(group.getUuid())) {
				return group;
			}
			if (group.getGroups()!=null) {
				Group group2 = find(id, group.getGroups(), true);
				if (group2!=null) {
					return group2;
				}
			}
		}
		return null;
	}

	public static List<Group> buildTree(List<? extends Group> groups) {
		List<Group> roots = new ArrayList<>();
		for (Group group : groups) {
			if (group.getParentId() == null && group.getRootId()==null) {
				roots.add(group);
			} else if (group.getParentId()!=null) {
				Group parent = Group.find(group.getParentId(), groups);
				if (parent != null) {
					parent.addGroup(group);
				}
			} else {
				Group root = Group.find(group.getRootId(), groups);
				if (root != null) {
					root.addGroup(group);
				}				
			}
		}
		return roots;
	}


	public static List<Group> flat(List<? extends Group> groups, boolean prune) {
		if (groups==null) {
			return null;
		}
		List<Group> groups2 = new ArrayList<>();
		for (Group group: groups) {
			groups2.add(group);
			if (group.getGroups()!=null) {
				for (Group group2: group.getGroups()) {
					groups2.add(group2);
					if (group2.getGroups()!=null)  {
						groups2.addAll(flat(group2.getGroups()));						
					}
					if (prune) {
						group2.setGroups(null);
					}
				}
				if (prune) {
					group.setGroups(null);
				}
			}
		}
		return groups2;
	}

	public static List<Group> flat(List<? extends Group> groups) {
		return flat(groups, false);
	}
	
	public static <T extends Group> List<T> flatTrees(List<T> groups) {
		return flatTrees(groups, false);
	}
	
	public static <T extends Group> List<T> flatTrees(List<T> groups, boolean prune) {
		if (groups==null) {
			return null;
		}
		for (Group group: groups) {
			group.setGroups(Group.flat(group.getGroups(), prune));
		}
		return groups;
	}

	public static <T extends Group> List<T> prune(List<T> groups) {
		if (groups!=null) {
			for (Group group: groups) {
				group.setGroups(null);
			}			
		}
		return groups;
	}

	public static <T extends Group> List<T> prune2(List<T> groups) {
		if (groups!=null) {
			for (Group group: groups) {
				if (group.getGroups()!=null) {
					for (Group group2: group.getGroups()) {
						group2.setGroups(null);
					}
				}
			}			
		}
		return groups;
	}

	public static List<Group> copy(List<? extends Group> groups, boolean recurse) {
		if (groups==null) {
			return null;
		}
		List<Group> groups2 = new ArrayList<>();
		for (Group group: groups) {
			groups2.add(new Group(group));
			if (recurse && group.getGroups()!=null) {
				group.setGroups(copy(group.getGroups(), true));
			}
		}
		return groups2;
	}

	
	public static void printTree(Group group) {
		printTree(group, 0);
	}

	public static void printTree(Group group, int n) {	
		System.out.println(String.format("%" + (n+1) + "s%s", "", group));
		if (group.getGroups()!=null) {
			for (Group group2: group.getGroups()) {
				printTree(group2, n+1);
			}
		}
	}
	
	public static void printTrees(List<? extends Group> groups) {
		if (groups!=null) {
			for (Group group: groups) {
				printTree(group);
			}			
		}
	}
	
	public static List<Group> ancestors(Group group, Group root) {
		return ancestors(group, root, new ArrayList<>());
	}

	public static List<Group> ancestors(Group group, Group root, List<Group> ancestors) {
		ancestors.add(root);
		if (root.getGroups()!=null) {
			for (Group group2: root.getGroups()) {
				if (group2.getUuid().equals(group.getUuid())) {
					return ancestors;
				}
				if (ancestors(group, group2, ancestors)!=null) {
					return ancestors;
				}
			}
		}
		ancestors.remove(ancestors.size()-1);
		return null;
	}
	
	public static <T extends Group> List<T> filter(List<T> groups, boolean recurse, boolean inclusive, Predicate<Group> filter) {
		if (groups==null) {
			return null;
		}
		List<T> groups2 = new ArrayList<>();
		for (T group: groups) {
			if (filter.test(group)) {
				groups2.add(group);
				if (recurse && !inclusive) {
					group.setGroups(Group.filter(group.getGroups(), true, false, filter));
				}
			}
			if (recurse && inclusive) {
				group.setGroups(Group.filter(group.getGroups(), true, true, filter));
			}
		}
		return groups2;
	}


}
