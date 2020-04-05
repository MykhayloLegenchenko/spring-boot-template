package com.softreactor.template.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softreactor.template.domain.SearchFilter;
import com.softreactor.template.service.AccountService;
import com.softreactor.template.web.MvcUtils.JsonSearchResult;

/**
 * Contains admmin interface actions. 
 */
@Controller
@PreAuthorize("hasRole('admin')")
@RequestMapping("/admin")
public class AdminController {
	private final AccountService accountService;
	
	public AdminController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping("/users")
	public String users(Model model) {
		return "admin/users";
	}

	@PostMapping("/users/search")
	@ResponseBody
	public JsonSearchResult seachUsers(HttpServletRequest request) {
		SearchFilter filter = MvcUtils.getSearchFilter(request);
		return MvcUtils.jsonSearchResult(
				request,
				filter, 
				accountService.searchUsers(filter),
				user -> new Object[] {
					MvcUtils.formatDateTime(user.getCreated()),
					user.getEmail(),
					String.join(", ", accountService.findUserRoles(user.getId()))
			});
	}
}
