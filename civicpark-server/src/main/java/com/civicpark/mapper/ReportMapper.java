package com.civicpark.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.civicpark.dto.ReportCreateRequestDTO;
import com.civicpark.dto.ReportResponseDTO;
import com.civicpark.entities.Report;

@Mapper(componentModel = "spring", uses = { EvidenceMapper.class, UserMapper.class })
public interface ReportMapper {

	ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

	// RequestDTO -> Entity
	Report toEntity(ReportCreateRequestDTO dto);

	// Entity -> ResponseDTO
	ReportResponseDTO toDto(Report report);

	// List<Entity> -> List<ResponseDTO>
	List<ReportResponseDTO> toDtoList(List<Report> reports);
}
