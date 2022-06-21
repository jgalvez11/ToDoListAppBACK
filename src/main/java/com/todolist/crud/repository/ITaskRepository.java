package com.todolist.crud.repository;

import com.todolist.crud.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    public List<Task> getTasksByEmployeeId(Long id);
}
