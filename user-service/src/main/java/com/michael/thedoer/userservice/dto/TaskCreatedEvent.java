package com.michael.thedoer.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskCreatedEvent {
    String taskId;
    String taskDescription;
}
