package com.softreactor.template.config;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.softreactor.template.domain.account.User;

/**
 * AccountUserDetails class.
 * Implements user details based on domain user.
 */
public class AccountUserDetails implements UserDetails, CredentialsContainer {
	private static final long serialVersionUID = 1L;
	private final User user;
	private final Set<GrantedAuthority> authorities;

	/**
	 * Class constructor.
	 */
	public AccountUserDetails(User user, List<String> roles) {
		this.user = user;
		this.authorities = roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toSet());
	}

	@Override
	public void eraseCredentials() {
		user.setPassword(null);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return user;
	}

}