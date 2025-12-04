package uk.sky.jsdp.application.port;

import uk.sky.jsdp.application.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserPersistence {
    List<UserDto> findAll();

    Optional<UserDto> findById(long id);

    void deleteById(long id);

    UserDto update(Long id, UserDto newUserData);

    UserDto save(UserDto userDto);

    boolean existsById(long id);

    <T> Optional<T> findByUsername(String username);
}
