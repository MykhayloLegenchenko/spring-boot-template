package com.softreactor.template.domain.account;

import java.util.List;
import java.util.UUID;

import com.softreactor.template.domain.BaseRepository;
import com.softreactor.template.domain.SearchFilter;
import com.softreactor.template.domain.SearchResult;

/**
 * User repository interface.
 */
public interface UserRepository extends BaseRepository<User> {

	/**
	 * Searches users by filter.
	 */
	SearchResult<? extends User> search(SearchFilter filter);

	/**
	 * Finds user by email.
	 */
	User findByEmail(String email);

	/**
	 * Finds role names by user id.
	 */
	List<String> findRoles(UUID userId);

	/**
	 * Adds role to user by id.
	 */
	void addRole(UUID userId, String roleName);

	/**
	 * Remove all user roles. 
	 */
	void removeRoles(UUID userId);
}
