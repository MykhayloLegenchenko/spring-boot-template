package com.softreactor.template.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Model for login form.
 */
public class LoginForm {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	private boolean rememberMe;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememeberMe) {
		this.rememberMe = rememeberMe;
	}

}
