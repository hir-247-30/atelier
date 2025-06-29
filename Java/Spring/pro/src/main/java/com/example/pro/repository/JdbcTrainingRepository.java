package com.example.pro.repository;

import com.example.pro.entity.TrainingEntity;
import com.example.pro.input.TrainingInput;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<TrainingEntity> create(TrainingInput trainingInput) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        String sql = "INSERT INTO t_training (title, start_date_time, end_date_time, reserved, capacity) VALUES (?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, trainingInput.getTitle());
            statement.setTimestamp(2, new java.sql.Timestamp(trainingInput.getStartDateTime().getTime()));
            statement.setTimestamp(3, new java.sql.Timestamp(trainingInput.getEndDateTime().getTime()));
            statement.setInt(4, trainingInput.getReserved());
            statement.setInt(5, trainingInput.getCapacity());
            return statement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();

        if (generatedId == null) return Optional.empty();
        
        return this.select(String.valueOf(generatedId.longValue()));
    }

    @Override
    public Optional<TrainingEntity> update(String id, TrainingInput trainingInput) {
        String sql = "UPDATE t_training SET title = ?, start_date_time = ?, end_date_time = ?, reserved = ?, capacity = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, trainingInput.getTitle(),
                new java.sql.Timestamp(trainingInput.getStartDateTime().getTime()),
                new java.sql.Timestamp(trainingInput.getEndDateTime().getTime()),
                trainingInput.getReserved(),
                trainingInput.getCapacity(),
                id);

        if (updated == 0) return Optional.empty();

        return this.select(id);
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM t_training WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
