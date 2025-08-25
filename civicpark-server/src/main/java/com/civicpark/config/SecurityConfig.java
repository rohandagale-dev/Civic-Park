package com.civicpark.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.civicpark.utils.RtoAuthenticationProvider;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	// ===================================================================================================//
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsServiceImpl,
			RtoAuthenticationProvider rtoAuthenticationProvider, PasswordEncoder passwordEncoder) throws Exception {

		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsServiceImpl);
		daoProvider.setPasswordEncoder(passwordEncoder);

		authBuilder.authenticationProvider(daoProvider);
		authBuilder.authenticationProvider(rtoAuthenticationProvider);

		return authBuilder.build();
	}

	// =======================================//
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// ===============================================================================================================//
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF for stateless JWT-based authentication
				.csrf(AbstractHttpConfigurer::disable)

				// Enable CORS with custom configuration
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				// No session: every request is authenticated via JWT
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization rules
				.authorizeHttpRequests(auth -> auth
						// Public endpoints
						.requestMatchers("/public/**", "/auth/**", "/public/user/**").permitAll()

						// Preflight requests (CORS)
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						// RTO dashboard or APIs (restricted to ROLE_RTO)
						.requestMatchers("/rto-office/**").permitAll()

						// Normal user APIs (restricted to ROLE_USER)
						.requestMatchers("/user/**").hasRole("USER")

						// Anything else requires authentication
						.anyRequest().permitAll())

				// Add JWT filter before UsernamePasswordAuthenticationFilter
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// ======================================================================================//
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:5173")); // exact domain, not pattern
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("Authorization"));
		config.setAllowCredentials(true); // allow cookies

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}
