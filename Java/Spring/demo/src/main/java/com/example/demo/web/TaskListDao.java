package com.example.demo.web;

import com.example.demo.web.HomeRestController.TaskItem;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

@Service
public class TaskListDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TaskListDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add (TaskItem taskItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tasklist");

        insert.execute(param);
    }

    public void update (TaskItem taskItem) {
        String query = "UPDATE tasklist SET task = ?, deadline = ?, done = ? WHERE id = ?";
        this.jdbcTemplate.update(query,
                taskItem.task(),
                taskItem.deadline(),
                taskItem.done(),
                taskItem.id()
        );
    }

    public void delete (String id) {
        String query = "DELETE FROM tasklist WHERE id = ?";
        this.jdbcTemplate.update(query, id);
    }

    public List<TaskItem> findAll () {
        String query = "SELECT * FROM tasklist";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<TaskItem> taskItems = result.stream()
                .map((Map<String, Object> row) -> new TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("deadline").toString(),
                        (Boolean) row.get("done")))
                .toList();

        return taskItems;
    }
}
