package com.bt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping(value = "/sort/desc/{deptNo}")
	public List<Employee> findSortDesc(@PathVariable int deptNo) {
		log.info("deptNo={}", deptNo);
		return employeeRepository.findSortDesc(deptNo);
		
	}
	
	@GetMapping(value = "/sort/asc/{deptNo}")
	public List<Employee> findSortAsc(@PathVariable int deptNo) {
		log.info("deptNo={}", deptNo);
		return employeeRepository.findSortAsc(deptNo);
		
	} 
	
	@GetMapping(value = "/sort1/{deptNo}")
	public List<String> findSortAndSelectOneField(@PathVariable int deptNo) {
		log.info("deptNo={}", deptNo);
		List<String> aaa = employeeRepository.findSortAndSelectOneField(deptNo);
		log.info("aa={}", aaa);
		return employeeRepository.findSortAndSelectOneField(deptNo);
		
	} 
	
	// select empNo, eName, sal from Employee where deptNo in (10, 30)
	@GetMapping(value = "/in/{deptNo1}/{deptNo2}")
	public List<Employee> findIn(@PathVariable int deptNo1, @PathVariable int deptNo2) {
		log.info("deptNo1={}, deptNo2={}", deptNo1, deptNo2);
		
		return employeeRepository.findIn(deptNo1, deptNo2);
	}
	
	@GetMapping(value = "/and/{deptNo}/{minSal}")
	public List<Employee> findAndCondition(@PathVariable int deptNo, @PathVariable double minSal) {
		log.info("deptNo={}, minSal={}", deptNo, minSal);
		return employeeRepository.findAndCondition(deptNo, minSal);
	}
	
	@GetMapping(value = "/find/exists")
	public List<Employee> findExists() {
		return employeeRepository.findExists();
	}
	
	
	// select empNo, comm from Employee where comm type is double
	@GetMapping(value = "/find/type/{fieldName}/{typeCode}")
	public List<Employee> findType(@PathVariable String fieldName, @PathVariable int typeCode) {
		log.info("fieldName={}, typeCode={}", fieldName, typeCode);
		
		return employeeRepository.findType(fieldName, typeCode);
	}	
	
	
	@GetMapping(value = "/find/regex1")
	public List<Employee> findRegex1() {
		return employeeRepository.regex1();
	}

	@GetMapping(value = "/find/regex2")
	public List<Employee> findRegex2() {
		return employeeRepository.regex2();
	}

	@GetMapping(value = "/find/regex3")
	public List<Employee> findRegex3() {
		return employeeRepository.regex3();
	}
	
	@GetMapping(value = "/find/regex4")
	public List<Employee> findRegex4() {
		return employeeRepository.regex4();
	}	
	
	
	// insert into
	@PostMapping(value = "/insert")
	public Employee insertEmployee(@RequestBody Employee employee) {
		return employeeRepository.insert(employee);
	}
	
	// select * from where empNo != 88932
	@GetMapping(value = "/not/{empNo}")
	public List<Employee> notEquals(@PathVariable int empNo) {
		return employeeRepository.notEquals(empNo);
	}	
	
	// select .. from Employee where deptNo=10 or sal >= 3000
	@GetMapping(value = "/or/{deptNo}/{sal}")
	public List<Employee> findByEmpNoAndDeptNo(@PathVariable int deptNo, @PathVariable int sal) {
		
		return employeeRepository.findByDeptNoOrSal(deptNo, sal);
	}	

	
	// select .. from Employee where hiredate like '%1999%'
	@GetMapping(value = "/like/{year}")
	public List<Employee> findLikeByHireDate(@PathVariable int year) {
		return employeeRepository.findLikeByHireDate(year);
	}
	
	// select .. from Employee where hiredate like '19%'
	@GetMapping(value = "/like/head/{year}")
	public List<Employee> findLikeByHireDateHead(@PathVariable int year) {
		return employeeRepository.findLikeByHireDateHead(year);
	}

	// select .. from Employee where rownum = 1;
	@GetMapping(value = "/findone")
	public Optional<Employee> findOne() {
		return employeeRepository.findAll().stream().findFirst();
	}
	
	// select .. from Employee where rownum > 2;
	@GetMapping(value = "/limit/{deptNo}")
	public List<Employee> findLimit(@PathVariable int deptNo) {
		log.info("deptNo={}", deptNo);
		return employeeRepository.findFirst5ByDeptNo(deptNo);
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
