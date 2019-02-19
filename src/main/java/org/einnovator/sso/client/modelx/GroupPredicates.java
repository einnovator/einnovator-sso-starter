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
	
	public static final Predicate<Group> ORGANIZATION = new GroupTypePredicate(GroupType.ORGANIZATION); 
	public static final Predicate<Group> OPERATION = new GroupTypePredicate(GroupType.OPERATION); 
	public static final Predicate<Group> DEPARTMENT = new GroupTypePredicate(GroupType.DEPARTMENT); 
	public static final Predicate<Group> TEAM = new GroupTypePredicate(GroupType.TEAM); 

}
