package uk.sky.jsdp.infrastructure.adapter.in.web.mapper;

import org.mapstruct.Mapper;
import uk.sky.jsdp.application.dto.ExternalProjectDto;
import uk.sky.jsdp.infrastructure.adapter.in.web.request.ExternalProjectRequest;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.ExternalProjectResponse;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WebExternalProjectMapper {
    List<ExternalProjectResponse> dtoCollectionToResponseList(Collection<ExternalProjectDto> all);

    ExternalProjectResponse dtoToResponse(ExternalProjectDto byId);

    ExternalProjectDto requestToDto(ExternalProjectRequest externalProjectRequest);
}
