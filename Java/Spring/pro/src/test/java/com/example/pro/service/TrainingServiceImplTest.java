package com.example.pro.service;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import com.example.pro.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private TrainingEntity sampleTraining;
    private TrainingInput sampleInput;

    @BeforeEach
    void setUp() {
        sampleTraining = new TrainingEntity();
        sampleTraining.setId("1");
        sampleTraining.setTitle("Spring Boot Training");
        sampleTraining.setStartDateTime(LocalDateTime.now());
        sampleTraining.setEndDateTime(LocalDateTime.now().plusHours(2));
        sampleTraining.setReserved(5);
        sampleTraining.setCapacity(10);

        sampleInput = new TrainingInput();
        sampleInput.setTitle("New Training");
        sampleInput.setStartDateTime(new Date());
        sampleInput.setEndDateTime(new Date());
        sampleInput.setReserved(0);
        sampleInput.setCapacity(20);
    }

    @Test
    void findById_WhenTrainingExists_ReturnsTraining() {
        when(trainingRepository.select("1")).thenReturn(Optional.of(sampleTraining));

        Optional<TrainingEntity> result = trainingService.findById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("Spring Boot Training", result.get().getTitle());
        verify(trainingRepository, times(1)).select("1");
    }

    @Test
    void findById_WhenTrainingNotExists_ReturnsEmpty() {
        when(trainingRepository.select("999")).thenReturn(Optional.empty());

        Optional<TrainingEntity> result = trainingService.findById("999");

        assertTrue(result.isEmpty());
        verify(trainingRepository, times(1)).select("999");
    }

    @Test
    void findAll_ReturnsAllTrainings() {
        TrainingEntity training1 = new TrainingEntity();
        training1.setId("1");
        training1.setTitle("Training 1");

        TrainingEntity training2 = new TrainingEntity();
        training2.setId("2");
        training2.setTitle("Training 2");

        List<TrainingEntity> expectedTrainings = Arrays.asList(training1, training2);

        when(trainingRepository.selectAll()).thenReturn(expectedTrainings);

        List<TrainingEntity> result = trainingService.findAll();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Training 1", result.get(0).getTitle());
        assertEquals("2", result.get(1).getId());
        assertEquals("Training 2", result.get(1).getTitle());
        verify(trainingRepository, times(1)).selectAll();
    }

    @Test
    void create_WithValidInput_ReturnsCreatedTraining() {
        when(trainingRepository.create(any(TrainingInput.class))).thenReturn(Optional.of(sampleTraining));

        Optional<TrainingEntity> result = trainingService.create(sampleInput);

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("Spring Boot Training", result.get().getTitle());
        verify(trainingRepository, times(1)).create(sampleInput);
    }

    @Test
    void create_WhenCreationFails_ReturnsEmpty() {
        when(trainingRepository.create(any(TrainingInput.class))).thenReturn(Optional.empty());

        Optional<TrainingEntity> result = trainingService.create(sampleInput);

        assertTrue(result.isEmpty());
        verify(trainingRepository, times(1)).create(sampleInput);
    }

    @Test
    void update_CallsRepositoryUpdate() {
        trainingService.update("1", sampleInput);

        verify(trainingRepository, times(1)).update("1", sampleInput);
    }

    @Test
    void delete_CallsRepositoryDelete() {
        trainingService.delete("1");

        verify(trainingRepository, times(1)).delete("1");
    }
}