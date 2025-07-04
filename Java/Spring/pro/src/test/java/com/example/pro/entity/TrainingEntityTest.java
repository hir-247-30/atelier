package com.example.pro.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingEntityTest {

    private TrainingEntity trainingEntity;

    @BeforeEach
    void setUp() {
        trainingEntity = new TrainingEntity();
    }

    @Test
    void testReservations() {
        ReservationEntity reservation1 = new ReservationEntity();
        ReservationEntity reservation2 = new ReservationEntity();
        List<ReservationEntity> reservations = Arrays.asList(reservation1, reservation2);
        
        trainingEntity.setReservations(reservations);
        assertEquals(reservations, trainingEntity.getReservations());
        assertEquals(2, trainingEntity.getReservations().size());
    }

    @Test
    void testReservationsNull() {
        trainingEntity.setReservations(null);
        assertNull(trainingEntity.getReservations());
    }

    @Test
    void testDateTimeFields() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        
        trainingEntity.setStartDateTime(startTime);
        trainingEntity.setEndDateTime(endTime);
        
        assertEquals(startTime, trainingEntity.getStartDateTime());
        assertEquals(endTime, trainingEntity.getEndDateTime());
    }
}