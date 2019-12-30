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
	
	
	
	// select empNo, eName, sal from Employee where deptNo = 20 order by sal desc
	@Query(value = "{deptNo: ?0}", sort = "{sal: -1}", fields = "{empNo:1, eName:1, sal:1}" )
	List<Employee> findSortDesc(int deptNo);
	
	// select empNo, eName, sal from Employee where deptNo = 20 order by sal asc
	@Query(value = "{deptNo: ?0}", sort = "{sal: 1}", fields = "{empNo:1, eName:1, sal:1}" )
	List<Employee> findSortAsc(int deptNo);
	
	
	// select empNo, eName, sal from Employee where deptNo = 20 order by sal desc
	@Query(value = "{deptNo: ?0}", fields = "{eName:1, _id:0}" )
	List<String> findSortAndSelectOneField(int deptNo);
	
	// select empNo, eName, sal from Employee where deptNo = 20 order by sal desc
	@Query(value = "{deptNo: { $in:[ ?0, ?1 ] } }", fields = "{eName:1, eName:1, _id:0}" )
	List<Employee> findIn(int deptNo1, int deptNo2);
	
	// select empNo, deptNo from Employee where deptNo = ? and sal > ?
	@Query(value = "{$and: [{deptNo:?0}, {sal: {$gt: ?1}}] }", fields = "{deptNo:1, empNo:1, _id:0}" )
	List<Employee> findAndCondition(int deptNo, double minSal);
	
	
	// select empNo, deptNo from Employee where deptNo = ? and sal > ?
	@Query(value = "{comm: {$exists:true}}", fields = "{empNo:1, comm:1, _id:0}" )
	List<Employee> findExists();
	
	// select empNo, comm from Employee where comm type is double
	@Query(value = "{comm: {$type:1}}", fields = "{empNo:1, comm:1, _id:0}" )
	List<Employee> findType(String fieldName, int typeCode);
	
	// pattern : 대소문자 무시
	@Query(value = "{eName: {$regex:'S.*H', $options: 'i'}}", fields = "{empNo:1, comm:1, _id:0}" )
	List<Employee> regex1();
	
	// pattern : 대소문자 무시
	@Query(value = "{eName: {$regex:'s.*h', $options: 'i'}}", fields = "{empNo:1, comm:1, _id:0}" )
	List<Employee> regex2();
	
	
	// pattern : 대소문자 식별
	@Query(value = "{eName: {$regex:'S.*H', $options: 'm'}}", fields = "{empNo:1, comm:1, _id:0}" )
	List<Employee> regex3();
	
	// pattern : 대소문자 식별
	@Query(value = "{eName: {$regex:'s.*h', $options: 'm'}}", fields = "{empNo:1, comm:1, _id:0}" )
	List<Employee> regex4();

	Employee insert(Employee employee);

	
	@Query(value = "{empNo: {$ne: 7793}}" )
	List<Employee> notEquals(int empNo);

	@Query("{$or: [{deptNo:?0}, {sal: {$gte: ?1}}  ]}")
	List<Employee> findByDeptNoOrSal(int deptNo, int sal);

	@Query("{hiredate: {$regex: '.*?0'}}")
	List<Employee> findLikeByHireDate(int year);
	
	@Query("{hiredate: {$regex: '?0.*'}}")
	List<Employee> findLikeByHireDateHead(int year);

	
	// Sending command '{"find": "Employee", "filter": {"deptNo": 30}, "limit": 1, "$db": "test"}'
//	Employee findFirstByDeptNo(int deptNo); // findEmployeeOne
	List<Employee> findAll(); // findEmployeeOne

	// Sending command '{"find": "Employee", "filter": {"deptNo": 30}, "limit": 5, "$db": "test"}'
	List<Employee> findFirst5ByDeptNo(int deptNo);
	
	
	
	
}
