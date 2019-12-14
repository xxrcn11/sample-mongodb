package com.bt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
public class RepositoryEmployeeController {

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
	
	// select .. from Employee where empno=7782 or empno=7844
	@GetMapping(value = "/empno/{empno1}/{empno2}")
	public List<Employee> findEmployeeByEmpNo(@PathVariable int empno1, @PathVariable int empno2) {
		log.info("empno1={}, empno2={}", empno1, empno2);
		
		return employeeRepository.findEmployeeByEmpNoOrEmpNo(empno1, empno2);
	}
	
	// select .. from Employee where empno=7782 or empno=7844
	@GetMapping(value = "/sal/{minSal}/{maxSal}")
	public List<Employee> findEmployeeBySalRange(@PathVariable int minSal, @PathVariable int maxSal) {
		log.info("minSal={}, maxSal={}", minSal, maxSal);
		
		return employeeRepository.findEmployeeBySalRange(minSal, maxSal);
	}
	
	// select count(*) from Employee
	@GetMapping(value = "/count")
	public long countEmployee() {
		return employeeRepository.countEmployee();
	}
	
	// select count(*) from Employee
	@GetMapping(value = "/count/sal/{minSal}/{maxSal}")
	public long countEmployeeBySalRange(@PathVariable int minSal, @PathVariable int maxSal) {
		log.info("minSal={}, maxSal={}", minSal, maxSal);
		return employeeRepository.countEmployeeBySalRange(minSal, maxSal);
	}
	
	@GetMapping(value = "/sort/{deptNo}")
	public List<Employee> findsort(@PathVariable int deptNo) {
		log.info("deptNo={}", deptNo);
		
		return employeeRepository.findSortAndSelectFields(deptNo);
		
	} 
	
	@GetMapping(value = "/sort1/{deptNo}")
	public List<String> findSortAndSelectOneField(@PathVariable int deptNo) {
		log.info("deptNo={}", deptNo);
		List<String> aaa = employeeRepository.findSortAndSelectOneField(deptNo);
		log.info("aa={}", aaa);
		return employeeRepository.findSortAndSelectOneField(deptNo);
		
	} 
	
}
