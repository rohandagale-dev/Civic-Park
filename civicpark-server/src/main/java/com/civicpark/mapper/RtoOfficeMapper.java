package com.civicpark.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.civicpark.dto.RtoRequestDTO;
import com.civicpark.dto.RtoResponseDTO;
import com.civicpark.entities.RtoOffice;

@Mapper(componentModel = "spring")
public interface RtoOfficeMapper {

	RtoOfficeMapper INSTANCE = Mappers.getMapper(RtoOfficeMapper.class);

	RtoResponseDTO toDTO(RtoOffice entity);

	RtoOffice toEntity(RtoRequestDTO dto);
}
