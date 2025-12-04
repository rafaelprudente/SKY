package uk.sky.jsdp.infrastructure.adapter.in.web.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@JsonPropertyOrder({"id", "username", "email", "name", "enable"})
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String name;
    private boolean enabled;
    private Set<RoleResponse> roles;
}
