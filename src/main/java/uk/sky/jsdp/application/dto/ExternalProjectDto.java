package uk.sky.jsdp.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalProjectDto {
    private String id;
    private String name;
}
