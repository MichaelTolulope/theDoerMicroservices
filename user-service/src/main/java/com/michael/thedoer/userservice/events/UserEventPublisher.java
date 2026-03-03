package com.michael.thedoer.userservice.events;

import com.michael.thedoer.userservice.dto.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void publishEvent(String topic, Object event) {
        kafkaTemplate.send(topic,event);
    }
}
