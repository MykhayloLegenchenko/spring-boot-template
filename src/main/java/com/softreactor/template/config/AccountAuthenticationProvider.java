package com.softreactor.template.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.softreactor.template.domain.account.User;
import com.softreactor.template.service.AccountService;

/**
 * AccountAuthenticationProvider class.
 * Implements authentication provider based on account service.
 */
public class AccountAuthenticationProvider implements AuthenticationProvider {

	private final AccountService accountService;

	/**
	 * Class constructor.
	 */
	public AccountAuthenticationProvider(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		User user = accountService.findUserByEmail(email);
		if (user == null) {
			throw new BadCredentialsException("error.incorrectLoginOrPassword");
		}

		String password = authentication.getCredentials().toString();
		if (!accountService.checkCredentials(user, password)) {
			throw new BadCredentialsException("error.incorrectLoginOrPassword");
		}

		AccountUserDetails userDetails = new AccountUserDetails(user, accountService.findUserRoles(user.getId()));
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password,
				userDetails.getAuthorities());
		authToken.setDetails(userDetails);
		return authToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
