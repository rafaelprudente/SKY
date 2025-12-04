package uk.sky.jsdp.infrastructure.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalProjectResponse {
    private Long id;
    private String name;
}
