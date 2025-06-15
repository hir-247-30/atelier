package com.example.demo.web;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

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

    private List<TaskItem> taskItems = new ArrayList<>();

    @GetMapping("/resthello")
    public String hello() {
        return """
                Hello It works!
                現在時刻は%sです。
                """.formatted(LocalDateTime.now());
    }

    // 実際は @PutMapping とすること
    @GetMapping("/restadd")
    String addItem(
            @RequestParam("task") String task,
            @RequestParam("deadline") String deadline
    ) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, false);
        taskItems.add(item);

        return "タスクを追加しました。";
    }

    @GetMapping("/restlist")
    String listItems() {
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(", "));

        return result;
    }
}
