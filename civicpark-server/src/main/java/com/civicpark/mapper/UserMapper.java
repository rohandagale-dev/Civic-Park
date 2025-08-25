package com.civicpark.mapper;

import com.civicpark.dto.UserRegistrationRequestDTO;
import com.civicpark.dto.UserResponseDTO;
import com.civicpark.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	// RequestDTO -> Entity
	User toEntity(UserRegistrationRequestDTO dto);

	// Entity -> ResponseDTO
	UserResponseDTO toDto(User user);
	

	// List<Entity> -> List<ResponseDTO>
	List<UserResponseDTO> toDtoList(List<User> users);
}
