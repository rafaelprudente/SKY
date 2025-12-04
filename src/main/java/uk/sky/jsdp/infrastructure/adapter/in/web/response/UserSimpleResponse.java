package uk.sky.jsdp.infrastructure.adapter.in.web.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@JsonPropertyOrder({"id", "name", "enable"})
public class UserSimpleResponse {
    private long id;
    private String name;
    private boolean enabled;
}
