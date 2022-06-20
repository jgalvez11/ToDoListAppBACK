package com.todolist.crud.service.impl;

import com.todolist.crud.model.Employee;
import com.todolist.crud.repository.IEmployeeRepository;
import com.todolist.crud.repository.ITaskRepository;
import com.todolist.crud.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IEmployeeRepository iEmployeeRepository;

    @Autowired
    private ITaskRepository iTaskRepository;

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        try {
            return iEmployeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error saving employee: ", employee.getFirstName());
            log.error("Error: ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee, Long id) {
        Employee employeeToUpdate = null;
        try {
            if (employee.getId() != null && iEmployeeRepository.existsById(id)) {
                Employee employeeDB = iEmployeeRepository.findById(id).orElse(null);

                employeeToUpdate = employee.validateDataToUpdate(employeeDB, employee);
                return iEmployeeRepository.save(employeeToUpdate);
            }

            return null;
        } catch (Exception e) {
            log.error("Error updating employee: ", id);
            log.error("Error: ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean deleteEmployee(Long id) {

        try {
            Boolean response = Boolean.TRUE;

            if (iEmployeeRepository.existsById(id)) {
                iEmployeeRepository.deleteById(id);
            } else {
                response = Boolean.FALSE;
            }

            return response;
        } catch (Exception e) {
            log.error("Error deleting employee: ", id);
            log.error("Error: ", e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> getAllEmployee(Pageable pageable) {
        return iEmployeeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return iEmployeeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getTaskByEmployeeId(Long id) {
        return iEmployeeRepository.getEmployeeByIdWithTasks(id);
    }
}
