package com.civicpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.civicpark.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
}
