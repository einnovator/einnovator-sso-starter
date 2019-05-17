package org.einnovator.sso.client.web;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.einnovator.util.PageOptions;
import org.einnovator.util.PageUtil;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.User;
import org.einnovator.sso.client.modelx.MemberFilter;
import org.einnovator.sso.client.modelx.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SsoQueryRestController {

	public static final Integer DEFAULT_PAGE_SIZE = 20;

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserManager userManager;

	@Autowired
	private GroupManager groupManager;

	@GetMapping("/user")
	public Page<User> queryUsers(UserFilter filter, PageOptions options, Principal principal) {
		if (principal==null) {
			logger.error("queryUsers: " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
			return null;
		}
		Page<User> page = userManager.listUsers(filter, options.toPageRequest());

		logger.info("query: " + PageUtil.toString(page) + " " + filter + " " + options);			
		return page;
    }
	
	@GetMapping("/group/{groupId}")
	public List<User> queryGroupMembers(@PathVariable String groupId, @RequestParam String q, PageOptions options, Principal principal) {
		if (principal==null) {
			logger.error("queryGroupMembers: " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
			return null;
		}
		MemberFilter filter = new MemberFilter();
		filter.setQ(q);
		Page<Member> members =  groupManager.listMembers(groupId, filter,  options.toPageRequest());
		if (members==null || members.getContent()==null) {
			return null;
		}
		List<User> users = Member.toUsers(members.getContent());
		if (users==null) {
			logger.error("queryGroupMembers: " + HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase() + " :" + q);
			return null;
		}			
		logger.info("queryGroupMembers: " + q + " #" + users.size());

		return users;
    }
	
	@GetMapping("/query")
	public Object query(Principal principal, UserFilter filter, PageOptions options) {
		if (principal==null) {
			logger.error("query: " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
			return null;
		}
		Page<?> page = userManager.listUsers(filter, options.toPageRequest());

		logger.info("query: " + PageUtil.toString(page) + " " + filter + " " + options);			

		return page;
	}
	
}
