package com.civicpark.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.civicpark.custom_exceptions.ResourceConflictException;
import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.UserLoginRequestDTO;
import com.civicpark.dto.UpdatePasswordRequest;
import com.civicpark.dto.UserRegistrationRequestDTO;
import com.civicpark.dto.UserResponseDTO;
import com.civicpark.entities.User;
import com.civicpark.mapper.UserMapper;
import com.civicpark.repository.UserRepository;
import com.civicpark.utils.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
/**
 * User service handles all user related service including post user, get user,
 * patch user etc.
 */
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final UserMapper userMapper;

	// ==================================================================//
	/**
	 * Insert new user into database
	 * 
	 * @param dto
	 * @return dto
	 */
	@Transactional
	public UserResponseDTO addUser(UserRegistrationRequestDTO dto) {
		// Check is user already exist
		User existingUser = userRepository.findByEmail(dto.getEmail());
		System.out.println(dto.getEmail());
		if (existingUser != null) {
			throw new ResourceConflictException("user already exists with email: " + dto.getEmail());
		}

		User user = userMapper.toEntity(dto);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		User savedUser = userRepository.save(user);

		return userMapper.toDto(savedUser);
	}

	// ================================================================================================//
	/**
	 * This method is used to authenticate user and generate cookie
	 * 
	 * @param dto
	 * @param response
	 * @return responseDTO
	 */
	public UserResponseDTO loginUserAndSetCookie(UserLoginRequestDTO dto, HttpServletResponse response) {
		// Authenticate the user first
		org.springframework.security.core.Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		// Store authentication in the security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Load UserDetails
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(dto.getEmail());

		User user = userRepository.findByEmail(dto.getEmail());
		UserResponseDTO responseDTO = userMapper.toDto(user);

		// Generate token
		String token = jwtTokenProvider.generateToken(userDetails);

		// Set Token
		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		cookie.setSecure(false); // HTTPS only
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60);
		response.addCookie(cookie);

		return responseDTO;
	}

//	// ==================== Get User Object ====================//
//	public UserResponseDTO getUserObject(String email) {
//		User user = userRepository.findByEmail(email);
//		
//		
//	}

	// ==================== Get All User ====================//
	public List<User> getUsers() {
		List<User> list = userRepository.findAll();
		return list;
	}

	// ==================== Update Password ====================//
	@Transactional
	public void updatePassword(Long id, UpdatePasswordRequest password) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user doen not exist"));

		if (user == null) {
			throw new RuntimeErrorException(null, "some fields are missing");
		} else {
			try {
				user.setPassword(password.getPassword());
				userRepository.save(user);

			} catch (Exception e) {
				throw new RuntimeErrorException(null, "update failed");
			}
		}
	}

	// ==================== Find By Email ====================//
	public User loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			System.out.println("user not found");
		}
		return user;
	}

	// ==========================================================================================================//
	public UserResponseDTO getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user does not exist"));
		UserResponseDTO responseUser = userMapper.toDto(user);
		return responseUser;
	}
}
