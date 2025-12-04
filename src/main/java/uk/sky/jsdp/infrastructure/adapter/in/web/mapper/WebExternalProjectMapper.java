package uk.sky.jsdp.infrastructure.adapter.in.web.mapper;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import uk.sky.jsdp.application.dto.ExternalProjectDto;
import uk.sky.jsdp.infrastructure.adapter.in.web.request.ExternalProjectRequest;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.ExternalProjectResponse;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.UerExternalProjectResponse;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WebExternalProjectMapper {
    List<UerExternalProjectResponse> dtoCollectionToUserResponseList(Collection<ExternalProjectDto> all);

    UerExternalProjectResponse dtoToUserResponse(ExternalProjectDto byId);

    ExternalProjectDto requestToDto(ExternalProjectRequest externalProjectRequest);

    List<ExternalProjectResponse> dtoCollectionToResponseList(List<ExternalProjectDto> all);

    @Nullable ExternalProjectResponse dtoToResponse(ExternalProjectDto byId);
}
