package com.example.pro.integration;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import com.example.pro.repository.TrainingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingRepository trainingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainingEntity sampleTraining;
    private TrainingInput sampleInput;

    @BeforeEach
    void setUp() {
        sampleTraining = new TrainingEntity();
        sampleTraining.setId("1");
        sampleTraining.setTitle("Integration Test Training");
        sampleTraining.setReserved(3);
        sampleTraining.setCapacity(15);

        sampleInput = new TrainingInput();
        sampleInput.setTitle("New Integration Training");
        sampleInput.setStartDateTime(new Date());
        sampleInput.setEndDateTime(new Date());
        sampleInput.setReserved(0);
        sampleInput.setCapacity(20);
    }

    @Test
    void controllerServiceIntegration_GetTraining_ReturnsTraining() throws Exception {
        when(trainingRepository.select("1")).thenReturn(Optional.of(sampleTraining));

        mockMvc.perform(get("/training/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Integration Test Training"))
                .andExpect(jsonPath("$.reserved").value(3))
                .andExpect(jsonPath("$.capacity").value(15));

        verify(trainingRepository, times(1)).select("1");
    }

    @Test
    void controllerServiceIntegration_GetAllTrainings_ReturnsAllTrainings() throws Exception {
        TrainingEntity training1 = new TrainingEntity();
        training1.setId("1");
        training1.setTitle("Training 1");

        TrainingEntity training2 = new TrainingEntity();
        training2.setId("2");
        training2.setTitle("Training 2");

        List<TrainingEntity> trainings = Arrays.asList(training1, training2);

        when(trainingRepository.selectAll()).thenReturn(trainings);

        mockMvc.perform(get("/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Training 1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].title").value("Training 2"));

        verify(trainingRepository, times(1)).selectAll();
    }

    @Test
    void controllerServiceIntegration_CreateTraining_ReturnsCreatedTraining() throws Exception {
        when(trainingRepository.create(any(TrainingInput.class))).thenReturn(Optional.of(sampleTraining));

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Integration Test Training"));

        verify(trainingRepository, times(1)).create(any(TrainingInput.class));
    }

    @Test
    void controllerServiceIntegration_UpdateTraining_CallsRepositoryUpdate() throws Exception {
        when(trainingRepository.update(eq("1"), any(TrainingInput.class))).thenReturn(Optional.of(sampleTraining));

        mockMvc.perform(put("/training/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInput)))
                .andExpect(status().isNoContent());

        verify(trainingRepository, times(1)).update(eq("1"), any(TrainingInput.class));
    }

    @Test
    void controllerServiceIntegration_DeleteTraining_CallsRepositoryDelete() throws Exception {
        mockMvc.perform(delete("/training/1"))
                .andExpect(status().isNoContent());

        verify(trainingRepository, times(1)).delete("1");
    }

    @Test
    void controllerServiceIntegration_GetNonExistentTraining_ReturnsEmpty() throws Exception {
        when(trainingRepository.select("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/training/999"))
                .andExpect(status().isOk());

        verify(trainingRepository, times(1)).select("999");
    }
}