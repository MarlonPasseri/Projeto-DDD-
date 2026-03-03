package com.tp1.monolito.tarefas.application.mapper;

import com.tp1.monolito.tarefas.application.dto.TaskRequest;
import com.tp1.monolito.tarefas.application.dto.TaskResponse;
import com.tp1.monolito.tarefas.domain.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest request) {
        Task task = new Task();
        copyToEntity(request, task);
        return task;
    }

    public void copyToEntity(TaskRequest request, Task task) {
        task.setTitle(request.getTitle().trim());
        task.setDescription(normalizeDescription(request.getDescription()));
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }
        return description.trim();
    }
}

