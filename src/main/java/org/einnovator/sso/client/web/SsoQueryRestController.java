package org.einnovator.sso.client.web;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.einnovator.util.ListOptions;
import org.einnovator.sso.client.manager.GroupManager;
import org.einnovator.sso.client.manager.UserManager;
import org.einnovator.sso.client.model.Member;
import org.einnovator.sso.client.model.User;
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

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserManager userManager;

	@Autowired
	private GroupManager groupManager;

	@GetMapping("/user")
	public Page<User> queryUsers(UserFilter filter, ListOptions options, Principal principal) {
		if (principal==null) {
			logger.error("queryUsers: " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
			return null;
		}
		Page<User> page = userManager.listUsers(filter, options.toPageRequest());

		logger.info("query: #" + (page!=null ? page.getTotalElements() : null) + " " + filter + " " + options);			
		return page;
    }
	
	@GetMapping("/group/{groupId}")
	public List<User> queryGroupMembers(@PathVariable String groupId, @RequestParam String q, ListOptions options, Principal principal) {
		if (principal==null) {
			logger.error("queryGroupMembers: " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
			return null;
		}
		UserFilter filter = new UserFilter();
		filter.setQ(q);
		Page<Member> members =  groupManager.listGroupMembers(groupId, options.toPageRequest(), filter);
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
	public Object query(Principal principal, UserFilter filter, ListOptions options) {
		if (principal==null) {
			logger.error("query: " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
			return null;
		}
		Page<?> page = userManager.listUsers(filter, options.toPageRequest());

		logger.info("query: #" + (page!=null ? page.getTotalElements() : null) + " " + filter + " " + options);			

		return page;
	}
	
}