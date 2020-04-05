package com.softreactor.template.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contains actions for login page.
 */
@Controller
@PreAuthorize("isAnonymous()")
public class LoginController extends FormController {

	/**
	 * Login action.
	 */
	@RequestMapping({ "/login", "/processLogin" })
	public String login(HttpServletRequest request, LoginForm loginForm, BindingResult bindingResult) {
		AuthenticationException exception = (AuthenticationException) request
				.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (exception != null) {
			bindingResult.rejectValue("email", exception.getMessage());
		}

		return "login";
	}
}
