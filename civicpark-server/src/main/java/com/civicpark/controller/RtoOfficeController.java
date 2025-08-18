package com.civicpark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.dto.RtoLoginDTO;
import com.civicpark.dto.RtoRequestDTO;
import com.civicpark.dto.RtoResponseDTO;
import com.civicpark.entities.RtoOffice;
import com.civicpark.mapper.RtoOfficeMapper;
import com.civicpark.service.RtoOfficeService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rto-office")
@AllArgsConstructor
public class RtoOfficeController {
	private final RtoOfficeService service;
	private final RtoOfficeMapper mapper;

	// ================== RTO Registration ==================//
	@PostMapping("/register")
	public ResponseEntity<?> registerNewOffice(@RequestBody RtoRequestDTO dto) {
		try {
			RtoResponseDTO response = service.registerNewOffice(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (ResourceConflictException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
		}
	}

	// ================== RTO Login ==================//
	@PostMapping("/login")
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

	// ================== List Officeses ==================//
	@GetMapping("/list")
	public ResponseEntity<?> getListOfOffice() {
		try {
			List<RtoOffice> response = service.getListOfOffice();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	// ================== Verify Me ==================//
	@PostMapping("/verify-me")
	public ResponseEntity<?> verifyMe() {
		return ResponseEntity.status(HttpStatus.OK).body("user verified");
	}
}
