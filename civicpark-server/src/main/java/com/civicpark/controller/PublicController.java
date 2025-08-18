package com.civicpark.controller;

import java.net.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
import com.civicpark.repository.UserRepository;
import com.civicpark.service.RtoOfficeService;
import com.civicpark.service.UserService;

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
	private final UserRepository repository;

	// ==================== Health Check ====================//
	@GetMapping("/health")
	public String login(HttpServletRequest request) {
		return "hello world" + request.getSession().getId();
	}

	// ==================== Login RTO User ====================//
	@PostMapping("/user/login")
	public ResponseEntity<?> addUser(@RequestBody LoginRequestDTO dto, HttpServletResponse response) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.loginUserAndSetCookie(dto, response));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
		}
	}

	// ==================== Login RTO Office ====================//
	@PostMapping("/rto/login")
	public ResponseEntity<?> loginRtoOffice(@RequestBody RtoLoginDTO dto, HttpServletResponse response) {
		try {
			String token = service.loginRtoOffice(dto);

			// Create cookie with SameSite=None for cross-origin requests
			ResponseCookie cookie = ResponseCookie.from("token", token).httpOnly(false) // prevent JavaScript access
					.secure(false) // set to true in production with HTTPS
					.sameSite("Lax") // required for cross-origin cookies
					.path("/").maxAge(7 * 24 * 60 * 60) // 7 days
					.build();

			// Add cookie to response
			response.addHeader(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString());

			return ResponseEntity.ok("Login successful");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong: " + e.getMessage());
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
