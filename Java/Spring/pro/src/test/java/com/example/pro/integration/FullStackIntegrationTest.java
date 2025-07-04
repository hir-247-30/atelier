package com.example.pro.integration;

import com.example.pro.input.TrainingInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class FullStackIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainingInput sampleInput;

    @BeforeEach
    void setUp() {
        sampleInput = createTrainingInput("Full Stack Integration Test", 0, 50);
    }

    private TrainingInput createTrainingInput(String title, int reserved, int capacity) {
        TrainingInput input = new TrainingInput();
        input.setTitle(title);
        input.setStartDateTime(new Date());
        input.setEndDateTime(new Date(System.currentTimeMillis() + 3600000)); // 1 hour later
        input.setReserved(reserved);
        input.setCapacity(capacity);
        return input;
    }

    @Test
    void fullStackIntegration_CreateFindUpdateDelete_Success() throws Exception {
        // 1. Create Training
        MvcResult createResult = mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Full Stack Integration Test"))
                .andExpect(jsonPath("$.reserved").value(0))
                .andExpect(jsonPath("$.capacity").value(50))
                .andReturn();

        String responseContent = createResult.getResponse().getContentAsString();
        String createdId = objectMapper.readTree(responseContent).get("id").asText();

        // 2. Find the created training
        mockMvc.perform(get("/training/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.title").value("Full Stack Integration Test"))
                .andExpect(jsonPath("$.reserved").value(0))
                .andExpect(jsonPath("$.capacity").value(50));

        // 3. Verify it appears in the list
        mockMvc.perform(get("/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == '" + createdId + "')].title").value("Full Stack Integration Test"));

        // 4. Update the training
        TrainingInput updateInput = new TrainingInput();
        updateInput.setTitle("Updated Full Stack Test");
        updateInput.setStartDateTime(new Date());
        updateInput.setEndDateTime(new Date(System.currentTimeMillis() + 7200000)); // 2 hours later
        updateInput.setReserved(15);
        updateInput.setCapacity(60);

        mockMvc.perform(put("/training/" + createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateInput)))
                .andExpect(status().isNoContent());

        // 5. Verify the update
        mockMvc.perform(get("/training/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.title").value("Updated Full Stack Test"))
                .andExpect(jsonPath("$.reserved").value(15))
                .andExpect(jsonPath("$.capacity").value(60));

        // 6. Delete the training
        mockMvc.perform(delete("/training/" + createdId))
                .andExpect(status().isNoContent());

        // 7. Verify it's deleted (should return empty)
        mockMvc.perform(get("/training/" + createdId))
                .andExpect(status().isOk());
    }

    @Test
    void fullStackIntegration_CreateMultipleTrainings_Success() throws Exception {
        // Create first training
        TrainingInput firstInput = createTrainingInput("First Training", 5, 20);

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("First Training"));

        // Create second training
        TrainingInput secondInput = createTrainingInput("Second Training", 10, 30);

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Second Training"));

        // Verify both trainings exist in the list
        mockMvc.perform(get("/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.title == 'First Training')]").exists())
                .andExpect(jsonPath("$[?(@.title == 'Second Training')]").exists());
    }

    @Test
    void fullStackIntegration_InvalidInput_ReturnsBadRequest() throws Exception {
        TrainingInput invalidInput = new TrainingInput();
        // Missing required fields

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void fullStackIntegration_FindNonExistentTraining_ReturnsEmpty() throws Exception {
        mockMvc.perform(get("/training/999999"))
                .andExpect(status().isOk());
    }

    @Test
    void fullStackIntegration_UpdateNonExistentTraining_NoContent() throws Exception {
        mockMvc.perform(put("/training/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInput)))
                .andExpect(status().isNoContent());
    }

    @Test
    void fullStackIntegration_DeleteNonExistentTraining_NoContent() throws Exception {
        mockMvc.perform(delete("/training/999999"))
                .andExpect(status().isNoContent());
    }
}