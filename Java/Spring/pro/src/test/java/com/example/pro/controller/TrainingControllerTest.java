package com.example.pro.controller;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import com.example.pro.service.TrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@WebMvcTest(TrainingController.class)
class TrainingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingService trainingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void index_ReturnsWelcomeMessage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Proを目指すSpring入門"));
    }

    @Test
    void getTraining_WhenTrainingExists_ReturnsTraining() throws Exception {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setId("1");
        trainingEntity.setTitle("Spring Boot Training");
        trainingEntity.setReserved(5);
        trainingEntity.setCapacity(10);

        when(trainingService.findById("1")).thenReturn(Optional.of(trainingEntity));

        mockMvc.perform(get("/training/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Spring Boot Training"))
                .andExpect(jsonPath("$.reserved").value(5))
                .andExpect(jsonPath("$.capacity").value(10));

        verify(trainingService, times(1)).findById("1");
    }

    @Test
    void getTraining_WhenTrainingNotExists_ReturnsEmpty() throws Exception {
        when(trainingService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/training/999"))
                .andExpect(status().isOk());

        verify(trainingService, times(1)).findById("999");
    }

    @Test
    void getTrainings_ReturnsAllTrainings() throws Exception {
        TrainingEntity training1 = new TrainingEntity();
        training1.setId("1");
        training1.setTitle("Spring Boot Training");

        TrainingEntity training2 = new TrainingEntity();
        training2.setId("2");
        training2.setTitle("JPA Training");

        List<TrainingEntity> trainings = Arrays.asList(training1, training2);

        when(trainingService.findAll()).thenReturn(trainings);

        mockMvc.perform(get("/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Spring Boot Training"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].title").value("JPA Training"));

        verify(trainingService, times(1)).findAll();
    }

    @Test
    void createTraining_WithValidInput_ReturnsCreatedTraining() throws Exception {
        TrainingInput input = new TrainingInput();
        input.setTitle("New Training");
        input.setStartDateTime(new Date());
        input.setEndDateTime(new Date());
        input.setReserved(0);
        input.setCapacity(20);

        TrainingEntity createdTraining = new TrainingEntity();
        createdTraining.setId("1");
        createdTraining.setTitle("New Training");
        createdTraining.setReserved(0);
        createdTraining.setCapacity(20);

        when(trainingService.create(any(TrainingInput.class))).thenReturn(Optional.of(createdTraining));

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("New Training"))
                .andExpect(jsonPath("$.reserved").value(0))
                .andExpect(jsonPath("$.capacity").value(20));

        verify(trainingService, times(1)).create(any(TrainingInput.class));
    }

    @Test
    void createTraining_WithInvalidInput_ReturnsBadRequest() throws Exception {
        TrainingInput input = new TrainingInput();

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());

        verify(trainingService, never()).create(any(TrainingInput.class));
    }

    @Test
    void updateTraining_WithValidInput_ReturnsNoContent() throws Exception {
        TrainingInput input = new TrainingInput();
        input.setTitle("Updated Training");
        input.setStartDateTime(new Date());
        input.setEndDateTime(new Date());
        input.setReserved(5);
        input.setCapacity(15);

        mockMvc.perform(put("/training/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNoContent());

        verify(trainingService, times(1)).update(eq("1"), any(TrainingInput.class));
    }

    @Test
    void updateTraining_WithInvalidInput_ReturnsBadRequest() throws Exception {
        TrainingInput input = new TrainingInput();

        mockMvc.perform(put("/training/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());

        verify(trainingService, never()).update(anyString(), any(TrainingInput.class));
    }

    @Test
    void deleteTraining_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/training/1"))
                .andExpect(status().isNoContent());

        verify(trainingService, times(1)).delete("1");
    }
}