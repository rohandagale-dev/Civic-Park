package com.civicpark.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.civicpark.custom_exceptions.ResourceNotFoundException;
import com.civicpark.dto.UpdatePasswordRequest;
import com.civicpark.dto.UserRequestDTO;
import com.civicpark.entities.User;
import com.civicpark.mapper.UserMapper;
import com.civicpark.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// --------------- Add New User ---------------//
	@Transactional
	public User addUser(UserRequestDTO user) {
		User exitingUser = userRepository.findByEmail(user.getEmail());
		UserMapper userMapper = new UserMapper();
		User mappedUser = userMapper.toEntity(user);

		if (exitingUser == null) {
			try {
				return userRepository.save(mappedUser);
			} catch (Exception e) {
				throw new RuntimeErrorException(null, "some fields are missing");
			}
		} else {
			throw new RuntimeErrorException(null, "some fields are missing");
		}
	}

	public List<User> getUsers() {
		List<User> list = userRepository.findAll();
		return list;
	}

	// --------------- Update User Password ---------------//
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

	// --------------- Find by Email ---------------//
	public User loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			System.out.println("user not found");
		}
		return user;
	}
	
}
