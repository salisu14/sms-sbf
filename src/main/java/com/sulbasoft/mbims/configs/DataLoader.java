package com.sulbasoft.mbims.configs;

import com.sulbasoft.mbims.models.Department;
import com.sulbasoft.mbims.repositories.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    public DataLoader(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Department csc = Department.builder()
                .name("Computer Science")
                .location("New Campus")
                .build();

        Department cyb = Department.builder()
                .name("Cyber Security")
                .location("New Campus")
                .build();

        Department cit = Department.builder()
                .name("Information Technology")
                .location("Kandahar")
                .build();

        Department cse = Department.builder()
                .name("Software Engineering")
                .location("New Campus")
                .build();

        // Save all departments to the database
        departmentRepository.saveAll(List.of(csc, cyb, cit, cse));
    }
}
