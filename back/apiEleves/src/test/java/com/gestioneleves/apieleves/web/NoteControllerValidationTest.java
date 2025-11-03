package com.gestioneleves.apieleves.web;

import com.gestioneleves.apieleves.config.ApiExceptionHandler;
import com.gestioneleves.apieleves.controller.NoteController;
import com.gestioneleves.apieleves.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NoteControllerValidationTest {

    private MockMvc mockMvc;
    private NoteService noteService;

    @BeforeEach
    void setup() {
        noteService = Mockito.mock(NoteService.class);
        NoteController controller = new NoteController(noteService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void postNote_invalidPayload_returns400ValidationError() throws Exception {
        String body = "{}"; // missing fields: dateNote, coefNote (>0), eleveId, matiereId

        mockMvc.perform(post("/api/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fields.dateNote").exists())
                .andExpect(jsonPath("$.fields.coefNote").exists())
                .andExpect(jsonPath("$.fields.eleveId").exists())
                .andExpect(jsonPath("$.fields.matiereId").exists());

        Mockito.verifyNoInteractions(noteService);
    }
}
