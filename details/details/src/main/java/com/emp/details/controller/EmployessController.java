package com.emp.details.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.details.entity.Employees;
import com.emp.details.service.EmployeeService;



@RestController
@RequestMapping("employee/detatils")
public class EmployessController {
	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/getAll")
    public List<Employees> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

	@PostMapping("/create")
    public Employees addEmployee(@RequestBody Employees employee) {
		employee.setCreatedAt(LocalDateTime.now());
		employee.setUpdatedAt(LocalDateTime.now());
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employees updateEmployee(@PathVariable Long id, @RequestBody Employees employee) throws NotFoundException {
        return employeeService.updateEmployee(id, employee);
    }

    @PutMapping("/{id}/status")
    public Employees updateEmployeeStatus(@PathVariable Long id, @RequestParam boolean active) throws NotFoundException {
        return employeeService.updateEmployeeStatus(id, active);
    }
}
