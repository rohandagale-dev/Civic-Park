package com.civicpark.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.civicpark.dto.RtoCookieDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V"; // Should be at least 32 chars for HMAC
	private final long EXPIRATION_TIME = 1000 * 60 * 60; // 7 Days

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	// ==========================================//
	/**
	 * Extract username from token
	 * 
	 * @param token
	 * @return email
	 */
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	// ==========================================//
	/**
	 * Extract expiration from token
	 * 
	 * @param token
	 * @return exp
	 */
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	// ============================================//
	/**
	 * 
	 * 
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	// ==========================================//
	/**
	 * Check token expiry
	 * 
	 * @param token
	 * @return ture/false
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// ===================================================//
	/**
	 * Generate token for user
	 * 
	 * @param userDetails
	 * @return string
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", "USER");
		return createToken(claims, userDetails.getUsername());
	}

	// ========================================================//
	/**
	 * Generate token for rto
	 * 
	 * @param rtoDetails
	 * @return string
	 */
	public String generateTokenForRTO(UserDetails rtoDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", "RTO");
		return createToken(claims, rtoDetails.getUsername());
	}

	// ====================================================================//
	/**
	 * Generate JWT token
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	// ==================================================================//
	/**
	 * Validate token
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	// ======================================================================= //
	/**
	 * Get all claims
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// ======================================//
	/**
	 * Extract role
	 * 
	 * @param token
	 * @return
	 */
	public String extractRole(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

}
