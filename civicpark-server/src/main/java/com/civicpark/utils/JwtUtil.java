package com.civicpark.utils;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

	// Secret key used to sign the token (in real app, store securely in
	// application.properties)
	private final String SECRET_KEY = "your_secret_key_here";

	// Token validity (10 hours)
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

	// Generate JWT token
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	// Create JWT token with claims and subject
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	// Extract username (subject) from token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extract expiration date from token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Generic claim extractor
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Validate the token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Check if token is expired
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Parse token and get claims
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
}
