package com.example.pro.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationEntityTest {

    private ReservationEntity reservationEntity;
    private TrainingEntity trainingEntity;
    private StudentTypeEntity studentTypeEntity;

    @BeforeEach
    void setUp() {
        reservationEntity = new ReservationEntity();
        trainingEntity = new TrainingEntity();
        studentTypeEntity = new StudentTypeEntity();
    }

    @Test
    void testId() {
        String id = "reservation123";
        reservationEntity.setId(id);
        assertEquals(id, reservationEntity.getId());
    }

    @Test
    void testTrainingId() {
        String trainingId = "training123";
        reservationEntity.setTrainingId(trainingId);
        assertEquals(trainingId, reservationEntity.getTrainingId());
    }

    @Test
    void testTrainingEntity() {
        trainingEntity.setId("training123");
        reservationEntity.setTraining(trainingEntity);
        assertEquals(trainingEntity, reservationEntity.getTraining());
        assertEquals("training123", reservationEntity.getTraining().getId());
    }

    @Test
    void testStudentTypeId() {
        String studentTypeId = "student123";
        reservationEntity.setStudentTypeId(studentTypeId);
        assertEquals(studentTypeId, reservationEntity.getStudentTypeId());
    }

    @Test
    void testStudentTypeEntity() {
        studentTypeEntity.setId("student123");
        reservationEntity.setStudentType(studentTypeEntity);
        assertEquals(studentTypeEntity, reservationEntity.getStudentType());
        assertEquals("student123", reservationEntity.getStudentType().getId());
    }

    @Test
    void testReservedDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        reservationEntity.setReservedDateTime(dateTime);
        assertEquals(dateTime, reservationEntity.getReservedDateTime());
    }

    @Test
    void testName() {
        String name = "John Doe";
        reservationEntity.setName(name);
        assertEquals(name, reservationEntity.getName());
    }

    @Test
    void testPhone() {
        String phone = "123-456-7890";
        reservationEntity.setPhone(phone);
        assertEquals(phone, reservationEntity.getPhone());
    }

    @Test
    void testEmailAddress() {
        String email = "john.doe@example.com";
        reservationEntity.setEmailAddress(email);
        assertEquals(email, reservationEntity.getEmailAddress());
    }

    @Test
    void testAllFieldsNull() {
        ReservationEntity entity = new ReservationEntity();
        assertNull(entity.getId());
        assertNull(entity.getTrainingId());
        assertNull(entity.getTraining());
        assertNull(entity.getStudentTypeId());
        assertNull(entity.getStudentType());
        assertNull(entity.getReservedDateTime());
        assertNull(entity.getName());
        assertNull(entity.getPhone());
        assertNull(entity.getEmailAddress());
    }
}