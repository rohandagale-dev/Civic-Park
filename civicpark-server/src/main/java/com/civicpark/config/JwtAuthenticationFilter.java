package com.civicpark.config;

import com.civicpark.service.UserDetailsServiceImpl;
import com.civicpark.service.RtoUserDetailsService;
import com.civicpark.utils.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final RtoUserDetailsService rtoUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = null;

		// ✅ 1. Try Authorization header
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}
  
		// ✅ 2. If no header, try cookies
		if (token == null && request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("rtoToken".equals(cookie.getName()) || "userToken".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}

		// ✅ If still no token → skip
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String username = null;
		String role = null;

		try {
			username = jwtTokenProvider.extractUsername(token);
			role = jwtTokenProvider.extractRole(token); // custom method
		} catch (Exception e) {
			System.out.println("Invalid Token: " + e.getMessage());
			filterChain.doFilter(request, response);
			return;
		}

		// ✅ Authenticate if username present & not already authenticated
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails;

			if ("RTO".equalsIgnoreCase(role)) {
				userDetails = rtoUserDetailsService.loadUserByUsername(username);
			} else {
				userDetails = userDetailsServiceImpl.loadUserByUsername(username);
			}

			if (jwtTokenProvider.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
