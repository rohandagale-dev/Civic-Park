package com.civicpark.mapper;

import com.civicpark.dto.AddressRequestDTO;
import com.civicpark.entities.Address;

public class AddressMapper {
	public Address toEntity(AddressRequestDTO dto) {
		Address address = new Address();
		address.setFlatNo(dto.getFlatNo());
		address.setStreetNo(dto.getStreetNo());
		address.setStreetName(dto.getStreetName());
		address.setDistrict(dto.getDistrict());
		address.setState(dto.getState());
		address.setCountry(dto.getCountry());
		address.setLongitude(dto.getLongitude());
		address.setLatitude(dto.getLatitude());
		return address;
	}
}
