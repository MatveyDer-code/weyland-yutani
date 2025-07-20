package controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import starter.SyntheticHumanStarterApplication;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import starter.controller.CommandController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommandController.class)
@ContextConfiguration(classes = SyntheticHumanStarterApplication.class)
class CommandControllerErrorHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenInvalidCommand_thenReturnsBadRequestAndErrorResponse() throws Exception {
        String invalidCommandJson = """
            {
              "description": "",
              "priority": null,
              "author": "",
              "date": null
            }
        """;

        mockMvc.perform(post("/commands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCommandJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }
}