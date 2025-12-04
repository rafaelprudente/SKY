package uk.sky.jsdp.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleDto {
    private Long id;
    private String name;
}
