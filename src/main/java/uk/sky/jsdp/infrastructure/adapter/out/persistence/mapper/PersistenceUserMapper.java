package uk.sky.jsdp.infrastructure.adapter.out.persistence.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uk.sky.jsdp.application.dto.UserDto;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface PersistenceUserMapper {
    UserDto entityToDto(UserEntity source);

    UserEntity dtoToEntity(UserDto source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UserDto source, @MappingTarget UserEntity destination);
}
