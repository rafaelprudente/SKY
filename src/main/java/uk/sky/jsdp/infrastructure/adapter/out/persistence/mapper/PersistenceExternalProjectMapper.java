package uk.sky.jsdp.infrastructure.adapter.out.persistence.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uk.sky.jsdp.application.dto.ExternalProjectDto;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.ExternalProjectEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersistenceExternalProjectMapper {
    ExternalProjectDto entityToDto(ExternalProjectEntity source);

    ExternalProjectEntity dtoToEntity(ExternalProjectDto source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ExternalProjectDto source, @MappingTarget ExternalProjectDto destination);

    List<ExternalProjectDto> entityListToDtoList(List<ExternalProjectEntity> source);
}
