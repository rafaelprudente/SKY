package uk.sky.jsdp.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthDto {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private long expiresIn;
}
