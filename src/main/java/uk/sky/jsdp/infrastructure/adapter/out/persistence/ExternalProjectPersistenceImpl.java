package uk.sky.jsdp.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uk.sky.jsdp.application.dto.ExternalProjectDto;
import uk.sky.jsdp.application.port.ExternalProjectPersistence;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.ExternalProjectEntity;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.UserEntity;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.mapper.PersistenceExternalProjectMapper;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.repository.ExternalProjectRepository;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class ExternalProjectPersistenceImpl implements ExternalProjectPersistence {
    private final UserRepository userRepository;
    private final ExternalProjectRepository externalProjectRepository;
    private final PersistenceExternalProjectMapper persistenceExternalProjectMapper;

    @Override
    public List<ExternalProjectDto> findAll() {
        return StreamSupport.stream(externalProjectRepository.findAll().spliterator(), false).map(persistenceExternalProjectMapper::entityToDto).toList();
    }

    @Override
    public Optional<ExternalProjectDto> findById(String id) {
        return externalProjectRepository.findById(id)
                .map(persistenceExternalProjectMapper::entityToDto);
    }

    @Override
    public void deleteById(String id) {
        externalProjectRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return externalProjectRepository.existsById(id);
    }

    @Override
    public List<ExternalProjectDto> findAllByUserId(Long userId) {
        return persistenceExternalProjectMapper.entityListToDtoList(externalProjectRepository.findByUser_Id(userId));
    }

    @Override
    public ExternalProjectDto saveExternalProjectToUser(Long userId, ExternalProjectDto externalProjectDto) {
        ExternalProjectEntity externalProjectEntity = persistenceExternalProjectMapper.dtoToEntity(externalProjectDto);

        Optional<UserEntity> user = userRepository.findById(userId);
        user.ifPresent(externalProjectEntity::setUser);

        return persistenceExternalProjectMapper.entityToDto(externalProjectRepository.save(externalProjectEntity));
    }
}
