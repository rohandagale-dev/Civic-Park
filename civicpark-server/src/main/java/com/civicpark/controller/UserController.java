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
import com.civicpark.dto.UserRequestDTO;
import com.civicpark.entities.User;
import com.civicpark.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody UserRequestDTO user) {

		User newUser = userService.addUser(user);
		if (newUser.equals(null)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went wrong");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	@GetMapping
	public ResponseEntity<List<User>> getUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest password) {
		userService.updatePassword(id, password);
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
