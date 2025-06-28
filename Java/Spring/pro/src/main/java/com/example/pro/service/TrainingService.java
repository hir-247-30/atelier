package com.example.pro.service;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import java.util.List;
import java.util.Optional;

public interface TrainingService {
    Optional<TrainingEntity> findById(String id);
    List<TrainingEntity> findAll();
    Optional<TrainingEntity> create(TrainingInput trainingInput);
    void update(String id);
    void delete(String id);
}
