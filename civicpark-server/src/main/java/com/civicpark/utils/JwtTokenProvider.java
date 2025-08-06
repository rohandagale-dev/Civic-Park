package com.civicpark.utils;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenProvider {

	private static final String SECRET_KEY = "hanna1234567890123456789012345678";

	private final Key key;

	public JwtTokenProvider() {
		this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	// ==================== Generate Token ====================//
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 86400000); // token valid for 1 day

		return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	// ==================== Extract Email ====================//
	public String getEmailFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	// ==================== Validate Token ====================//
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			System.out.println("JWT expired: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			System.out.println("Malformed JWT: " + ex.getMessage());
		} catch (SignatureException ex) {
			System.out.println("Invalid signature: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			System.out.println("Empty token: " + ex.getMessage());
		}
		return false;
	}

	// ============== Validate Token ============== //
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getEmailFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	// ============== Check Expiration ============== //
	private boolean isTokenExpired(String token) {
		final Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
				.getExpiration();
		return expiration.before(new Date());
	}
}
