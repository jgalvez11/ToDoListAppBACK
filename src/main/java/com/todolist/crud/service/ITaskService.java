package com.todolist.crud.service;

import com.todolist.crud.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {

    public Task saveTask(Task task);

    public Boolean deleteTask(Long id);

    public Task updateTask(Task task, Long id);

    public Page<Task> getAllTask(Pageable pageable);

    public Task getTaskById(Long id);
}
