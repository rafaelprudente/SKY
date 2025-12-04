package uk.sky.jsdp.application.port;

import uk.sky.jsdp.application.dto.ExternalProjectDto;

import java.util.List;

public interface ExternalProjectManagement {

    List<ExternalProjectDto> findAll();

    ExternalProjectDto findById(String id);

    void deleteById(String id);

    List<ExternalProjectDto> findAllByUserId(Long userId);

    ExternalProjectDto saveExternalProjectToUser(Long userId, ExternalProjectDto externalProjectDto);
}
