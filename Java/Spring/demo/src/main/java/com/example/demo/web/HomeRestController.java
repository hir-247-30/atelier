package com.example.demo.web;

import com.example.demo.web.TaskListDao;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {
    record TaskItem(
            String id,
            String task,
            String deadline,
            boolean done
    ) {}

    private final TaskListDao dao;
    private List<TaskItem> taskItems = new ArrayList<>();

    @Autowired
    HomeRestController (TaskListDao dao) {
        this.dao = dao;
    }

    @GetMapping("/hello")
    public String hello() {
        return """
                Hello It works!
                現在時刻は%sです。
                """.formatted(LocalDateTime.now());
    }

    // 実際は @PostMapping とすること
    @GetMapping("/add")
    String addItem (
            @RequestParam("task") String task,
            @RequestParam("deadline") String deadline
    ) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, false);
        this.dao.add(item);

        return "タスクを追加しました。";
    }

    // 実際は @PutMapping とすること
    @GetMapping("/update")
    String updateItem (
            @RequestParam("id") String id,
            @RequestParam("task") String task,
            @RequestParam("deadline") String deadline,
            @RequestParam("done") boolean done
    ) {
        TaskItem taskItem = new TaskItem(id, task, deadline, done);
        this.dao.update(taskItem);

        return "タスクを更新しました。";
    }

    // 実際は @DeleteMapping とすること
    @GetMapping("/delete")
    String deleteItems (@RequestParam("id") String id) {
        this.dao.delete(id);

        return "タスクを削除しました。";
    }

    @GetMapping("/list")
    String listItems () {
        List<TaskItem> items = this.dao.findAll();
        String result = items.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(", "));

        return result;
    }
}
