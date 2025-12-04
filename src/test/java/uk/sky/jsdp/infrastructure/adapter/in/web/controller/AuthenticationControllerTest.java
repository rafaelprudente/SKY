package uk.sky.jsdp.infrastructure.adapter.in.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.sky.jsdp.application.dto.AuthDto;
import uk.sky.jsdp.application.port.UserAuthentication;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebAuthenticationMapper;
import uk.sky.jsdp.infrastructure.adapter.in.web.response.AuthResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserAuthentication userAuthentication;
    @MockitoBean
    private WebAuthenticationMapper webAuthenticationMapper;
    @MockitoBean
    private Authentication authentication;

    @Test
    void authenticateUser() throws Exception {
        when(userAuthentication.token(any()))
                .thenReturn(AuthDto.builder().token("abc").type("Bearer").expiresIn(3600).build());
        when(webAuthenticationMapper.dtoToResponse(any()))
                .thenReturn(AuthResponse.builder().token("abc").type("Bearer").expiresIn(3600).build());

        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("abc"));
    }
}