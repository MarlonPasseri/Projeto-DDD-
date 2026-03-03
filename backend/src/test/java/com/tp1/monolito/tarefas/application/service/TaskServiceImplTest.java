package com.tp1.monolito.tarefas.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tp1.monolito.shared.exception.ResourceNotFoundException;
import com.tp1.monolito.tarefas.application.dto.TaskRequest;
import com.tp1.monolito.tarefas.application.dto.TaskResponse;
import com.tp1.monolito.tarefas.application.mapper.TaskMapper;
import com.tp1.monolito.tarefas.domain.model.Task;
import com.tp1.monolito.tarefas.domain.model.TaskStatus;
import com.tp1.monolito.tarefas.domain.repository.TaskRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, new TaskMapper());
    }

    @Test
    void shouldCreateTask() {
        TaskRequest request = new TaskRequest(
                "Estudar Spring",
                "Concluir API REST",
                TaskStatus.TODO,
                LocalDate.now().plusDays(1)
        );

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(1L);
            return task;
        });

        TaskResponse response = taskService.create(request);

        assertEquals(1L, response.getId());
        assertEquals("Estudar Spring", response.getTitle());
        assertEquals(TaskStatus.TODO, response.getStatus());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.findById(99L));
    }
}

