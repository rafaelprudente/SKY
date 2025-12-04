package uk.sky.jsdp.infrastructure.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.sky.jsdp.application.port.ExternalProjectManagement;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebExternalProjectMapper;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.ExternalProjectResponse;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.UerExternalProjectResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external-projects")
@Tag(name = "External Projects Management")
public class ExternalProjectController {
    private final ExternalProjectManagement externalProjectManagement;
    private final WebExternalProjectMapper webExternalProjectMapper;

    @Operation(
            operationId = "findAllExternalProjects",
            summary = "Get list of external projects",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "External projects found",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UerExternalProjectResponse.class)
                                    )
                            )
                    )
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExternalProjectResponse>> findAll() {
        return ResponseEntity.ok(webExternalProjectMapper.dtoCollectionToResponseList(externalProjectManagement.findAll()));
    }

    @Operation(
            operationId = "findExternalProjectById",
            summary = "Find external project by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "External project found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UerExternalProjectResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "External project not found",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(webExternalProjectMapper.dtoToResponse(externalProjectManagement.findById(id)));
    }

    @Operation(
            operationId = "deleteExternalProject",
            summary = "Delete external project id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "External project deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "External project not found",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        externalProjectManagement.deleteById(id);
    }
}
