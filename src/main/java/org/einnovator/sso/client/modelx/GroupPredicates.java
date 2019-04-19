package org.einnovator.sso.client.modelx;

import java.util.function.Predicate;

import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.GroupType;

public class GroupPredicates {
	
	public static class  GroupTypePredicate implements Predicate<Group> {
		private GroupType groupType;
		
		public GroupTypePredicate(GroupType groupType) {
			this.groupType = groupType;
		}

		@Override
		public boolean test(Group group) {
			return group!=null && groupType.equals(group.getType());
		}		
	}
	
	public static class  GroupTypesPredicate implements Predicate<Group> {
		private GroupType[] groupTypes;
		
		public GroupTypesPredicate(GroupType... groupTypes) {
			this.groupTypes = groupTypes;
		}

		@Override
		public boolean test(Group group) {
			if (group!=null) {
				for (GroupType type: groupTypes) {
					if (type.equals(group.getType())) {
						return true;
					}
				}				
			}
			return false;
		}		
	}
	
	public static final Predicate<Group> ORGANIZATION = new GroupTypePredicate(GroupType.ORGANIZATION); 
	public static final Predicate<Group> OPERATION = new GroupTypePredicate(GroupType.OPERATION); 
	public static final Predicate<Group> DEPARTMENT = new GroupTypePredicate(GroupType.DEPARTMENT); 
	public static final Predicate<Group> TEAM = new GroupTypePredicate(GroupType.TEAM); 
	public static final Predicate<Group> ORGANIZATION_OR_OPERATION = new GroupTypesPredicate(GroupType.ORGANIZATION, GroupType.OPERATION); 
	public static final Predicate<Group> ORGANIZATION_TREE = new GroupTypesPredicate(GroupType.ORGANIZATION, GroupType.OPERATION, GroupType.TEAM); 

}
