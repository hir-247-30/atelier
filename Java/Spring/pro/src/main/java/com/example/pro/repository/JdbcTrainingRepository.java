package com.example.pro.repository;

import com.example.pro.entity.TrainingEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// TODO ちゃんとDBから取る
@Repository
public class JdbcTrainingRepository implements TrainingRepository {

    @Override
    public TrainingEntity select(String id) {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setTitle("タイトル" + id);

        return trainingEntity;
    }

    @Override
    public List<TrainingEntity> selectAll() {
        // データベースから取得している想定
        List<TrainingEntity> trainingEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TrainingEntity trainingEntity = new TrainingEntity();
            trainingEntity.setTitle("タイトル" + i);
            trainingEntities.add(trainingEntity);
        }
        return trainingEntities;
    }
}
