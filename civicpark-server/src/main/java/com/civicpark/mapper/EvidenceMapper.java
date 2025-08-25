package com.civicpark.mapper;

import com.civicpark.dto.EvidenceResponseDTO;
import com.civicpark.entities.Evidence;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {

    EvidenceMapper INSTANCE = Mappers.getMapper(EvidenceMapper.class);

    // Entity → DTO
    EvidenceResponseDTO toDto(Evidence evidence);

    // List<Entity> → List<DTO>
    List<EvidenceResponseDTO> toDtoList(List<Evidence> evidences);
}
