package com.tp1.monolito.tarefas.interfaces.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tp1.monolito.tarefas.infrastructure.persistence.TaskJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskJpaRepository taskJpaRepository;

    @BeforeEach
    void cleanDatabase() {
        taskJpaRepository.deleteAll();
    }

    @Test
    void shouldCreateAndListTasks() throws Exception {
        String payload = """
                {
                  "title": "Entregar etapa 1",
                  "description": "Monolito Spring + React",
                  "status": "TODO",
                  "dueDate": "2030-01-10"
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Entregar etapa 1"));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldReturnBadRequestWhenPayloadIsInvalid() throws Exception {
        String payload = """
                {
                  "title": "",
                  "description": "descricao qualquer",
                  "status": "TODO",
                  "dueDate": "2030-01-10"
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}

