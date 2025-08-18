package com.civicpark.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.civicpark.dto.UserResponseDTO;
import com.civicpark.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapperStruct {
    UserMapperStruct INSTANCE = Mappers.getMapper(UserMapperStruct.class);

    UserResponseDTO userToUserResponseDTO(User user);
}
