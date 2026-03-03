package com.tp1.monolito.tarefas.application.dto;

import com.tp1.monolito.tarefas.domain.model.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class TaskRequest {

    @NotBlank(message = "title e obrigatorio")
    @Size(max = 120, message = "title deve ter no maximo 120 caracteres")
    private String title;

    @Size(max = 500, message = "description deve ter no maximo 500 caracteres")
    private String description;

    @NotNull(message = "status e obrigatorio")
    private TaskStatus status;

    @NotNull(message = "dueDate e obrigatorio")
    @FutureOrPresent(message = "dueDate deve ser hoje ou uma data futura")
    private LocalDate dueDate;

    public TaskRequest() {
    }

    public TaskRequest(String title, String description, TaskStatus status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

