package com.civicpark.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RtoUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

	public RtoUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
}
