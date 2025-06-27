package com.example.pro.service;

import com.example.pro.entity.TrainingEntity;
import java.util.List;
import java.util.Optional;

public interface TrainingService {
    Optional<TrainingEntity> findById(String id);
    List<TrainingEntity> findAll();
    String create();
    void update(String id);
    void delete(String id);
}
