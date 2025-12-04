package uk.sky.jsdp.infrastructure.adapter.in.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.sky.jsdp.application.port.ExternalProjectManagement;
import uk.sky.jsdp.infrastructure.adapter.in.web.mapper.WebExternalProjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExternalProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExternalProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExternalProjectManagement externalProjectManagement;
    @MockitoBean
    private WebExternalProjectMapper webExternalProjectMapper;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/external-projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/external-projects/externalProjectsId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/external-projects/externalProjectsId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}