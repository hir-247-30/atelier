package com.example.pro.repository;

import com.example.pro.entity.TrainingEntity;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO ちゃんとDBから取る
@Repository
public class JdbcTrainingRepository implements TrainingRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTrainingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<TrainingEntity> select(String id) {
        String query = "SELECT * FROM t_training WHERE id = ?";
        List<TrainingEntity> result = jdbcTemplate.query(query, new DataClassRowMapper<>(TrainingEntity.class), id);

        return result.stream().findFirst();
    }

    @Override
    public List<TrainingEntity> selectAll() {
        String query = "SELECT * FROM t_training";

        return jdbcTemplate.query(query, new DataClassRowMapper<>(TrainingEntity.class));
    }

    @Override
    public String create() {
        return "";
    }

    @Override
    public void update(String id) {
    }

    @Override
    public void delete(String id) {
    }
}
