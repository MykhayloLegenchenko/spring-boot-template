package com.softreactor.template.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contains home actions.
 */
@Controller
public class HomeController {
	/**
	 * Home action.
	 */
	@RequestMapping(value = "/")
	public String home(Authentication auth, Model model) {
		if (auth != null) {
			return "forward:/dashboard";
		}

		return "home";
	}

}
