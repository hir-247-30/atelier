package com.example.pro.repository;

import com.example.pro.entity.Training;

import java.util.List;

public interface TrainingRepository {
    List<Training> selectAll();
}
