package uk.sky.jsdp.infrastructure.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.sky.jsdp.application.port.UserAuthentication;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebAuthenticationMapper;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.AuthResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final UserAuthentication userAuthentication;
    private final WebAuthenticationMapper webAuthenticationMapper;

    @SecurityRequirements
    @Operation(
            operationId = "authenticate",
            summary = "Authenticate user",
            description = "Validates user credentials and returns an authentication token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authentication successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid username or password",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @PostMapping(
            value = "/authenticate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AuthResponse authenticate(Authentication authentication) {
        return webAuthenticationMapper.dtoToResponse(userAuthentication.token(authentication));
    }
}
