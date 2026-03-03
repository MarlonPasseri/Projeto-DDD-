package com.tp1.monolito.tarefas.infrastructure.persistence;

import com.tp1.monolito.tarefas.domain.model.Task;
import com.tp1.monolito.tarefas.domain.repository.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Long>, TaskRepository {
}

