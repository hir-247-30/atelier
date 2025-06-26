package com.example.pro.repository;

import com.example.pro.entity.TrainingEntity;

import java.util.List;

public interface TrainingRepository {
    TrainingEntity select(String id);
    List<TrainingEntity> selectAll();
    String create();
    void update(String id);
    void delete(String id);
}
