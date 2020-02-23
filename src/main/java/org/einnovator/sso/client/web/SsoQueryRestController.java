package org.einnovator.sso.client.web;

import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.model.Group;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.GroupFilter;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.einnovator.util.PageOptions;
import org.einnovator.util.PageUtil;
import org.einnovator.util.web.ControllerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SsoQueryRestController extends ControllerBase {

	public static final Integer DEFAULT_PAGE_SIZE = 20;

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserManager userManager;

	@Autowired
	private GroupManager groupManager;

	@GetMapping("/user")
	@ResponseBody
	public Page<User> users(UserFilter filter, PageOptions options, Principal principal) {
		
		Page<User> page = userManager.listUsers(filter, options.toPageRequest(), null);
		
		if (logger.isDebugEnabled()) {
			logger.debug(format("users:", PageUtil.toString(page), filter, options));
		}
		return page;
    }
	
	@GetMapping("/group/{groupId}")
	@ResponseBody
	public Page<Member> members(@PathVariable String groupId, MemberFilter filter, PageOptions options, Principal principal) {
		
		Page<Member> page =  groupManager.listMembers(groupId, filter,  options.toPageRequest(), null);
		
		if (logger.isDebugEnabled()) {
			logger.debug(format("members:", PageUtil.toString(page), filter, options));
		}
		return page;
    }
	
	@GetMapping("/group")
	@ResponseBody
	public Page<Group> groups(Principal principal, GroupFilter filter, PageOptions options) {

		Page<Group> page = groupManager.listGroups(filter, options.toPageRequest(), null);
		
		if (logger.isDebugEnabled()) {
			logger.debug(format("groups:", PageUtil.toString(page), filter, options));
		}
		return page;
	}
	
}
