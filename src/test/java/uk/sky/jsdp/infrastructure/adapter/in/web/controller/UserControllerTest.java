package uk.sky.jsdp.infrastructure.adapter.in.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.sky.jsdp.application.dto.UserDto;
import uk.sky.jsdp.application.port.ExternalProjectManagement;
import uk.sky.jsdp.application.port.UserManagement;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebExternalProjectMapper;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebUserMapper;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.ExternalProjectResponse;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.UserResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserManagement userManagement;
    @MockitoBean
    private WebUserMapper webUserMapper;
    @MockitoBean
    private ExternalProjectManagement externalProjectManagement;
    @MockitoBean
    private WebExternalProjectMapper webExternalProjectMapper;

    @Test
    void findAll() throws Exception {
        when(userManagement.findAll()).thenReturn(List.of());
        when(webUserMapper.dtoCollectionToResponseList(any())).thenReturn(List.of(UserResponse.builder().id(1).build(), UserResponse.builder().build()));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void findById() throws Exception {
        when(userManagement.findById(anyLong())).thenReturn(UserDto.builder().build());
        when(webUserMapper.dtoToResponse(any())).thenReturn(UserResponse.builder().id(1).build());

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void save() throws Exception {
        when(webUserMapper.requestToDto(any())).thenReturn(UserDto.builder().build());
        when(userManagement.save(any())).thenReturn(UserDto.builder().build());
        when(webUserMapper.dtoToResponse(any())).thenReturn(UserResponse.builder().build());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"test\", \"password\": \"password\", \"email\": \"test@test.com\", \"name\": \"Test test\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"test\", \"password\": \"password\", \"email\": \"test@test.com\", \"name\": \"Test test\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(userManagement).deleteById(anyLong());

        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void findAllExternalProjectsFromUser() throws Exception {
        when(userManagement.findAll()).thenReturn(List.of());
        when(webUserMapper.dtoCollectionToResponseList(any())).thenReturn(List.of());

        mockMvc.perform(get("/users/1/external-projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveExternalProjectToUser() throws Exception {
        when(webUserMapper.requestToDto(any())).thenReturn(UserDto.builder().build());
        when(userManagement.save(any())).thenReturn(UserDto.builder().build());
        when(webExternalProjectMapper.dtoToResponse(any())).thenReturn(ExternalProjectResponse.builder().build());

        mockMvc.perform(post("/users/1/external-projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"test\", \"password\": \"password\", \"email\": \"test@test.com\", \"name\": \"Test test\" }"))
                .andExpect(status().isCreated());
    }
}