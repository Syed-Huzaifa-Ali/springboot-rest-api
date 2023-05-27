package com.crudapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudapi.model.Employee;
import com.crudapi.respository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping("/employees")
	public String createNewEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return "Employee created in database";
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {

		List<Employee> empList = new ArrayList<>();

		employeeRepository.findAll().forEach(empList::add);

		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);

	}

	@GetMapping("/employees/{empid}")
	public ResponseEntity<?> getEmployeeById(@PathVariable long empid) {

		Optional<Employee> emp = employeeRepository.findById(empid);

		if (emp.isPresent()) {
			return new ResponseEntity<>(emp.get(), HttpStatus.FOUND);
		} else {
			String errorMessage = "Employee with ID " + empid + " not found";
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/employees/{empid}")
	public String updateEmployee(@PathVariable long empid, @RequestBody Employee employee) {

		Optional<Employee> emp = employeeRepository.findById(empid);

		if (emp.isPresent()) {
			Employee existEmp = emp.get();
			existEmp.setEmp_name(employee.getEmp_name());
			existEmp.setEmp_salary(employee.getEmp_salary());
			existEmp.setEmp_age(employee.getEmp_age());
			existEmp.setEmp_city(employee.getEmp_city());

			employeeRepository.save(existEmp);

			return "Employee has been updated successfully against ID: " + empid;
		} else {

			return "Employee doesn\'t exist in the database";
		}
	}

	@DeleteMapping("/employees/{empid}")
	public String deleteEmployeeByEmpId(@PathVariable long empid) {

		Optional<Employee> emp = employeeRepository.findById(empid);

		if (emp.isPresent()) {

			employeeRepository.deleteById(empid);

			return "Employee has been deleted successfully";
		}

		else {
			return "Employee ID doesn\'t exist in the database";
		}
	}

	@DeleteMapping("/employees")
	public String deleteAllEmployee() {

		employeeRepository.deleteAll();

		return "All Employees has been deleted successfully";
	}
}
