package com.softreactor.template.domain.jpa.account;

/**
 * Queries class.
 * Contains SQL queries.
 */
abstract class Queries {

	static final String FIND_USER_ROLES = "SELECT r.name FROM User u JOIN u.roles r WHERE u.id = ?1";
	
	static final String ADD_USER_ROLE = "INSERT INTO `user_role` (`user_id`, `role_id`)"
			+ " VALUES(?1, (SELECT id FROM `role` WHERE `name` = ?2 LIMIT 1))";

	static final String REMOVE_USER_ROLES = "DELETE FROM user_role WHERE user_id = ?1";

}
