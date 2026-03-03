package com.tp1.monolito.tarefas.application.service;

import com.tp1.monolito.tarefas.application.dto.TaskRequest;
import com.tp1.monolito.tarefas.application.dto.TaskResponse;
import java.util.List;

public interface TaskService {
    List<TaskResponse> listAll();

    TaskResponse findById(Long id);

    TaskResponse create(TaskRequest request);

    TaskResponse update(Long id, TaskRequest request);

    void delete(Long id);
}

