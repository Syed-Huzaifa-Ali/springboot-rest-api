package com.crudapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudapi.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
