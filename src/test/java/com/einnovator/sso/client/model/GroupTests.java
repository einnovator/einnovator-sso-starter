/**
 * 
 */
package com.einnovator.sso.client.model;

import java.util.Arrays;
import java.util.List;

import org.einnovator.sso.client.model.Group;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class GroupTests {

	@Test
	public void test() {
		Group group1 = (Group)new Group().withName("g1").withUuid("1");
		Group group2 = (Group)new Group().withName("g2").withUuid("2");
		Group group3 = (Group)new Group().withName("g3").withUuid("3");
		Group group4 = (Group)new Group().withName("g4").withUuid("4");
		Group group5 = (Group)new Group().withName("g5").withUuid("5");
		group1.addGroup(group2);
		group2.addGroup(group3);
		group1.addGroup(group4);
		List<Group> groups = Arrays.asList(group1, group2, group3, group4, group5);
		List<Group> trees = Group.buildTree(groups);
		Group.printTrees(trees);
	}

}
