package com.sulbasoft.mbims.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sulbasoft.mbims.models.Employee;
import com.sulbasoft.mbims.repositories.EmployeeRepository;
import com.sulbasoft.mbims.exceptions.EmployeeNotFoundException;

import lombok.AllArgsConstructor;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(final EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {

		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee updatedEmployee) {
		// Retrieve the existing employee by ID
		Employee existingEmployee = employeeRepository.findById(updatedEmployee.getId()).orElseThrow(
				() -> new EmployeeNotFoundException("Employee not found with ID: " + updatedEmployee.getId()));

		// Update the properties of the existing employee
		existingEmployee.setName(updatedEmployee.getName());
		existingEmployee.setEmail(updatedEmployee.getEmail());
		existingEmployee.setDepartment(updatedEmployee.getDepartment());

		// Save the updated employee back to the database
		return employeeRepository.save(existingEmployee);
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void delete(Employee employee) {
		employeeRepository.delete(employee);
	}
}