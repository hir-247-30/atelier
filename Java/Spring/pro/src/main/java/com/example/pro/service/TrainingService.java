package com.example.pro.service;

import com.example.pro.entity.TrainingEntity;
import java.util.List;

public interface TrainingService {
    TrainingEntity findById(String id);
    List<TrainingEntity> findAll();
    String create();
    void update(String id);
    void delete(String id);
}
