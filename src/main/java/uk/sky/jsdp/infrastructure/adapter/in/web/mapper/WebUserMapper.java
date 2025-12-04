package uk.sky.jsdp.infrastructure.adapter.in.web.mapper;

import org.mapstruct.Mapper;
import uk.sky.jsdp.application.dto.UserDto;
import uk.sky.jsdp.infrastructure.adapter.in.web.request.UserRequest;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.UserResponse;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WebUserMapper {
    UserResponse dtoToResponse(UserDto source);

    List<UserResponse> dtoCollectionToResponseList(Collection<UserDto> source);

    UserDto requestToDto(UserRequest userRequest);
}
