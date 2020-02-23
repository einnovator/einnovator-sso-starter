package org.einnovator.sso.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.einnovator.util.model.Address;
import org.einnovator.util.model.EntityBase;
import org.einnovator.util.model.Phone;
import org.einnovator.util.model.ToStringCreator;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
	
	private Map<String, Object> profile;


	/**
	 * Create instance of {@code Group}.
	 *
	 */
	public Group() {
	}

	/**
	 * Create instance of {@code Group}.
	 *
	 * @param group a prototype group
	 */
	public Group(Group group) {
		super(group);
		if (group.groups()!=null) {
			groups = new ArrayList<>(group.groups());
		}
	}

	/**
	 * Create instance of {@code Group}.
	 *
	 * @param prototype a prototype
	 */
	public Group(Object prototype) {
		super(prototype);
	}

	/**
	 * Create instance of {@code Group}.
	 *
	 * @param uuid the uuid
	 */
	public Group(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Get the value of property {@code name}.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the value of property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public GroupType getType() {
		return type;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the value of property type
	 */
	public void setType(GroupType type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code email}.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the value of property {@code email}.
	 *
	 * @param email the value of property email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the value of property {@code email2}.
	 *
	 * @return the email2
	 */
	public String getEmail2() {
		return email2;
	}

	/**
	 * Set the value of property {@code email2}.
	 *
	 * @param email2 the value of property email2
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/**
	 * Get the value of property {@code owner}.
	 *
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Set the value of property {@code owner}.
	 *
	 * @param owner the value of property owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Get the value of property {@code parentId}.
	 *
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Set the value of property {@code parentId}.
	 *
	 * @param parentId the value of property parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Get the value of property {@code rootId}.
	 *
	 * @return the rootId
	 */
	public String getRootId() {
		return rootId;
	}

	/**
	 * Set the value of property {@code rootId}.
	 *
	 * @param rootId the value of property rootId
	 */
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	/**
	 * Get the value of property {@code phone}.
	 *
	 * @return the phone
	 */
	public Phone getPhone() {
		return phone;
	}

	/**
	 * Set the value of property {@code phone}.
	 *
	 * @param phone the value of property phone
	 */
	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	/**
	 * Get the value of property {@code phone2}.
	 *
	 * @return the phone2
	 */
	public Phone getPhone2() {
		return phone2;
	}

	/**
	 * Set the value of property {@code phone2}.
	 *
	 * @param phone2 the value of property phone2
	 */
	public void setPhone2(Phone phone2) {
		this.phone2 = phone2;
	}

	/**
	 * Get the value of property {@code fax}.
	 *
	 * @return the fax
	 */
	public Phone getFax() {
		return fax;
	}

	/**
	 * Set the value of property {@code fax}.
	 *
	 * @param fax the value of property fax
	 */
	public void setFax(Phone fax) {
		this.fax = fax;
	}

	/**
	 * Get the value of property {@code avatar}.
	 *
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Set the value of property {@code avatar}.
	 *
	 * @param avatar the value of property avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Get the value of property {@code cover}.
	 *
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * Set the value of property {@code cover}.
	 *
	 * @param cover the value of property cover
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}

	/**
	 * Get the value of property {@code cover2}.
	 *
	 * @return the cover2
	 */
	public String getCover2() {
		return cover2;
	}

	/**
	 * Set the value of property {@code cover2}.
	 *
	 * @param cover2 the value of property cover2
	 */
	public void setCover2(String cover2) {
		this.cover2 = cover2;
	}

	/**
	 * Get the value of property {@code description}.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the value of property {@code website}.
	 *
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Set the value of property {@code website}.
	 *
	 * @param website the value of property website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Get the value of property {@code social}.
	 *
	 * @return the social
	 */
	public String getSocial() {
		return social;
	}

	/**
	 * Set the value of property {@code social}.
	 *
	 * @param social the value of property social
	 */
	public void setSocial(String social) {
		this.social = social;
	}

	/**
	 * Get the value of property {@code social2}.
	 *
	 * @return the social2
	 */
	public String getSocial2() {
		return social2;
	}

	/**
	 * Set the value of property {@code social2}.
	 *
	 * @param social2 the value of property social2
	 */
	public void setSocial2(String social2) {
		this.social2 = social2;
	}

	/**
	 * Get the value of property {@code address}.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Set the value of property {@code address}.
	 *
	 * @param address the value of property address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Get the value of property {@code memberCount}.
	 *
	 * @return the memberCount
	 */
	public Long getMemberCount() {
		return memberCount;
	}

	/**
	 * Set the value of property {@code memberCount}.
	 *
	 * @param memberCount the value of property memberCount
	 */
	public void setMemberCount(Long memberCount) {
		this.memberCount = memberCount;
	}

	/**
	 * Get the value of property {@code groups}.
	 *
	 * @return the groups
	 */
	public List<Group> groups() {
		return groups;
	}


	/**
	 * Get the value of property {@code groups}.
	 *
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}
	
	/**
	 * Set the value of property {@code groups}.
	 *
	 * @param groups the value of property groups
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	/**
	 * Get the value of property {@code level}.
	 *
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * Set the value of property {@code level}.
	 *
	 * @param level the value of property level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * Get the value of property {@code groupCount}.
	 *
	 * @return the groupCount
	 */
	public Integer getGroupCount() {
		return groupCount;
	}

	/**
	 * Set the value of property {@code groupCount}.
	 *
	 * @param groupCount the value of property groupCount
	 */
	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	/**
	 * Get the value of property {@code groupCount2}.
	 *
	 * @return the groupCount2
	 */
	public Integer getGroupCount2() {
		return groupCount2;
	}

	/**
	 * Set the value of property {@code groupCount2}.
	 *
	 * @param groupCount2 the value of property groupCount2
	 */
	public void setGroupCount2(Integer groupCount2) {
		this.groupCount2 = groupCount2;
	}

	/**
	 * Get the value of property {@code subLevelCount}.
	 *
	 * @return the subLevelCount
	 */
	public Integer getSubLevelCount() {
		return subLevelCount;
	}

	/**
	 * Set the value of property {@code subLevelCount}.
	 *
	 * @param subLevelCount the value of property subLevelCount
	 */
	public void setSubLevelCount(Integer subLevelCount) {
		this.subLevelCount = subLevelCount;
	}

	/**
	 * Get the value of property {@code authenticated}.
	 *
	 * @return the authenticated
	 */
	public Boolean getAuthenticated() {
		return authenticated;
	}

	/**
	 * Set the value of property {@code authenticated}.
	 *
	 * @param authenticated the value of property authenticated
	 */
	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	/**
	 * Get the value of property {@code onboarding}.
	 *
	 * @return the onboarding
	 */
	public Integer getOnboarding() {
		return onboarding;
	}

	/**
	 * Set the value of property {@code onboarding}.
	 *
	 * @param onboarding the value of property onboarding
	 */
	public void setOnboarding(Integer onboarding) {
		this.onboarding = onboarding;
	}

	/**
	 * Get the value of property {@code profile}.
	 *
	 * @return the profile
	 */
	public Map<String, Object> getProfile() {
		return profile;
	}

	/**
	 * Set the value of property {@code profile}.
	 *
	 * @param profile the value of property profile
	 */
	public void setProfile(Map<String, Object> profile) {
		this.profile = profile;
	}
	
	// With

	/**
	 * Set the value of property {@code name}.
	 *
	 * @param name the value of property name
	 */
	public Group withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the value of property type
	 */
	public Group withType(GroupType type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the value of property {@code email}.
	 *
	 * @param email the value of property email
	 */
	public Group withEmail(String email) {
		this.email = email;
		return this;
	}

	/**
	 * Set the value of property {@code email2}.
	 *
	 * @param email2 the value of property email2
	 */
	public Group withEmail2(String email2) {
		this.email2 = email2;
		return this;
	}

	/**
	 * Set the value of property {@code owner}.
	 *
	 * @param owner the value of property owner
	 */
	public Group withOwner(String owner) {
		this.owner = owner;
		return this;
	}

	/**
	 * Set the value of property {@code parentId}.
	 *
	 * @param parentId the value of property parentId
	 */
	public Group withParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	/**
	 * Set the value of property {@code rootId}.
	 *
	 * @param rootId the value of property rootId
	 */
	public Group withRootId(String rootId) {
		this.rootId = rootId;
		return this;
	}

	/**
	 * Set the value of property {@code phone}.
	 *
	 * @param phone the value of property phone
	 */
	public Group withPhone(Phone phone) {
		this.phone = phone;
		return this;
	}

	/**
	 * Set the value of property {@code phone2}.
	 *
	 * @param phone2 the value of property phone2
	 */
	public Group withPhone2(Phone phone2) {
		this.phone2 = phone2;
		return this;
	}

	/**
	 * Set the value of property {@code fax}.
	 *
	 * @param fax the value of property fax
	 */
	public Group withFax(Phone fax) {
		this.fax = fax;
		return this;
	}

	/**
	 * Set the value of property {@code avatar}.
	 *
	 * @param avatar the value of property avatar
	 */
	public Group withAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	 * Set the value of property {@code cover}.
	 *
	 * @param cover the value of property cover
	 */
	public Group withCover(String cover) {
		this.cover = cover;
		return this;
	}

	/**
	 * Set the value of property {@code cover2}.
	 *
	 * @param cover2 the value of property cover2
	 */
	public Group withCover2(String cover2) {
		this.cover2 = cover2;
		return this;
	}

	/**
	 * Set the value of property {@code description}.
	 *
	 * @param description the value of property description
	 */
	public Group withDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Set the value of property {@code website}.
	 *
	 * @param website the value of property website
	 */
	public Group withWebsite(String website) {
		this.website = website;
		return this;
	}

	/**
	 * Set the value of property {@code social}.
	 *
	 * @param social the value of property social
	 */
	public Group withSocial(String social) {
		this.social = social;
		return this;
	}

	/**
	 * Set the value of property {@code social2}.
	 *
	 * @param social2 the value of property social2
	 */
	public Group withSocial2(String social2) {
		this.social2 = social2;
		return this;
	}

	/**
	 * Set the value of property {@code address}.
	 *
	 * @param address the value of property address
	 */
	public Group withAddress(Address address) {
		this.address = address;
		return this;
	}

	/**
	 * Set the value of property {@code memberCount}.
	 *
	 * @param memberCount the value of property memberCount
	 */
	public Group withMemberCount(Long memberCount) {
		this.memberCount = memberCount;
		return this;
	}

	/**
	 * Set the value of property {@code groups}.
	 *
	 * @param groups the value of property groups
	 */
	public Group withGroups(List<Group> groups) {
		this.groups = groups;
		return this;
	}

	/**
	 * Set the value of property {@code level}.
	 *
	 * @param level the value of property level
	 */
	public Group withLevel(Integer level) {
		this.level = level;
		return this;
	}

	/**
	 * Set the value of property {@code groupCount}.
	 *
	 * @param groupCount the value of property groupCount
	 */
	public Group withGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
		return this;
	}

	/**
	 * Set the value of property {@code groupCount2}.
	 *
	 * @param groupCount2 the value of property groupCount2
	 */
	public Group withGroupCount2(Integer groupCount2) {
		this.groupCount2 = groupCount2;
		return this;
	}

	/**
	 * Set the value of property {@code subLevelCount}.
	 *
	 * @param subLevelCount the value of property subLevelCount
	 */
	public Group withSubLevelCount(Integer subLevelCount) {
		this.subLevelCount = subLevelCount;
		return this;
	}

	/**
	 * Set the value of property {@code authenticated}.
	 *
	 * @param authenticated the value of property authenticated
	 */
	public Group withAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
		return this;
	}

	/**
	 * Set the value of property {@code onboarding}.
	 *
	 * @param onboarding the value of property onboarding
	 */
	public Group withOnboarding(Integer onboarding) {
		this.onboarding = onboarding;
		return this;
	}

	/**
	 * Set the value of property {@code profile}.
	 *
	 * @param profile the value of property profile
	 */
	public Group withProfile(Map<String, Object> profile) {
		this.profile = profile;
		return this;
	}

	public void addGroup(Group group) {
		if (this.groups == null) {
			this.groups = new ArrayList<>();
		}
		group.parentId = uuid;
		if (group.rootId==null) {
			group.rootId = uuid;
		}
		this.groups.add(group);
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
				if (recurse && group.groups()!=null) {
					Group group2 = find(id, group.groups(), true);
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
			if (group.groups()!=null) {
				Group group2 = find(id, group.groups(), true);
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
				if (Group.findByUuid(group.getUuid(), roots)==null) {
					roots.add(group);	
				}
			} else if (group.getParentId()!=null) {
				Group parent = Group.find(group.getParentId(), groups);
				if (parent != null) {
					if (Group.findByUuid(group.getUuid(), parent.getGroups())==null) {
						parent.addGroup(group);
					}
				}
			} else {
				Group root = Group.find(group.getRootId(), groups);
				if (root != null) {
					if (Group.findByUuid(group.getUuid(), root.getGroups())==null) {
						root.addGroup(group);
					}
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
			if (group.groups()!=null) {
				for (Group group2: group.groups()) {
					groups2.add(group2);
					if (group2.groups()!=null)  {
						groups2.addAll(flat(group2.groups()));						
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
			group.setGroups(Group.flat(group.groups(), prune));
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
				if (group.groups()!=null) {
					for (Group group2: group.groups()) {
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
			if (recurse && group.groups()!=null) {
				group.setGroups(copy(group.groups(), true));
			}
		}
		return groups2;
	}

	
	public static void printTree(Group group) {
		printTree(group, 0);
	}

	public static void printTree(Group group, int n) {	
		System.out.println(String.format("%" + (n+1) + "s%s", "", group));
		if (group.groups()!=null) {
			for (Group group2: group.groups()) {
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
		if (root.groups()!=null) {
			for (Group group2: root.groups()) {
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
					group.setGroups(Group.filter(group.groups(), true, false, filter));
				}
			}
			if (recurse && inclusive) {
				group.setGroups(Group.filter(group.groups(), true, true, filter));
			}
		}
		return groups2;
	}

	public static List<String> getUsernames(Iterable<Group> groups) {
		List<String> names = new ArrayList<>();
		if (groups!=null) {
			for (Group group: groups) {
				if (StringUtils.hasText(group.getName())) {
					names.add(group.getName());
				}
			}			
		}
		return names;
	}
}
