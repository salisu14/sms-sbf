package com.sulbasoft.mbims.controllers;

import com.sulbasoft.mbims.exceptions.EmployeeNotFoundException;
import com.sulbasoft.mbims.models.Department;
import com.sulbasoft.mbims.models.Employee;
import com.sulbasoft.mbims.services.DepartmentService;
import com.sulbasoft.mbims.services.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/create")
    public String createEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());

        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(departments);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        return "employees/form";
    }

    @PostMapping
    public String createEmployee(@Valid @ModelAttribute Employee employee,
                                 @RequestParam("id") Long department_id,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "employees/form";
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Department ID: " + department_id);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        // Retrieve the department from the database based on the departmentId
        Optional<Department> departmentOptional = departmentService.findDepartmentById(department_id);

        if (departmentOptional.isPresent()) {
            Department existingDept = departmentOptional.get();

            Employee employeeToSave = new Employee();
            employeeToSave.setEmail(employee.getEmail());
            employeeToSave.setName(employee.getName());

            // Set the selected department on the employee
            employeeToSave.setDepartment(existingDept);

            existingDept.addEmployee(employeeToSave);

            // Save the employee
            employeeService.saveEmployee(employeeToSave);

            // Add a success message to the RedirectAttributes
            redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully");

            return "redirect:/employees";
        } else {
            // Department not found, add an error message and return to the form
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found with ID: " + department_id);
            return "redirect:/employees/form"; // Redirect back to the form with the error message
        }
    }


    @GetMapping("/edit/{id}")
    public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findById(id).get();

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(employee);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);

        model.addAttribute("employee", employee);

        return "employees/edit-form";
    }

    @PostMapping("/update")
    public String updateEmployee(@Valid Employee employee,
                                 @RequestParam("id") Long department_id,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "employees/edit-form";
        }

        Employee updatedEmployee = employeeService.updateEmployee(employee);

        if (updatedEmployee != null) {
            // Add a success message to the RedirectAttributes
            redirectAttributes.addFlashAttribute("successMessage", "Employee information updated successfully");
        } else {
            // Add a warning message to the RedirectAttributes
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating this employee! Please check all employee properties and try again.");
            return "employees/edit-form";
        }

        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployeeById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);

        Optional<Department> department = departmentService.findDepartmentById(employee.get().getDepartment().getId());

        if (employee.isPresent()) {
            employeeService.delete(employee.get());

            // Remove department employee association
            department.get().removeEmployee(employee.get());

            // Add a success message to the RedirectAttributes
            redirectAttributes.addFlashAttribute("successMessage", "Employee information updated successfully");
        }

        return "redirect:/employees";
    }

    @GetMapping("/dump-data")
    public void dumpData(HttpServletResponse response) throws IOException {
        // Data to be dumped
//        String data = "Hello, world! This is data to be dumped.";

        Employee data = employeeService.findById(1L).orElseThrow(() -> new EmployeeNotFoundException("No employee found with this id"));

        // Set the response content type
        response.setContentType("text/plain");

        // Get the response output stream
        try (var outputStream = response.getOutputStream()) {
            // Write the data to the response output stream

            outputStream.write(data.toString().getBytes());

            outputStream.flush();
        }
    }
}
