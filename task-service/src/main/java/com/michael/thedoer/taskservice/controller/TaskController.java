package com.michael.thedoer.taskservice.controller;

import com.michael.thedoer.taskservice.dto.TaskDto;
import com.michael.thedoer.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/user/{userId}")
    public List<TaskDto> getTasks(@PathVariable String userId,
                                  @RequestParam int page, int size) {
        return taskService.getTasksByUserId(userId, page, size);

    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/")
    public TaskDto addTask(@RequestBody TaskDto taskDto) {
        return taskService.addTask(taskDto);
    }
}
