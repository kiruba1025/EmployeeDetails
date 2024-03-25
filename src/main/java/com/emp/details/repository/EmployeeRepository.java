package com.emp.details.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.details.entity.Employees;
@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long> {
}
