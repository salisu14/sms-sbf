package com.sulbasoft.mbims.services;

import com.sulbasoft.mbims.models.Department;
import com.sulbasoft.mbims.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository deptRepo;

    public DepartmentServiceImpl(DepartmentRepository deptRepo) {
        this.deptRepo = deptRepo;
    }

    @Override
    public Department createDepartment(Department department) {
        return deptRepo.save(department);
    }

    @Override
    public Optional<Department> findDepartmentById(Long id) {
        return deptRepo.findById(id);
    }

    @Override
    public List<Department> findAllDepartments() {
        return deptRepo.findAll();
    }

    @Override
    public Department updateDepartment(Department department) {
        // Load the existing department from the database
        Department existingDepartment = deptRepo.getReferenceById(department.getId());

        // Update the properties of the existing department
        existingDepartment.setName(department.getName());
        existingDepartment.setLocation(department.getLocation());
        // existingDepartment.setEmployees(department.getEmployees());

        // Save the updated department
        return deptRepo.save(existingDepartment);
    }

    @Override
    public void deleteDepartmentById(Long id) {
        deptRepo.deleteById(id);
    }
}
