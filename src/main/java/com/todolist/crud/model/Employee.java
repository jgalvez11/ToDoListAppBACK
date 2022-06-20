package com.todolist.crud.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPLOYEE")
    private Long id;

    @NotNull(message = "El campo firstName es requerido")
    @NotEmpty(message = "El campo firstName no puede ser vacío")
    @NotBlank(message = "El campo firstName no puede ser vacío")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull(message = "El campo lastName es requerido")
    @NotEmpty(message = "El campo lastName no puede ser vacío")
    @NotBlank(message = "El campo lastName no puede ser vacío")
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull(message = "El campo email es requerido")
    @NotEmpty(message = "El campo email no puede ser vacío")
    @NotBlank(message = "El campo email no puede ser vacío")
    @Email
    private String email;

    @NotNull
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public Employee() {
        tasks = new ArrayList<Task>();
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    public Employee validateDataToUpdate(Employee employeeDB, Employee newEmployee) {

        newEmployee.setCreatedAt(employeeDB.getCreatedAt());

        if (newEmployee.getFirstName() == null) {
            newEmployee.setFirstName(employeeDB.getFirstName());
        }

        if (newEmployee.getLastName() == null) {
            newEmployee.setLastName(employeeDB.getLastName());
        }

        if (newEmployee.getEmail() == null) {
            newEmployee.setEmail(employeeDB.getEmail());
        }

        return newEmployee;
    }
}
