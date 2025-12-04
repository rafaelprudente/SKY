package uk.sky.jsdp.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalProjectDto {
    private Long id;
    private UserDto user;
    private String name;
}
