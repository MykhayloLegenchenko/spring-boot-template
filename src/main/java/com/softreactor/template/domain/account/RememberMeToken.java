package com.softreactor.template.domain.account;

import java.time.Instant;
import java.util.UUID;

import com.softreactor.template.domain.BaseObject;

/**
 * Remember me token entity interface.
 */
public interface RememberMeToken extends BaseObject {

	String getUsername();

	void setUsername(String username);

	UUID getToken();

	void setToken(UUID token);

	Instant getDate();

	void setDate(Instant date);
}
