package uk.sky.jsdp.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uk.sky.jsdp.application.dto.UserAuthenticatedDto;
import uk.sky.jsdp.application.dto.UserDto;
import uk.sky.jsdp.application.port.UserPersistence;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.RoleEntity;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.UserEntity;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.mapper.PersistenceUserMapper;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.repository.RoleRepository;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PersistenceUserMapper persistenceUserMapper;

    @Override
    public List<UserDto> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(persistenceUserMapper::entityToDto).toList();
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id).map(persistenceUserMapper::entityToDto);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto update(Long id, UserDto newUserData) {
        AtomicReference<UserEntity> user = new AtomicReference<>();
        userRepository.findById(id).ifPresent(user::set);

        persistenceUserMapper.partialUpdate(newUserData, user.get());

        return persistenceUserMapper.entityToDto(userRepository.save(user.get()));
    }

    @Override
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = persistenceUserMapper.dtoToEntity(userDto);

        Optional<RoleEntity> defaultRole = roleRepository.findByName("ROLE_USER");
        defaultRole.ifPresent(roleEntity -> userEntity.setRoles(new java.util.HashSet<>(List.of(roleEntity))));

        return persistenceUserMapper.entityToDto(userRepository.save(userEntity));
    }

    @Override
    public boolean existsById(long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<UserAuthenticatedDto> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserAuthenticatedDto::new);
    }
}
