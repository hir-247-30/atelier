package com.example.pro.repository;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JdbcTrainingRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcTrainingRepository repository;

    private TrainingEntity sampleTraining;
    private TrainingInput sampleInput;

    @BeforeEach
    void setUp() {
        sampleTraining = new TrainingEntity();
        sampleTraining.setId("1");
        sampleTraining.setTitle("Spring Boot Training");
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
    void select_WhenTrainingExists_ReturnsTraining() {
        List<TrainingEntity> mockResult = Arrays.asList(sampleTraining);
        
        when(jdbcTemplate.query(anyString(), any(DataClassRowMapper.class), eq("1")))
            .thenReturn(mockResult);

        Optional<TrainingEntity> result = repository.select("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("Spring Boot Training", result.get().getTitle());
        
        verify(jdbcTemplate, times(1)).query(
            eq("SELECT * FROM t_training WHERE id = ?"),
            any(DataClassRowMapper.class),
            eq("1")
        );
    }

    @Test
    void select_WhenTrainingNotExists_ReturnsEmpty() {
        when(jdbcTemplate.query(anyString(), any(DataClassRowMapper.class), eq("999")))
            .thenReturn(Arrays.asList());

        Optional<TrainingEntity> result = repository.select("999");

        assertTrue(result.isEmpty());
        
        verify(jdbcTemplate, times(1)).query(
            eq("SELECT * FROM t_training WHERE id = ?"),
            any(DataClassRowMapper.class),
            eq("999")
        );
    }

    @Test
    void selectAll_ReturnsAllTrainings() {
        TrainingEntity training1 = new TrainingEntity();
        training1.setId("1");
        training1.setTitle("Training 1");

        TrainingEntity training2 = new TrainingEntity();
        training2.setId("2");
        training2.setTitle("Training 2");

        List<TrainingEntity> mockResult = Arrays.asList(training1, training2);

        when(jdbcTemplate.query(anyString(), any(DataClassRowMapper.class)))
            .thenReturn(mockResult);

        List<TrainingEntity> result = repository.selectAll();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Training 1", result.get(0).getTitle());
        assertEquals("2", result.get(1).getId());
        assertEquals("Training 2", result.get(1).getTitle());

        verify(jdbcTemplate, times(1)).query(
            eq("SELECT * FROM t_training"),
            any(DataClassRowMapper.class)
        );
    }

    @Test
    void update_WithValidInput_ReturnsUpdatedTraining() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), eq("1")))
            .thenReturn(1);

        List<TrainingEntity> mockSelectResult = Arrays.asList(sampleTraining);
        when(jdbcTemplate.query(anyString(), any(DataClassRowMapper.class), eq("1")))
            .thenReturn(mockSelectResult);

        Optional<TrainingEntity> result = repository.update("1", sampleInput);

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());

        verify(jdbcTemplate, times(1)).update(
            eq("UPDATE t_training SET title = ?, start_date_time = ?, end_date_time = ?, reserved = ?, capacity = ? WHERE id = ?"),
            eq(sampleInput.getTitle()),
            any(),
            any(),
            eq(sampleInput.getReserved()),
            eq(sampleInput.getCapacity()),
            eq("1")
        );
        verify(jdbcTemplate, times(1)).query(
            eq("SELECT * FROM t_training WHERE id = ?"),
            any(DataClassRowMapper.class),
            eq("1")
        );
    }

    @Test
    void update_WhenNoRowsUpdated_ReturnsEmpty() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), eq("999")))
            .thenReturn(0);

        Optional<TrainingEntity> result = repository.update("999", sampleInput);

        assertTrue(result.isEmpty());
        verify(jdbcTemplate, times(1)).update(
            eq("UPDATE t_training SET title = ?, start_date_time = ?, end_date_time = ?, reserved = ?, capacity = ? WHERE id = ?"),
            eq(sampleInput.getTitle()),
            any(),
            any(),
            eq(sampleInput.getReserved()),
            eq(sampleInput.getCapacity()),
            eq("999")
        );
        verify(jdbcTemplate, never()).query(anyString(), any(DataClassRowMapper.class), anyString());
    }

    @Test
    void delete_CallsJdbcTemplateUpdate() {
        repository.delete("1");

        verify(jdbcTemplate, times(1)).update(
            eq("DELETE FROM t_training WHERE id = ?"),
            eq("1")
        );
    }
}