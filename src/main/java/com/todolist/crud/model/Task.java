package com.todolist.crud.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TASK")
    private Long id;

    private Boolean status;

    @NotNull(message = "El campo description es requerido")
    @NotEmpty(message = "El campo description no puede estar vacío")
    @NotBlank(message = "El campo description no puede ser vacío")
    private String description;

    @Column(name = "EXECUTION_DATE")
    private Date executionDate;

    @Column(name = "DAYS_LATE")
    private int daysLate;

    @NotNull
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "task_employee")
    private Employee employee;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        status = Boolean.FALSE;
    }

    public Task validateDataToUpdate(Task taskDB, Task newTask) {

        newTask.setCreatedAt(taskDB.getCreatedAt());

        if (newTask.getStatus() == null) {
            newTask.setStatus(taskDB.getStatus());
        }

        if (newTask.getDescription() == null) {
            newTask.setDescription(taskDB.getDescription());
        }

        if (newTask.getExecutionDate() == null) {
            newTask.setExecutionDate(taskDB.getExecutionDate());
        }

        if (newTask.getEmployee() == null) {
            newTask.setEmployee(taskDB.getEmployee());
        }

        return newTask;
    }

    public Integer validateDaysToLate(Task task) {
        int response = 0;

        if (task.getExecutionDate() != null) {
            response = daysBetween(task.getExecutionDate());
        }

        return response;
    }

    private static int daysBetween(Date one) {
        long difference = (one.getTime() - new Date().getTime()) / 86400000;
        return (int) Math.abs(difference);
    }
}
