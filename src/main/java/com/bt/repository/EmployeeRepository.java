package com.bt.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bt.vo.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

	List<Employee> findEmployeeByEmpNo(int empNo);
	
	List<Employee> findEmployeeByeName(String eName);
	
	void deleteByEmpNo(int empNo);
	// select .. from Employee where empno=7782 or empno=7844
	@Query("{$or: [{empNo:?0}, {empNo:?1}]}")
	List<Employee> findEmployeeByEmpNoOrEmpNo(int empNo1, int empNo2);
	
	// select .. from Employee where sal > 1000 and sal <= 8000
	@Query("{sal: {$gt: ?0, $lte: ?1}}")
	List<Employee> findEmployeeBySalRange(int minSal, int maxSal);
	
	
	// select count(*) from Employee;
	@Query(value = "{}", count = true)
	long countEmployee();
	
	
	// select count(*) from Employee where sal >1200 and  sal <= 8000
	@Query(value = "{sal: {$gt: ?0, $lte: ?1}}", count = true)
	long countEmployeeBySalRange(int minSal, int maxSal);
}
