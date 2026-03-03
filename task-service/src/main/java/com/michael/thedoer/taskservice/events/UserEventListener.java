package com.michael.thedoer.taskservice.events;

import com.michael.thedoer.taskservice.dto.UserCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {

    @KafkaListener(topics = "user.created")
    public void listen(UserCreatedEvent event) {
        System.out.println("Received event: " + event);
    }
}
