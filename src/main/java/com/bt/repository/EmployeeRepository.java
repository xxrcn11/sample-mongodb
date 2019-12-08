package com.bt.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bt.vo.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

	List<Employee> findEmployeeByEmpNo(int empNo);
	
	List<Employee> findEmployeeByeName(String eName);
	
	void deleteByEmpNo(int empNo);
}
