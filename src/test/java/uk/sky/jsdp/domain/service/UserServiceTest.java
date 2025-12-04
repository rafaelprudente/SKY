package uk.sky.jsdp.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.sky.jsdp.application.dto.AuthDto;
import uk.sky.jsdp.application.dto.UserDto;
import uk.sky.jsdp.application.port.UserPersistence;
import uk.sky.jsdp.domain.exception.NotFoundException;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.mapper.PersistenceUserMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"app.jwt_expiration=3600", "app.jwt_secret=abc123"})
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserPersistence userPersistence;
    @Autowired
    private PersistenceUserMapper persistenceUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private Authentication authentication;
    @Autowired
    private JwtEncoder jwtEncoder;

    @Test
    void token() {
        given(jwtEncoder.encode(any())).willReturn(Jwt.withTokenValue("fake-token")
                .header("alg", "HS256")
                .claim("sub", "user1")
                .build());
        given(authentication.getName()).willReturn("username");

        AuthDto response = userService.token(authentication);

        assertThat(response).isNotNull();
    }

    @Test
    void findAll() {
        UserDto user1 = UserDto.builder().id(1L).username("username1").build();
        UserDto user2 = UserDto.builder().id(2L).username("username2").build();
        given(userPersistence.findAll()).willReturn(List.of(user1, user2));

        List<UserDto> userList = userService.findAll();

        assertThat(userList).isNotNull().hasSize(2);
        verify(userPersistence).findAll();
    }

    @Test
    void findById() {
        clearInvocations(userPersistence);

        UserDto user1 = UserDto.builder().id(1L).username("username1").build();
        given(userPersistence.existsById(anyLong())).willReturn(true);
        given(userPersistence.findById(anyLong())).willReturn(Optional.ofNullable(user1));

        UserDto user = userService.findById(1L);

        assertThat(user).isNotNull();
        verify(userPersistence).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        doThrow(NotFoundException.class).when(userPersistence).findById(anyLong());

        assertThrows(NotFoundException.class, () -> {
            userService.findById(1L);
        });
    }

    @Test
    void deleteById() {
        given(userPersistence.findById(anyLong())).willReturn(Optional.ofNullable(UserDto.builder().id(1L).build()));
        doNothing().when(userPersistence).deleteById(anyLong());

        userService.deleteById(1L);

        verify(userPersistence).deleteById(anyLong());
    }

    @Test
    void deleteNotFound() {
        doThrow(NotFoundException.class).when(userPersistence).deleteById(anyLong());

        assertThrows(NotFoundException.class, () -> {
            userService.deleteById(1L);
        });
    }

    @Test
    void update() {
        clearInvocations(userPersistence);

        given(userPersistence.existsById(anyLong())).willReturn(true);
        given(userPersistence.update(anyLong(), any())).willReturn(UserDto.builder().id(1L).username("username1").build());

        UserDto user = userService.update(1L, UserDto.builder().username("new-username").build());

        assertThat(user).isNotNull();
        verify(userPersistence).update(anyLong(), any());
    }

    @Test
    void updateNotFound() {
        given(userPersistence.existsById(anyLong())).willReturn(false);
        UserDto userDto = UserDto.builder().username("new-username").build();

        assertThrows(NotFoundException.class, () -> {
            userService.update(1L, userDto);
        });
    }

    @Test
    void save() {
        clearInvocations(userPersistence);

        given(userPersistence.save(any())).willReturn(UserDto.builder().id(1L).username("new-username").build());

        UserDto user = userService.save(UserDto.builder().username("new-username").password("password").build());

        assertThat(user).isNotNull();
        verify(userPersistence).save(any());
    }

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public Authentication authentication() {
            return Mockito.mock(Authentication.class);
        }

        @Bean
        public JwtEncoder jwtEncoder() {
            return Mockito.mock(JwtEncoder.class);
        }

        @Bean
        public JwtService jwtService(JwtEncoder jwtEncoder) {
            return new JwtService(jwtEncoder);
        }

        @Bean
        public UserPersistence userPersistence() {
            return Mockito.mock(UserPersistence.class);
        }

        @Bean
        public PersistenceUserMapper persistenceUserMapper() {
            return Mappers.getMapper(PersistenceUserMapper.class);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserService userService(JwtService jwtService,
                                       UserPersistence userPersistence,
                                       PasswordEncoder passwordEncoder) {

            return new UserService(jwtService, userPersistence, passwordEncoder);
        }
    }
}