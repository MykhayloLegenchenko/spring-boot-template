package com.softreactor.template.domain.account;

import java.time.Instant;
import java.util.UUID;

import com.softreactor.template.domain.BaseRepository;

/**
 * Remember me token repository interface.
 */
public interface RememberMeTokenRepository extends BaseRepository<RememberMeToken> {

	/**
	 * Updates token record by id.
	 */
	void updateToken(UUID id, UUID tokenValue, Instant lastUsed);

	/**
	 * Removes tokens by username.
	 */	
	void removeByUsername(String username);

}
