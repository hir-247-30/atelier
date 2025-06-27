package com.example.pro.controller;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<TrainingEntity> getTraining(@PathVariable String id) {
        return this.trainingService.findById(id);
    }

    @GetMapping("/trainings")
    public List<TrainingEntity> getTrainings() {
        return this.trainingService.findAll();
    }

    @PostMapping("/training")
    @ResponseStatus(HttpStatus.CREATED)
    public String createTraining() {
        return this.trainingService.create();
    }

    @PutMapping("/training/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTraining(@PathVariable String id) {
        this.trainingService.update(id);
    }

    @DeleteMapping("/training/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraining(@PathVariable String id) {
        this.trainingService.delete(id);
    }
}
