package com.emp.details.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.details.entity.Employees;
import com.emp.details.service.EmployeeService;



@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/employees")
public class EmployessController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String getAllEmployees(Model model) {
        List<Employees> employees = employeeService.getAllEmployees();
        model.addAttribute("listEmployees", employees);
        return "emp"; 
    }
    
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
       
    	Employees employee = new Employees();
        model.addAttribute("employee", employee);
        return "new_employee";
    }
  
    @PostMapping("/create")
    public String addEmployee(@ModelAttribute Employees employee) {
    	System.out.println("--------------------"+employee.getDob());
    	employee.setActive(true);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        employeeService.addEmployee(employee);
        return "redirect:/employees/"; 
    }

    @GetMapping("/update/{id}")
    public String updateEmployeeForm(@PathVariable Long id, Model model) throws NotFoundException {
        Employees employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee"; 
    }

    @PutMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employees employee) {
        try {
            employeeService.updateEmployee(id, employee);
            return "redirect:/employees/";
        } catch (NotFoundException e) {
            return "errorPage";
        }
    }

    @PutMapping("/{id}/status")
    public Employees updateEmployeeStatus(@PathVariable Long id, @RequestParam boolean active) throws NotFoundException {
        return employeeService.updateEmployeeStatus(id, active);
    }
}


