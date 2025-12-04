package uk.sky.jsdp.infrastructure.adapter.in.web.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalProjectResponse {
    private String id;
    private String name;
}
