package com.michael.thedoer.taskservice.repository;

import com.michael.thedoer.taskservice.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<TaskDto, String> {
    public Page<TaskDto> findTasksByUserId(String userId, Pageable pageable);

}
