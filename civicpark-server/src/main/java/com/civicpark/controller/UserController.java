package com.civicpark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.dto.UpdatePasswordRequest;
import com.civicpark.dto.UserResponseDTO;
import com.civicpark.entities.User;
import com.civicpark.repository.UserRepository;
import com.civicpark.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	// =============== get all the users ===============//
	@GetMapping
	public ResponseEntity<List<User>> getUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
	}

	// =============== update user password ===============//
	@PatchMapping("/{id}")
	public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest password) {
		userService.updatePassword(id, password);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	// =============== Get User By Id ===============//
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user doesn't found");
		}
	}

	// ==================== Verify Me ====================//
	@PostMapping("/verify-me")
	public ResponseEntity<?> verifyMe() {
		return ResponseEntity.status(HttpStatus.OK).body("something is fishy");
	}

	// =============== Logout User ===============//
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
	    Cookie cookie = new Cookie("token", null);
	    cookie.setMaxAge(0); // expires immediately
	    cookie.setPath("/");
	    cookie.setHttpOnly(true); 
	    cookie.setSecure(true); // if using HTTPS
	    response.addCookie(cookie);
	    return ResponseEntity.ok("Logged out successfully");
	}

}
