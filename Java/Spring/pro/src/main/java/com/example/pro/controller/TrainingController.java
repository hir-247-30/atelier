package com.example.pro.controller;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.service.TrainingService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/")
    public String index() {
        return "Proを目指すSpring入門";
    }

    @GetMapping("/training/{id}")
    public TrainingEntity getTraining(@PathVariable String id) {
        return this.trainingService.findById(id);
    }

    @GetMapping("/trainings")
    public List<TrainingEntity> getTrainings() {
        return this.trainingService.findAll();
    }
}
