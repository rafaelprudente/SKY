package uk.sky.jsdp.application.port;

import uk.sky.jsdp.application.dto.ExternalProjectDto;

import java.util.List;
import java.util.Optional;

public interface ExternalProjectPersistence {
    List<ExternalProjectDto> findAll();

    Optional<ExternalProjectDto> findById(String id);

    void deleteById(String id);

    boolean existsById(String id);

    List<ExternalProjectDto> findAllByUserId(Long userId);

    ExternalProjectDto saveExternalProjectToUser(Long userId, ExternalProjectDto externalProjectDto);
}
