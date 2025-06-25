package com.example.pro.repository;

import com.example.pro.entity.Training;

import java.util.ArrayList;
import java.util.List;

public class JdbcTrainingRepository implements TrainingRepository {
    @Override
    public List<Training> selectAll() {
        System.out.println("データベースからデータを取得します");
        // データベースから取得している想定
        List<Training> trainings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Training training = new Training();
            training.setTitle("title_" + i);
            trainings.add(training);
        }
        return trainings;
    }
}
