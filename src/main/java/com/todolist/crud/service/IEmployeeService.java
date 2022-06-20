package com.todolist.crud.service;

import com.todolist.crud.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {

    public Employee saveEmployee(Employee employee);

    public Employee updateEmployee(Employee employee, Long id);

    public Boolean deleteEmployee(Long id);

    public Page<Employee> getAllEmployee(Pageable pageable);

    public Employee getEmployeeById(Long id);

    public Employee getTaskByEmployeeId(Long id);
}
