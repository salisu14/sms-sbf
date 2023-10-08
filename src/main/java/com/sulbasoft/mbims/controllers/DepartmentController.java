package com.sulbasoft.mbims.controllers;

import com.sulbasoft.mbims.exceptions.DepartmentNotFoundException;
import com.sulbasoft.mbims.models.Department;
import com.sulbasoft.mbims.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Display a list of all departments
    @GetMapping
    public String listDepartments(Model model) {
        List<Department> departments = departmentService.findAllDepartments();

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(departments);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        model.addAttribute("departments", departments);
        return "departments/list";
    }

    // Display a form to create a new department
    @GetMapping("/create")
    public String createDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "departments/create-form";
    }

    // Process the creation of a new department
    @PostMapping()
    public String createDepartment(@Valid @ModelAttribute("department") Department department, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "departments/create-form";
        }
        departmentService.createDepartment(department);
        redirectAttributes.addFlashAttribute("successMessage", "Department created successfully");
        return "redirect:/departments";
    }

    // Display a form to edit a department
    @GetMapping("/edit/{id}")
    public String editDepartmentForm(@PathVariable("id") Long id, Model model) {
        Department department = departmentService.findDepartmentById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));
        model.addAttribute("department", department);
        return "departments/edit-form";
    }

    // Process the update of a department
    @PostMapping("/update")
    public String editDepartment(@Valid @ModelAttribute("department") Department department, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "departments/edit-form";
        }
        departmentService.updateDepartment(department);
        redirectAttributes.addFlashAttribute("successMessage", "Department updated successfully");
        return "redirect:/departments";
    }

    // Delete a department
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        departmentService.deleteDepartmentById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully");
        return "redirect:/departments";
    }
}
