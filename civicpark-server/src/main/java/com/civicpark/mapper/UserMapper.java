package com.civicpark.mapper;

import com.civicpark.dto.UserRequestDTO;
import com.civicpark.entities.User;

public class UserMapper {
	public User toEntity(UserRequestDTO dto) {
		AddressMapper addressMapper = new AddressMapper();

		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setMiddleName(dto.getMiddleName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setContactNumber(dto.getContactNumber());
		user.setAddress(addressMapper.toEntity(dto.getAddress()));
		return user;
	}
}
