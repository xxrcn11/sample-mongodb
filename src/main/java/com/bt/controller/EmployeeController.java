package com.bt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bt.repository.EmployeeRepository;
import com.bt.vo.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/r")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping
	public List<Employee> findAll() {
		log.info("employeeRepository.findAll()={}", employeeRepository.findAll());
		return employeeRepository.findAll();
	}
	
	@GetMapping(value = "/empno/{empNo}")
	public List<Employee> findEmployeeByEmpNo(@PathVariable int empNo) {
		log.info("employeeRepository.findEmployeeByEmpNo()={}", employeeRepository.findEmployeeByEmpNo(empNo));
		return employeeRepository.findEmployeeByEmpNo(empNo);
	}
	
	@GetMapping(value = "/ename/{eName}")
	public List<Employee> findEmployeeByeName(@PathVariable String eName) {
		log.info("employeeRepository.findEmployeeByeName()={}", employeeRepository.findEmployeeByeName(eName));
		return employeeRepository.findEmployeeByeName(eName);
	}
	
	
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		return employee;
	}
	
	@PutMapping(value = "/{empNo}")
	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable int empNo) {
		employee.setEmpNo(empNo);
		employeeRepository.save(employee);
		return employee;
	}
	
	@DeleteMapping(value = "/{empNo}")
	public int deleteById(@PathVariable int empNo) {
		log.info("DELET empnbo={}", empNo);
		employeeRepository.deleteByEmpNo(empNo);
		return empNo;
	}
	
	
	
}
