package com.civicpark.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.civicpark.dto.AddressRequestDTO;
import com.civicpark.entities.Address;
import com.civicpark.mapper.AddressMapper;
import com.civicpark.repository.AddressRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {

	private final AddressRepository addressRepository;

	public AddressService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	public List<Address> getAllAddress() {
		return addressRepository.findAll();
	}

	@Transactional
	public Address addUser(AddressRequestDTO address) {
		try {
			AddressMapper mapper = new AddressMapper();
			Address temp = mapper.toEntity(address);
			return addressRepository.save(temp);
		} catch (Exception e) {
			System.err.println("Error while saving address: " + e.getMessage());
			throw new RuntimeErrorException(null, "some fields are missing");

		}
	}
}
