package uk.sky.jsdp.infrastructure.adapter.in.web.mapper;

import org.mapstruct.Mapper;
import uk.sky.jsdp.application.dto.AuthDto;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.AuthResponse;

@Mapper(componentModel = "spring")
public interface WebAuthenticationMapper {

    AuthResponse dtoToResponse(AuthDto token);
}
