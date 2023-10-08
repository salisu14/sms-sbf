package com.sulbasoft.mbims.services;

import com.sulbasoft.mbims.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    // Create a new department
    Department createDepartment(Department department);

    // Read a department by ID
    Optional<Department> findDepartmentById(Long id);

    // Read all departments
    List<Department> findAllDepartments();

    // Update a department
    Department updateDepartment(Department department);

    // Delete a department by ID
    void deleteDepartmentById(Long id);
}
