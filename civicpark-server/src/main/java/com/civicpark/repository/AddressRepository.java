package com.civicpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.civicpark.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	
}
