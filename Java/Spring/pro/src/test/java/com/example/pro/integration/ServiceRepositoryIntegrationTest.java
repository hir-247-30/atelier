package com.example.pro.integration;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import com.example.pro.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ServiceRepositoryIntegrationTest {

    @Autowired
    private TrainingService trainingService;

    private TrainingInput sampleInput;

    @BeforeEach
    void setUp() {
        sampleInput = new TrainingInput();
        sampleInput.setTitle("Service Repository Integration Test");
        sampleInput.setStartDateTime(new Date());
        sampleInput.setEndDateTime(new Date(System.currentTimeMillis() + 3600000)); // 1 hour later
        sampleInput.setReserved(0);
        sampleInput.setCapacity(25);
    }

    @Test
    void serviceRepositoryIntegration_CreateAndFindTraining_Success() {
        Optional<TrainingEntity> createdTraining = trainingService.create(sampleInput);

        assertTrue(createdTraining.isPresent());
        assertNotNull(createdTraining.get().getId());
        assertEquals("Service Repository Integration Test", createdTraining.get().getTitle());
        assertEquals(0, createdTraining.get().getReserved());
        assertEquals(25, createdTraining.get().getCapacity());

        String createdId = createdTraining.get().getId();
        Optional<TrainingEntity> foundTraining = trainingService.findById(createdId);

        assertTrue(foundTraining.isPresent());
        assertEquals(createdId, foundTraining.get().getId());
        assertEquals("Service Repository Integration Test", foundTraining.get().getTitle());
    }

    @Test
    void serviceRepositoryIntegration_FindAllTrainings_ReturnsTrainings() {
        trainingService.create(sampleInput);

        TrainingInput secondInput = new TrainingInput();
        secondInput.setTitle("Second Training");
        secondInput.setStartDateTime(new Date());
        secondInput.setEndDateTime(new Date(System.currentTimeMillis() + 7200000)); // 2 hours later
        secondInput.setReserved(5);
        secondInput.setCapacity(30);

        trainingService.create(secondInput);

        List<TrainingEntity> allTrainings = trainingService.findAll();

        assertFalse(allTrainings.isEmpty());
        assertTrue(allTrainings.size() >= 2);

        boolean foundFirst = allTrainings.stream()
                .anyMatch(t -> "Service Repository Integration Test".equals(t.getTitle()));
        boolean foundSecond = allTrainings.stream()
                .anyMatch(t -> "Second Training".equals(t.getTitle()));

        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    void serviceRepositoryIntegration_UpdateTraining_Success() {
        Optional<TrainingEntity> createdTraining = trainingService.create(sampleInput);
        assertTrue(createdTraining.isPresent());

        String trainingId = createdTraining.get().getId();

        TrainingInput updateInput = new TrainingInput();
        updateInput.setTitle("Updated Training Title");
        updateInput.setStartDateTime(new Date());
        updateInput.setEndDateTime(new Date(System.currentTimeMillis() + 5400000)); // 1.5 hours later
        updateInput.setReserved(10);
        updateInput.setCapacity(40);

        trainingService.update(trainingId, updateInput);

        Optional<TrainingEntity> updatedTraining = trainingService.findById(trainingId);

        assertTrue(updatedTraining.isPresent());
        assertEquals("Updated Training Title", updatedTraining.get().getTitle());
        assertEquals(10, updatedTraining.get().getReserved());
        assertEquals(40, updatedTraining.get().getCapacity());
    }

    @Test
    void serviceRepositoryIntegration_DeleteTraining_Success() {
        Optional<TrainingEntity> createdTraining = trainingService.create(sampleInput);
        assertTrue(createdTraining.isPresent());

        String trainingId = createdTraining.get().getId();

        Optional<TrainingEntity> beforeDelete = trainingService.findById(trainingId);
        assertTrue(beforeDelete.isPresent());

        trainingService.delete(trainingId);

        Optional<TrainingEntity> afterDelete = trainingService.findById(trainingId);
        assertTrue(afterDelete.isEmpty());
    }

    @Test
    void serviceRepositoryIntegration_FindNonExistentTraining_ReturnsEmpty() {
        Optional<TrainingEntity> nonExistent = trainingService.findById("999999");
        assertTrue(nonExistent.isEmpty());
    }
}