package com.gestioneleves.apieleves.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestioneleves.apieleves.config.ApiExceptionHandler;
import com.gestioneleves.apieleves.controller.EleveController;
import com.gestioneleves.apieleves.service.EleveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EleveControllerValidationTest {

    private MockMvc mockMvc;
    private EleveService eleveService;

    @BeforeEach
    void setup() {
        eleveService = Mockito.mock(EleveService.class);
        EleveController controller = new EleveController(eleveService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void postEleve_invalidPayload_returns400ValidationError() throws Exception {
        String body = "{}"; // missing required fields

        mockMvc.perform(post("/api/eleve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fields.nom").exists())
                .andExpect(jsonPath("$.fields.prenom").exists())
                .andExpect(jsonPath("$.fields.dateNaissance").exists());

        Mockito.verifyNoInteractions(eleveService);
    }
}
