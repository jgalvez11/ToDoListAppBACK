package com.todolist.crud.service.impl;

import com.todolist.crud.model.Task;
import com.todolist.crud.repository.ITaskRepository;
import com.todolist.crud.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements ITaskService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITaskRepository iTaskRepository;

    @Override
    @Transactional
    public Task saveTask(Task task) {
        try {
            return iTaskRepository.save(task);
        } catch (Exception e) {
            log.error("Error saving task: ", task.getDescription());
            log.error("Error: ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean deleteTask(Long id) {
        try {
            Boolean response = Boolean.TRUE;

            if (iTaskRepository.existsById(id)) {
                iTaskRepository.deleteById(id);
            } else {
                response = Boolean.FALSE;
            }

            return response;
        } catch (Exception e) {
            log.error("Error deleting task: ", id);
            log.error("Error: ", e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    @Transactional
    public Task updateTask(Task task, Long id) {
        Task taskToUpdate = null;
        try {
            if (task.getId() != null && iTaskRepository.existsById(id)) {
                Task taskDB = iTaskRepository.findById(id).orElse(null);

                taskToUpdate = task.validateDataToUpdate(taskDB, task);
                return iTaskRepository.save(taskToUpdate);
            }

            return null;
        } catch (Exception e) {
            log.error("Error updating task: ", id);
            log.error("Error: ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> getAllTask(Pageable pageable) {
        Page<Task> response = iTaskRepository.findAll(pageable);

        if (response.getTotalElements() > 0) {
            response.forEach(task -> {
                task.setDaysLate(task.validateDaysToLate(task));
            });
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {

        Task response = iTaskRepository.findById(id).orElse(null);

        if (response != null) {
            response.setDaysLate(response.validateDaysToLate(response));
        }

        return response;
    }
}
