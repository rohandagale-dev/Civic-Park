package com.civicpark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.dto.RtoCookieDTO;
import com.civicpark.dto.RtoLoginDTO;
import com.civicpark.dto.RtoRequestDTO;
import com.civicpark.dto.RtoResponseDTO;
import com.civicpark.dto.UserLoginRequestDTO;
import com.civicpark.dto.UserRegistrationRequestDTO;
import com.civicpark.dto.UserResponseDTO;
import com.civicpark.entities.User;
import com.civicpark.repository.UserRepository;
import com.civicpark.service.RtoOfficeService;
import com.civicpark.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/public")
@AllArgsConstructor

/**
 * Public controller which accepts request from open RESTApi endpoints
 */
public class PublicController {
	private final UserService userService;
	private final RtoOfficeService rtoService;

	// ============================================//
	/**
	 * Checking health of server
	 * 
	 * @param request object
	 * @return sessionId
	 */
	@GetMapping("/health")
	public String login(HttpServletRequest request) {
		return "hello world \n" + "JSESSION_ID: " + request.getSession().getId();
	}

	// =================================================================================================//
	/**
	 * 
	 * 
	 * @param dto
	 * @param response
	 * @return 200 ok status and cookie
	 */
	@PostMapping("/user/login")
	public ResponseEntity<?> addUser(@RequestBody UserLoginRequestDTO dto, HttpServletResponse response) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.loginUserAndSetCookie(dto, response));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
		}
	}

	// ================================================================================================//
	/**
	 * This method verify authenticated RTO and response back with cookie
	 * 
	 * @param dto
	 * @param response
	 * @return 200 OK with cookie
	 */
	@PostMapping("/rto/login")
	public ResponseEntity<?> loginRtoOffice(@RequestBody RtoCookieDTO dto, HttpServletResponse response) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(rtoService.loginRtoAndSetCookie(dto, response));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong: " + e.getMessage());
		}
	}

	// ===================================================================================//
	/**
	 * Register new user
	 * 
	 * @param dto
	 * @return 200 ok status and user
	 */
	@PostMapping("/user/signup")
	public ResponseEntity<?> createUser(@RequestBody UserRegistrationRequestDTO dto) {
		System.out.println("inside signup controller");
		try {
			UserResponseDTO newUser = userService.addUser(dto);
			return ResponseEntity.status(HttpStatus.OK).body(newUser);
		} catch (ResourceConflictException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// ========================================================================//
	/**
	 * Register new RTO office to database.
	 * 
	 * @param dto
	 * @return 201 create, 409 conflict, 500 server error.
	 */
	@PostMapping("/rto/register")
	public ResponseEntity<?> registerNewOffice(@RequestBody RtoRequestDTO dto) {
		try {
			RtoResponseDTO response = rtoService.registerNewOffice(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (ResourceConflictException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
		}
	}
}
