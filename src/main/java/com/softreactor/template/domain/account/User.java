package com.softreactor.template.domain.account;

import java.time.Instant;

import com.softreactor.template.domain.BaseObject;

/**
 * User entity interface.
 */
public interface User extends BaseObject {

	Instant getCreated();
	void setCreated(Instant created);

	boolean IsEnabled();
	void setEnabled(boolean enabled);
	
	String getEmail();

	void setEmail(String email);

	String getPassword();

	void setPassword(String password);
}

