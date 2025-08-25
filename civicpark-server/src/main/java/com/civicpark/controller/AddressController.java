package com.civicpark.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civicpark.dto.AddressRequestDTO;
import com.civicpark.entities.Address;
import com.civicpark.service.AddressService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/addresses")
public class AddressController {
	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@GetMapping
	public ResponseEntity<List<Address>> getAllAddresses() {
		List<Address> addresses = addressService.getAllAddress();

		if (addresses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(addresses);
	}


	@PostMapping
	public ResponseEntity<Address> addUser(@RequestBody AddressRequestDTO address) {
		Address newAddress = addressService.addUser(address);

		if (address.equals(null)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(newAddress);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
	}
}
