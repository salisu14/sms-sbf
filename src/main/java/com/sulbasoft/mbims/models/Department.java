package com.sulbasoft.mbims.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="departments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Size(max = 50, message = "Department name must be less than or equal to 50 characters")
    private String name;

    @NotEmpty(message =  "Location is a required field")
    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employee.setDepartment(null);
        this.employees.remove(employee);
    }

    public void removeEmployees() {
        Iterator<Employee> iterator = this.employees.iterator();

        while(iterator.hasNext()) {
            Employee employee = iterator.next();

            employee.setDepartment(null);

            iterator.remove();
        }
    }
}
