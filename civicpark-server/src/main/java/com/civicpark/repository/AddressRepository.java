package com.civicpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.civicpark.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
}
