package com.civicpark.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.civicpark.dto.RtoResponseDTO;
import com.civicpark.entities.RtoOffice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V"; // Should be at least 32 chars for HMAC
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ========== Extract Username ========== //
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ========== Extract Expiration ========== //
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // ========== Extract All Claims ========== //
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ========== Check Token Expired ========== //
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ========== Generate Token ========== //
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    
    public String generateRtoToken(RtoResponseDTO response) {
    	 Map<String, Object> claims = new HashMap<>();
         return createToken(claims, response.getEmail());
    }

    // ========== Create Token ========== //
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ========== Validate Token ========== //
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
