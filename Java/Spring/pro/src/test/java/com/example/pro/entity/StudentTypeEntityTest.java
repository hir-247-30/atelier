package com.example.pro.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTypeEntityTest {

    private StudentTypeEntity studentTypeEntity;

    @BeforeEach
    void setUp() {
        studentTypeEntity = new StudentTypeEntity();
    }

    @Test
    void testId() {
        String id = "student123";
        studentTypeEntity.setId(id);
        assertEquals(id, studentTypeEntity.getId());
    }

    @Test
    void testCode() {
        String code = "STU001";
        studentTypeEntity.setCode(code);
        assertEquals(code, studentTypeEntity.getCode());
    }

    @Test
    void testName() {
        String name = "Regular Student";
        studentTypeEntity.setName(name);
        assertEquals(name, studentTypeEntity.getName());
    }

    @Test
    void testAllFieldsNull() {
        StudentTypeEntity entity = new StudentTypeEntity();
        assertNull(entity.getId());
        assertNull(entity.getCode());
        assertNull(entity.getName());
    }

    @Test
    void testAllFieldsSet() {
        String id = "123";
        String code = "REG";
        String name = "Regular";
        
        studentTypeEntity.setId(id);
        studentTypeEntity.setCode(code);
        studentTypeEntity.setName(name);
        
        assertEquals(id, studentTypeEntity.getId());
        assertEquals(code, studentTypeEntity.getCode());
        assertEquals(name, studentTypeEntity.getName());
    }
}