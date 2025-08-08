package com.civicpark.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.LoginRequestDTO;
import com.civicpark.dto.UpdatePasswordRequest;
import com.civicpark.dto.UserRequestDTO;
import com.civicpark.entities.User;
import com.civicpark.mapper.UserMapper;
import com.civicpark.repository.UserRepository;
import com.civicpark.utils.JwtTokenProvider;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	// ==================== Register New User ====================//
	@Transactional
	public User addUser(UserRequestDTO user) {
		User exitingUser = userRepository.findByEmail(user.getEmail());

		UserMapper userMapper = new UserMapper(); // Model mapper

		User mappedUser = userMapper.toEntity(user); // DTO to Entity
		mappedUser.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the password before saving in
																			// database

		if (exitingUser == null) {
			try {
				User newUser = userRepository.save(mappedUser);
				return newUser;
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "Error while saving the user");
			}
		} else {
			throw new RuntimeErrorException(null, "User already exists");
		}
	}

	// ==================== Login User ====================//
	public String loginUser(LoginRequestDTO dto) {
		// Authenticate the user first
		org.springframework.security.core.Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		// Store authentication in the security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Load UserDetails
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(dto.getEmail());

		// Generate token
		return jwtTokenProvider.generateToken(userDetails);
	}

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

}
