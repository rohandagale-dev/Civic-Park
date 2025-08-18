package com.civicpark.mapper;

import com.civicpark.dto.EvidenceResponseDTO;
import com.civicpark.dto.ReportResponseDTO;
import com.civicpark.entities.VerifiedReport;
import com.civicpark.entities.Evidence;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

	ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

	ReportResponseDTO toDto(VerifiedReport report);

	List<ReportResponseDTO> toDtoList(List<VerifiedReport> reports);

	EvidenceResponseDTO toEvidenceDto(Evidence evidence);
}
