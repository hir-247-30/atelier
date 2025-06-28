package com.example.pro.repository;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository {
    Optional<TrainingEntity> select(String id);
    List<TrainingEntity> selectAll();
    Optional<TrainingEntity> create(TrainingInput trainingInput);
    void update(String id);
    void delete(String id);
}
