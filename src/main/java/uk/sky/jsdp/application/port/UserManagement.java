package uk.sky.jsdp.application.port;

import uk.sky.jsdp.application.dto.UserDto;

import java.util.List;

public interface UserManagement {
    List<UserDto> findAll();

    UserDto findById(long id);

    UserDto save(UserDto userDto);

    void deleteById(long id);

    UserDto update(Long id, UserDto userDto);
}
