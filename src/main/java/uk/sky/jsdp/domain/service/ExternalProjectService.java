package uk.sky.jsdp.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.sky.jsdp.application.dto.ExternalProjectDto;
import uk.sky.jsdp.application.port.ExternalProjectManagement;
import uk.sky.jsdp.application.port.ExternalProjectPersistence;
import uk.sky.jsdp.application.port.UserPersistence;
import uk.sky.jsdp.domain.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalProjectService implements ExternalProjectManagement {
    private final UserPersistence userPersistence;
    private final ExternalProjectPersistence externalProjectPersistence;

    @Override
    public List<ExternalProjectDto> findAll() {
        return externalProjectPersistence.findAll();
    }

    @Override
    public ExternalProjectDto findById(String id) {
        return externalProjectPersistence.findById(id)
                .orElseThrow(() -> new NotFoundException("External project not found."));
    }

    @Override
    public void deleteById(String id) {
        if (!externalProjectPersistence.existsById(id)) {
            throw new NotFoundException("External project not found.");
        }

        externalProjectPersistence.deleteById(id);
    }

    @Override
    public List<ExternalProjectDto> findAllByUserId(Long userId) {
        if (!userPersistence.existsById(userId)) {
            throw new NotFoundException("User not found.");
        }

        return externalProjectPersistence.findAllByUserId(userId);
    }

    @Override
    public ExternalProjectDto saveExternalProjectToUser(Long userId, ExternalProjectDto externalProjectDto) {
        if (!userPersistence.existsById(userId)) {
            throw new NotFoundException("User not found.");
        }

        return externalProjectPersistence.saveExternalProjectToUser(userId, externalProjectDto);
    }
}
