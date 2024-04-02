package com.emp.details.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.emp.details.entity.Employees;
import com.emp.details.repository.EmployeeRepository;
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	 public List<Employees> getAllEmployees() {
		 
	        return employeeRepository.findAll();
	    }

	    public Employees addEmployee(Employees employee) {
	        return employeeRepository.save(employee);
	    }

	    public Employees updateEmployee(Long id, Employees updatedEmployee) throws NotFoundException {
	    	Employees existingEmployee = employeeRepository.findById(id)
	                .orElseThrow(() -> new NotFoundException());
	        existingEmployee.setName(updatedEmployee.getName());
	        existingEmployee.setDob(updatedEmployee.getDob());
	        existingEmployee.setGender(updatedEmployee.getGender());
	        existingEmployee.setCountry(updatedEmployee.getCountry());
	        existingEmployee.setState(updatedEmployee.getState());
	        existingEmployee.setAddressLine1(updatedEmployee.getAddressLine1());
	        existingEmployee.setAddressLine2(updatedEmployee.getAddressLine2());
	        existingEmployee.setCity(updatedEmployee.getCity());
	        existingEmployee.setAboutMe(updatedEmployee.getAboutMe());
	        existingEmployee.setActive(updatedEmployee.isActive());
	        return employeeRepository.save(existingEmployee);
	    }

	    public Employees updateEmployeeStatus(Long id, boolean active) throws NotFoundException {
	    	Employees existingEmployee = employeeRepository.findById(id)
	                .orElseThrow(() -> new NotFoundException());
	        existingEmployee.setActive(active);
	        return employeeRepository.save(existingEmployee);
	    }

	    public Employees getEmployeeById(Long id) throws NotFoundException {
	        return employeeRepository.findById(id)
	                .orElseThrow(() -> new NotFoundException());
	    }

		public List<Employees> getAll() {
			return employeeRepository.findAll();
		}

}
