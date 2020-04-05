package com.softreactor.template.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.softreactor.template.domain.DomainUtils;
import com.softreactor.template.domain.SearchFilter;
import com.softreactor.template.domain.SearchResult;
import com.softreactor.template.domain.account.RememberMeToken;
import com.softreactor.template.domain.account.RememberMeTokenRepository;
import com.softreactor.template.domain.account.Role;
import com.softreactor.template.domain.account.RoleRepository;
import com.softreactor.template.domain.account.User;
import com.softreactor.template.domain.account.UserRepository;

/**
 * Account service class.
 */
@Service
public class AccountService implements PersistentTokenRepository {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final RememberMeTokenRepository rememberMeTokenRepository;
	private final SCryptPasswordEncoder passwordEncoder;

	/**
	 * Class constructor.
	 */
	public AccountService(UserRepository userRepository, RoleRepository roleRepository,
			RememberMeTokenRepository rememberMeTokenRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.rememberMeTokenRepository = rememberMeTokenRepository;
		this.passwordEncoder = new SCryptPasswordEncoder();
	}

	/**
	 * @see PersistentTokenRepository#createNewToken(PersistentRememberMeToken)
	 */
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		RememberMeToken tokenObj = rememberMeTokenRepository.createObject();
		tokenObj.setId(DomainUtils.uuidFromString(token.getSeries()));
		tokenObj.setUsername(token.getUsername());
		tokenObj.setToken(DomainUtils.uuidFromString(token.getTokenValue()));
		tokenObj.setDate(token.getDate().toInstant());
		rememberMeTokenRepository.persist(tokenObj);
	}

	/**
	 * @see PersistentTokenRepository#updateToken(String, String, Date)
	 */
	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		rememberMeTokenRepository.updateToken(
				DomainUtils.uuidFromString(series),
				DomainUtils.uuidFromString(tokenValue),
				lastUsed.toInstant());
	}

	/**
	 * @see PersistentTokenRepository#getTokenForSeries(String)
	 */
	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		RememberMeToken tokenObj = rememberMeTokenRepository
				.findById(DomainUtils.uuidFromString(seriesId))
				.orElse(null);

		if (tokenObj == null) {
			return null;
		}

		return new PersistentRememberMeToken(tokenObj.getUsername(),
				DomainUtils.uuidToString(tokenObj.getId()),
				DomainUtils.uuidToString(tokenObj.getToken()),
				Date.from(tokenObj.getDate()));
	}

	/**
	 * @see PersistentTokenRepository#removeUserTokens(String)
	 */
	@Override
	public void removeUserTokens(String username) {
		rememberMeTokenRepository.removeByUsername(username);
	}

	/**
	 * Checks user credentials.
	 */
	public boolean checkCredentials(User user, String presentedPassword) {
		return passwordEncoder.matches(presentedPassword.trim(), user.getPassword());
	}

	/**
	 * Finds user by email.
	 */
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email.trim());
	}

	/**
	 * Finds role names roles by user id.
	 */
	public List<String> findUserRoles(UUID userId) {
		return userRepository.findRoles(userId);
	}

	/**
	 * Registers new user.
	 */
	public User registersUser(String email, String password) {
		User user = userRepository.createObject();

		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.persist(user);

		return user;
	}

	/**
	 * Adds role to user. 
	 */
	public void addUserRole(UUID userId, String roleName) {
		userRepository.addRole(userId, roleName);
	}

	/**
	 * Searches users by filter.
	 */
	public SearchResult<? extends User> searchUsers(SearchFilter filter) {
		return userRepository.search(filter);
	}
	
	/**
	 * Creates test account data.
	 */
	public void createTestData() {
		Role adminRole = roleRepository.createObject();
		adminRole.setName("admin");
		roleRepository.persist(adminRole);

		User adminUser = registersUser("admin@test.com", "admin");
		addUserRole(adminUser.getId(), "admin");

		ArrayList<String> roles = new ArrayList<>(6);
		roles.add("admin");
		for (int i = 1; i <= 5; i++) {
			Role role = roleRepository.createObject();
			role.setName("role_" + i);
			roleRepository.persist(role);
			roles.add(role.getName());
		}

		Random random = new Random();
		for (int i = 1; i <= 20; i++) {
			User user = registersUser("user" + i +  "@test.com", "password" + i);
			roles.forEach(role -> {
				if (random.nextDouble() < .3) {
					addUserRole(user.getId(), role);
				}
			});
		}

	}

}
