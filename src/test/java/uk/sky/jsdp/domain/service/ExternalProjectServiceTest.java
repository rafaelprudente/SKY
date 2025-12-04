package uk.sky.jsdp.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.sky.jsdp.application.dto.ExternalProjectDto;
import uk.sky.jsdp.application.port.ExternalProjectPersistence;
import uk.sky.jsdp.application.port.UserPersistence;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ExternalProjectServiceTest {
    @Autowired
    private ExternalProjectService externalProjectService;
    @Autowired
    private UserPersistence userPersistence;
    @Autowired
    private ExternalProjectPersistence externalProjectPersistence;

    @Test
    void findAll() {
        ExternalProjectDto externalProjectDto1 = ExternalProjectDto.builder().id("1").name("name1").build();
        ExternalProjectDto externalProjectDto2 = ExternalProjectDto.builder().id("2").name("name2").build();

        given(externalProjectPersistence.findAll()).willReturn(List.of(externalProjectDto1, externalProjectDto2));

        List<ExternalProjectDto> list = externalProjectService.findAll();

        assertThat(list).isNotNull().hasSize(2);
        verify(externalProjectPersistence).findAll();
    }

    @Test
    void findById() {
        clearInvocations(externalProjectPersistence);

        ExternalProjectDto externalProjectDto = ExternalProjectDto.builder().id("1L").name("external project name").build();
        given(externalProjectPersistence.existsById(anyString())).willReturn(true);
        given(externalProjectPersistence.findById(anyString())).willReturn(Optional.ofNullable(externalProjectDto));

        ExternalProjectDto externalProject = externalProjectService.findById("1L");

        assertThat(externalProject).isNotNull();
        verify(externalProjectPersistence).findById(anyString());
    }

    @Test
    void deleteById() {
        doNothing().when(externalProjectPersistence).deleteById(anyString());

        externalProjectService.deleteById("1L");

        verify(externalProjectPersistence).deleteById(anyString());
    }

    @Test
    void findAllByUserId() {
        ExternalProjectDto externalProjectDto1 = ExternalProjectDto.builder().id("1").name("name1").build();
        ExternalProjectDto externalProjectDto2 = ExternalProjectDto.builder().id("2").name("name2").build();

        given(userPersistence.existsById(1L)).willReturn(true);
        given(externalProjectPersistence.findAllByUserId(anyLong())).willReturn(List.of(externalProjectDto1, externalProjectDto2));

        List<ExternalProjectDto> list = externalProjectService.findAllByUserId(1L);

        assertThat(list).isNotNull().hasSize(2);
        verify(externalProjectPersistence).findAllByUserId(anyLong());
    }

    @Test
    void saveExternalProjectToUser() {
        clearInvocations(userPersistence);

        given(userPersistence.existsById(1L)).willReturn(true);
        given(externalProjectPersistence.saveExternalProjectToUser(anyLong(), any())).willReturn(ExternalProjectDto.builder().id("1").name("new-project-name").build());

        ExternalProjectDto externalProject = externalProjectService.saveExternalProjectToUser(1L, ExternalProjectDto.builder().id("1").name("new-project-name").build());

        assertThat(externalProject).isNotNull();
        verify(externalProjectPersistence).saveExternalProjectToUser(anyLong(), any());
    }

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public ExternalProjectPersistence externalProjectPersistence() {
            return Mockito.mock(ExternalProjectPersistence.class);
        }

        @Bean
        public UserPersistence userPersistence() {
            return Mockito.mock(UserPersistence.class);
        }

        @Bean
        public ExternalProjectService externalProjectService(UserPersistence userPersistence, ExternalProjectPersistence externalProjectPersistence) {

            return new ExternalProjectService(userPersistence, externalProjectPersistence);
        }
    }
}