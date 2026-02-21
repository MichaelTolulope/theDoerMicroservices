package com.michael.thedoer.taskservice.service;

import com.michael.thedoer.taskservice.dto.TaskDto;
import com.michael.thedoer.taskservice.repository.TaskRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WebClient webClient;

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    public ResponseEntity<?> getTasksByUserId(String userId, int page, int size) {
        boolean userExists = Boolean.TRUE.equals(webClient.get()
                .uri("http://user-service/api/users/user-exists/{id}", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());

        if(!userExists) return null;

        Pageable pageRequest = PageRequest.of(page, size);
        List<TaskDto> taskList = taskRepository.findTasksByUserId(userId, pageRequest).getContent();
        List<TaskDto> taskListToBeReturned = taskList.stream().map(task-> TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .userId(task.getUserId())
                .completed(task.isCompleted())
                .build()).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(taskListToBeReturned);
    }

    public ResponseEntity<?> fallbackUser(String userId, int page, int size, Throwable t) {
        System.out.println("Fallback method called due to: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "User service is currently unavailable. Please try again later.", "error", t.getMessage()));
    }

    public TaskDto getTaskById(String id) {
        TaskDto taskFound = taskRepository.findById(id).orElse(null);
        if (taskFound == null) return null;
        return TaskDto.builder()
                .id(taskFound.getId())
                .title(taskFound.getTitle())
                .description(taskFound.getDescription())
                .userId(taskFound.getUserId())
                .completed(taskFound.isCompleted())
                .build();
    }

    @CircuitBreaker(name = "userServiceTaskCreation", fallbackMethod = "fallbackUserTaskCreation")
    public ResponseEntity<?> addTask(TaskDto taskDto) {
        long userId = Long.parseLong(taskDto.getUserId());

//        check with user service if user exists
        boolean userExists = webClient.get()
                .uri("http://user-service/api/users/user-exists/{id}", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        System.out.println("response from user service "+userExists);

        if(!userExists) return null;


        var taskCreated = taskRepository.save(taskDto);
        return ResponseEntity.status(200).body(TaskDto.builder()
                .id(taskCreated.getId())
                .title(taskCreated.getTitle())
                .description(taskCreated.getDescription())
                .userId(taskCreated.getUserId())
                .completed(taskCreated.isCompleted())
                .build());
    }

    public ResponseEntity<?> fallbackUserTaskCreation(TaskDto taskDto, Throwable t) {
        System.out.println("Fallback method for task creation called due to: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "User service is currently unavailable. Cannot create task. Please try again later.", "error", t.getMessage()));
    }
}
