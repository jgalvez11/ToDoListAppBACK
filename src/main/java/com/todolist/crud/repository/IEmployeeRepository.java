package com.todolist.crud.repository;

import com.todolist.crud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select c from Employee c left join fetch c.tasks f where c.id=?1")
    public Employee getEmployeeByIdWithTasks(Long id);
}
