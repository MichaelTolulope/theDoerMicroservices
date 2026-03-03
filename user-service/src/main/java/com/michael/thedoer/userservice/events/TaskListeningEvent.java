package com.michael.thedoer.userservice.events;

import com.michael.thedoer.userservice.dto.TaskCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskListeningEvent {
    @KafkaListener(topics = "task.created")
    public void listen(TaskCreatedEvent event) {
        System.out.println("Received event: " + event);
    }
}
