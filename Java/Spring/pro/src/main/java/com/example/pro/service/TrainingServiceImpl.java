package com.example.pro.service;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import com.example.pro.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Optional<TrainingEntity> findById(String id) {
        return trainingRepository.select(id);
    }

    @Override
    public List<TrainingEntity> findAll() {
        return trainingRepository.selectAll();
    }

    @Override
    public Optional<TrainingEntity> create(TrainingInput trainingInput) {
        return trainingRepository.create(trainingInput);
    }

    @Override
    public void update(String id, TrainingInput trainingInput) {
        trainingRepository.update(id, trainingInput);
    }

    @Override
    public void delete(String id) {
        trainingRepository.delete(id);
    }
}
