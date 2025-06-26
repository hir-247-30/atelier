package com.example.pro.repository;

import com.example.pro.entity.TrainingEntity;

import java.util.List;

public interface TrainingRepository {
    TrainingEntity select(String id);
    List<TrainingEntity> selectAll();
}
