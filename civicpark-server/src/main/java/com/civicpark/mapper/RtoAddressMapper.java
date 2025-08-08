package com.civicpark.mapper;

import org.mapstruct.Mapper;
import com.civicpark.dto.AddressRequestDTO;
import com.civicpark.entities.Address;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface RtoAddressMapper {
	AddressRequestDTO toDTO(Address entity);

	Address toEntity(AddressRequestDTO dto);
}
