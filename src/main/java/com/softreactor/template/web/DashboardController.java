package com.softreactor.template.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DashboardController class.
 * Contains dashboard actions.
 */
@Controller
@PreAuthorize("isAuthenticated()")
public class DashboardController {
	/**
	 * Home action.
	 */
	@RequestMapping(value = "/dashboard")
	public String dashboard() {
		return "dashboard";
	}
}
