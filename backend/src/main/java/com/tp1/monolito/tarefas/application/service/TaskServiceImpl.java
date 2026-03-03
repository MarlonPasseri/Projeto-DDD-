package com.tp1.monolito.tarefas.application.service;

import com.tp1.monolito.shared.exception.ResourceNotFoundException;
import com.tp1.monolito.tarefas.application.dto.TaskRequest;
import com.tp1.monolito.tarefas.application.dto.TaskResponse;
import com.tp1.monolito.tarefas.application.mapper.TaskMapper;
import com.tp1.monolito.tarefas.domain.model.Task;
import com.tp1.monolito.tarefas.domain.repository.TaskRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> listAll() {
        return taskRepository.findAllByOrderByDueDateAscIdAsc()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse findById(Long id) {
        Task task = findTaskOrThrow(id);
        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponse create(TaskRequest request) {
        Task task = taskMapper.toEntity(request);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    @Override
    public TaskResponse update(Long id, TaskRequest request) {
        Task task = findTaskOrThrow(id);
        taskMapper.copyToEntity(request, task);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    @Override
    public void delete(Long id) {
        Task task = findTaskOrThrow(id);
        taskRepository.delete(task);
    }

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task nao encontrada: id=" + id));
    }
}

