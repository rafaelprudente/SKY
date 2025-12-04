package uk.sky.jsdp.domain.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.sky.jsdp.application.dto.AuthDto;
import uk.sky.jsdp.application.dto.UserDto;
import uk.sky.jsdp.application.port.UserAuthentication;
import uk.sky.jsdp.application.port.UserManagement;
import uk.sky.jsdp.application.port.UserPersistence;
import uk.sky.jsdp.domain.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserAuthentication, UserManagement {
    private final JwtService jwtService;
    private final UserPersistence userPersistence;
    private final PasswordEncoder passwordEncoder;

    public AuthDto token(Authentication authentication) {
        return jwtService.getGeneratedToken(authentication);
    }

    @Override
    public List<UserDto> findAll() {
        return userPersistence.findAll();
    }

    @Override
    public UserDto findById(long id) {
        return userPersistence.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    @Override
    public void deleteById(long id) {
        userPersistence.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));

        userPersistence.deleteById(id);
    }

    @Override
    public UserDto update(Long id, UserDto newUserData) {
        if (!userPersistence.existsById(id)) {
            throw new NotFoundException("User not found.");
        }

        return userPersistence.update(id, newUserData);
    }

    @Override
    public @Nullable UserDto save(UserDto userDto) {
        userDto.setEnabled(true);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userPersistence.save(userDto);
    }
}
