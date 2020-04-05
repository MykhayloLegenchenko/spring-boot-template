package com.softreactor.template.web;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.softreactor.template.service.AccountService;

/**
 * Contains actions for user registration.
 */
@Controller
@PreAuthorize("isAnonymous()")
public class RegistrationController {
	private final AccountService accountService;

	/**
	 * Class constructor.
	 */
	public RegistrationController(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * Registration action.
	 */
	@GetMapping(value = "/register")
	public String register(@ModelAttribute("registationForm") RegistrationForm registationForm) {
		return "register";
	}

	/**
	 * Process registration action.
	 */
	@PostMapping(value = "/register")
	public String registerSubmit(@Valid @ModelAttribute("registationForm") RegistrationForm registationForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		if (!registationForm.getPassword().equals(registationForm.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "error.passwordConfirmation");
			return "register";
		}

		try {
			accountService.registersUser(registationForm.getEmail(), registationForm.getPassword());
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("email", "error.emailAlreadyRegistered");
			return "register";
		}

		return "redirect:/";
	}

}
