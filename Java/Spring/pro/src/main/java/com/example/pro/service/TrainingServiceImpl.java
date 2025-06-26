package com.example.pro.service;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public TrainingEntity findById(String id) {
        return trainingRepository.select(id);
    }

    @Override
    public List<TrainingEntity> findAll() {
        return trainingRepository.selectAll();
    }

    @Override
    public String create() {
        return trainingRepository.create();
    }

    @Override
    public void update(String id) {
        trainingRepository.update(id);
    }

    @Override
    public void delete(String id) {
        trainingRepository.delete(id);
    }
}
