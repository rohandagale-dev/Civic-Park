package com.civicpark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.dto.LoginRequestDTO;
import com.civicpark.dto.RtoLoginDTO;
import com.civicpark.dto.UserRequestDTO;
import com.civicpark.entities.User;
import com.civicpark.service.RtoOfficeService;
import com.civicpark.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final RtoOfficeService service;

	// ==================== Health Check ====================//
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		return "hello world" + request.getSession().getId();
	}

	// ==================== Login User ====================//
	@PostMapping("/login")
	public ResponseEntity<?> addUser(@RequestBody LoginRequestDTO dto, HttpServletResponse response) {
		try {
			String token = userService.loginUser(dto);

			// Create the cookie
			Cookie cookie = new Cookie("token", token);
			cookie.setHttpOnly(true); // Prevents JavaScript access (XSS protection)
			cookie.setSecure(true); // Send only over HTTPS (set false in local dev)
			cookie.setPath("/"); // Cookie available for all paths
			cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days in seconds

			// Add cookie to response
			response.addCookie(cookie);

			return ResponseEntity.ok("Login successful");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
		}
	}

	// ==================== Login RTO Office ====================//
	@PostMapping("/rto/rto-office")
	public ResponseEntity<?> loginRtoOffice(@RequestBody RtoLoginDTO dto, HttpServletResponse response) {
		try {
			String token = service.loginRtoOffice(dto);
			Cookie cookie = new Cookie("token", token);
			cookie.setHttpOnly(true); // Prevents JavaScript access (XSS protection)
			cookie.setSecure(true); // Send only over HTTPS (set false in local dev)
			cookie.setPath("/"); // Cookie available for all paths
			cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days in seconds

			// Add cookie to response
			response.addCookie(cookie);
			return ResponseEntity.ok("Login successful");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");

		}
	}

	// =============== Register User ===============//
	@PostMapping("/signup")
	public ResponseEntity<?> addUser(@RequestBody UserRequestDTO dto) {
		try {
			User newUser = userService.addUser(dto);
			System.out.println(newUser);
			return ResponseEntity.status(HttpStatus.OK).body(newUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user");
		}
	}
}
