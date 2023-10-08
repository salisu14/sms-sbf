package com.sulbasoft.mbims.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sulbasoft.mbims.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
}

