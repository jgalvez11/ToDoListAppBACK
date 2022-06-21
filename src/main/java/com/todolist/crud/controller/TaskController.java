package com.todolist.crud.controller;

import com.todolist.crud.model.Task;
import com.todolist.crud.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin("*")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITaskService iTaskService;

    @PostMapping("/save")
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(iTaskService.saveTask(task));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(iTaskService.deleteTask(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(iTaskService.updateTask(task, id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Task>> getAllTask(@RequestParam(name = "page", defaultValue = "0", required = false) int page) {
        Pageable pageRequest = PageRequest.of(page, 6);
        return ResponseEntity.ok(iTaskService.getAllTask(pageRequest));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok(iTaskService.getTaskById(id));
    }

    @GetMapping("/getTasksByEmployeeId/{id}")
    public ResponseEntity<List<Task>> getTasksByEmployeeId(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok(iTaskService.getTasksByEmployeeId(id));
    }

}
