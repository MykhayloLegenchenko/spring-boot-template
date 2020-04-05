package com.softreactor.template.domain.jpa.account;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softreactor.template.domain.account.RememberMeToken;
import com.softreactor.template.domain.account.RememberMeTokenRepository;

/**
 * Remember me token repository JPA interface.
 */
public interface RememberMeTokenRepositoryJpa extends 
		RememberMeTokenRepository,
		JpaRepository<RememberMeTokenEntity, UUID> {

	@Override
	default RememberMeToken createObject() {
		return new RememberMeTokenEntity();
	}

	@Override
	default void persist(RememberMeToken entity) {
		saveAndFlush((RememberMeTokenEntity) entity);
	}

	@Override
	default void updateToken(UUID id, UUID tokenValue, Instant lastUsed) {
		findById(id).ifPresent(token -> {
			token.setToken(tokenValue);
			token.setDate(lastUsed);
			saveAndFlush(token);
		});
	}

	@Override
	void removeByUsername(String username);
}
