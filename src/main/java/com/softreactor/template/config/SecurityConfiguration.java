package com.softreactor.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.softreactor.template.domain.account.User;
import com.softreactor.template.service.AccountService;

/**
 * Security configuration class.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final AccountService accountService;

	public SecurityConfiguration(AccountService accountService) {
		Assert.notNull(accountService, "AccountService must not be null");
		this.accountService = accountService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new AccountAuthenticationProvider(accountService));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			/*.requiresChannel()
				.anyRequest()
					.requiresSecure()
			.and()*/.csrf()
				.csrfTokenRepository(new CookieCsrfTokenRepository())
			.and().formLogin()
				.usernameParameter("email")
				.passwordParameter("password")
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureForwardUrl("/processLogin")
			.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
			.and().rememberMe()
				.rememberMeParameter("rememberMe")
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(30*24*60*60);
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				User user = accountService.findUserByEmail(username);
				if (user == null) {
					throw new UsernameNotFoundException("Incorrect username: " + username);
				}

				return new AccountUserDetails(user, accountService.findUserRoles(user.getId()));
			}
		};
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		return accountService;
	}

}