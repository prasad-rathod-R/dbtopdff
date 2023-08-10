package com.topdf.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topdf.project.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
