package com.sulbasoft.mbims.services;

import java.util.List;
import java.util.Optional;

import com.sulbasoft.mbims.models.Employee;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(Long id);

    void delete(Employee employee);
}