package com.civicpark.utils;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.civicpark.custom_exceptions.BadCredentialsException;
import com.civicpark.service.RtoUserDetailsService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RtoAuthenticationProvider implements AuthenticationProvider {

	private final RtoUserDetailsService rtoUserDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();

		UserDetails userDetails = rtoUserDetailsService.loadUserByUsername(email);

		if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid RTO credentials");
		}

		return new RtoUsernamePasswordAuthenticationToken(userDetails, userDetails.getUsername());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return RtoUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
