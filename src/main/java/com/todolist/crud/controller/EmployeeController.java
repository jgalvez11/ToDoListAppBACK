package com.todolist.crud.controller;

import com.todolist.crud.model.Employee;
import com.todolist.crud.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IEmployeeService iEmployeeService;

    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(iEmployeeService.saveEmployee(employee));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(iEmployeeService.updateEmployee(employee, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(iEmployeeService.deleteEmployee(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Employee>> getAllEmployee(@RequestParam(name = "page", defaultValue = "0", required = false) int page) {
        Pageable pageRequest = PageRequest.of(page, 6);
        return ResponseEntity.ok(iEmployeeService.getAllEmployee(pageRequest));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok(iEmployeeService.getEmployeeById(id));
    }

}
