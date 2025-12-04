package uk.sky.jsdp.infrastructure.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.sky.jsdp.application.port.ExternalProjectManagement;
import uk.sky.jsdp.application.port.UserManagement;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebExternalProjectMapper;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebUserMapper;
import uk.sky.jsdp.infrastructure.adapter.in.web.request.ExternalProjectRequest;
import uk.sky.jsdp.infrastructure.adapter.in.web.request.UserRequest;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.UerExternalProjectResponse;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.UserResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Management")
public class UserController {
    private final UserManagement userManagement;
    private final WebUserMapper webUserMapper;
    private final ExternalProjectManagement externalProjectManagement;
    private final WebExternalProjectMapper webExternalProjectMapper;

    @Operation(
            operationId = "findAllUsers",
            summary = "Get list of users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of users",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserResponse.class)
                                    )
                            )
                    )
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<@NotNull UserResponse>> findAll() {
        return ResponseEntity.ok(webUserMapper.dtoCollectionToResponseList(userManagement.findAll()));
    }

    @Operation(
            operationId = "findUserById",
            summary = "Find user by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(webUserMapper.dtoToResponse(userManagement.findById(id)));
    }

    @Operation(
            operationId = "saveUser",
            summary = "Create user",
            description = "Creates a new user and returns its location",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created",
                            headers = {
                                    @Header(
                                            name = "Location",
                                            description = "URL of the created resource",
                                            schema = @Schema(type = "string", format = "uri")
                                    )
                            },
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    )
            }
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserRequest userRequest) {
        UserResponse response = webUserMapper.dtoToResponse(userManagement.save(
                webUserMapper.requestToDto(userRequest)
        ));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(
            operationId = "updateUser",
            summary = "Update the user data",
            description = "Update the user data",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    )
            }
    )
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse response = webUserMapper.dtoToResponse(userManagement.update(id,
                webUserMapper.requestToDto(userRequest)
        ));

        return ResponseEntity.ok(response);
    }

    @Operation(
            operationId = "deleteUser",
            summary = "Delete user by id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userManagement.deleteById(id);
    }

    @Operation(
            operationId = "findAllExternalProjectsFromUser",
            summary = "Get list of external project from user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of external project from user",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UerExternalProjectResponse.class)
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = ("/{userId}/external-projects"), produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UerExternalProjectResponse>> findAllExternalProjectsFromUser(@PathVariable Long userId) {
        return ResponseEntity.ok(webExternalProjectMapper.dtoCollectionToUserResponseList(externalProjectManagement.findAllByUserId(userId)));
    }

    @Operation(
            operationId = "saveExternalProjectToUser",
            summary = "Create external project for user",
            description = "Create external project for user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "External project for user created",
                            headers = {
                                    @Header(
                                            name = "Location",
                                            description = "URL of the created resource",
                                            schema = @Schema(type = "string", format = "uri")
                                    )
                            },
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UerExternalProjectResponse.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/{userId}/external-projects",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponse(responseCode = "201", description = "External project created")
    public ResponseEntity<UerExternalProjectResponse> saveExternalProjectToUser(@PathVariable Long userId, @Valid @RequestBody ExternalProjectRequest externalProjectRequest) {
        UerExternalProjectResponse response = webExternalProjectMapper.dtoToUserResponse(externalProjectManagement.saveExternalProjectToUser(userId,
                webExternalProjectMapper.requestToDto(externalProjectRequest)
        ));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }
}
