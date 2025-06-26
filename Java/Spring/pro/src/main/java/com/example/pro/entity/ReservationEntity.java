package com.example.pro.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class ReservationEntity implements Serializable {
    private String id;
    private String trainingId;
    private TrainingEntity trainingEntity;
    private String studentTypeId;
    private StudentTypeEntity studentType;
    private LocalDateTime reservedDateTime;
    private String name;
    private String phone;
    private String emailAddress;


    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getStudentTypeId() {
        return studentTypeId;
    }

    public void setStudentTypeId(String studentTypeId) {
        this.studentTypeId = studentTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrainingEntity getTraining() {
        return trainingEntity;
    }

    public void setTraining(TrainingEntity trainingEntity) {
        this.trainingEntity = trainingEntity;
    }

    public StudentTypeEntity getStudentType() {
        return studentType;
    }

    public void setStudentType(StudentTypeEntity studentType) {
        this.studentType = studentType;
    }

    public LocalDateTime getReservedDateTime() {
        return reservedDateTime;
    }

    public void setReservedDateTime(LocalDateTime reservedDateTime) {
        this.reservedDateTime = reservedDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
