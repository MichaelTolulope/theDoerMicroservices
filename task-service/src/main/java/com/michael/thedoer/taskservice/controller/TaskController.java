package com.michael.thedoer.taskservice.controller;

import com.michael.thedoer.taskservice.dto.TaskDto;
import com.michael.thedoer.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTasks(@PathVariable String userId,
                                                  @RequestParam int page, int size) {
        return new ResponseEntity<>(taskService.getTasksByUserId(userId, page, size).getBody(), taskService.getTasksByUserId(userId, page, size).getStatusCode());

    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> addTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.addTask(taskDto), taskService.addTask(taskDto).getStatusCode());
    }
}
