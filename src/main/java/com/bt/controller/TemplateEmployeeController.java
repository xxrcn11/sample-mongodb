package com.bt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bt.vo.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/t")
public class TemplateEmployeeController {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@GetMapping
	public List<Employee> findAll() {
		return mongoTemplate.findAll(Employee.class);
	}
	
	@GetMapping(value = "/empno/{empNo}")
	public List<Employee> findEmployeeByEmpNo(@PathVariable int empNo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("empNo").is(empNo));
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		mongoTemplate.save(employee);
		return employee;
	}
	
	@PutMapping(value = "/{empNo}")
	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable int empNo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("empNo").is(empNo));
		
		Update update = new Update();
		update.set("empNo", employee.getEmpNo());
		update.set("eName", employee.geteName());
		update.set("job", employee.getJob());
		update.set("manager", employee.getManager());
		update.set("hiredate", employee.getHiredate());
		update.set("sal", employee.getSal());
		update.set("deptNo", employee.getDeptNo());
		update.set("comm", employee.getComm());
		
		
		return mongoTemplate.findAndModify(query, update, Employee.class);
	}
	
	@DeleteMapping(value = "/{empNo}")
	public int deleteByEmpNo(@PathVariable int empNo) {
		log.info("DELET empnbo={}", empNo);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("empNo").is(empNo));
		mongoTemplate.findAndRemove(query, Employee.class);
		return empNo;
	} 
	
	// select .. from Employee where ename >= 'ALLEN' and ename < 'SCOTT'
	@GetMapping(value = "/ename/{ename1}-{ename2}")
	public List<Employee> findEmployeeByEname(@PathVariable String ename1, @PathVariable String ename2) {
		log.info("ename1={}, ename2={}", ename1, ename2);
		Query query = new Query();
		query.addCriteria(Criteria.where("eName").gte(ename1).lt(ename2).
				and("sal").gte(5000));
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// select .. from Employee where empno=7782 or empno=7844
	@GetMapping(value = "/empno/{empno1}/{empno2}")
	public List<Employee> findEmployeeByEmpNo(@PathVariable int empno1, @PathVariable int empno2) {
		log.info("empno1={}, empno2={}", empno1, empno2);
		Query query = new Query();
		query.addCriteria( new Criteria().orOperator(
				Criteria.where("empNo").is(empno1), Criteria.where("empNo").is(empno2) 
			) 
		);
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	
	// select .. from Employee where sal >1200 and  sal <= 8000
	@GetMapping(value = "/sal/{minSal}/{maxSal}")
	public List<Employee> findEmployeeBySalRange(@PathVariable int minSal, @PathVariable int maxSal) {
		log.info("minSal={}, maxSal={}", minSal, maxSal);
		Query query = new Query();
		query.addCriteria( Criteria.where("sal").gt(minSal).lte(maxSal) );
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// select count(*) from Employee
	@GetMapping(value = "/count")
	public long countEmployee() {
		return mongoTemplate.count(new Query(), "Employee");
//		return mongoTemplate.count(new Query(), Employee.class);
	}
	
	// select count(*) from Employee where sal >1200 and  sal <= 8000
	@GetMapping(value = "/count/sal/{minSal}/{maxSal}")
	public long countEmployeeBySalRange(@PathVariable int minSal, @PathVariable int maxSal) {
		log.info("minSal={}, maxSal={}", minSal, maxSal);
		Query query = new Query();
		query.addCriteria( Criteria.where("sal").gt(minSal).lte(maxSal) );
		
		return mongoTemplate.count(query, "Employee");
//			return mongoTemplate.count(new Query(), Employee.class);
	}
	
	// select distinct deptno from Employee
	@GetMapping(value = "/distinct")
	public List<String> distinct() {
		Query query = new Query();
		
		return mongoTemplate.findDistinct(query, "job", Employee.class, String.class);
	}
	
	// select distinct deptno from Employee where sal > 3000
	@GetMapping(value = "/distinct/{minSal}")
	public List<Integer> distinctWithCondition(@PathVariable int minSal) {
		Query query = new Query();
		query.addCriteria( Criteria.where("sal").gt(minSal) );
		return mongoTemplate.findDistinct(query, "deptNo", Employee.class, Integer.class);
	}
	
	
	
	// select empNo, eName, sal from Employee where deptNo = 20 order by sal desc
	@GetMapping(value = "/sort/{deptNo}")
	public List<Employee> findsort(@PathVariable int deptNo) {
		log.info("deptNo={}", deptNo);
		
		
		Query query = new Query();
		query.addCriteria(Criteria.where("deptNo").is(deptNo));
		query.with(Sort.by(Direction.DESC, "sal"));

		query.fields().include("empNo");
		query.fields().include("eName");		
		
		return mongoTemplate.find(query, Employee.class);
		
	} 
	
	// select empNo, eName, sal from Employee where deptNo in (10, 30)
	@GetMapping(value = "/in/{deptNo1}/{deptNo2}")
	public List<Employee> findIn(@PathVariable int deptNo1, @PathVariable int deptNo2) {
		log.info("deptNo1={}, deptNo2={}", deptNo1, deptNo2);
		
		
		Query query = new Query();
		query.addCriteria(Criteria.where("deptNo").in(deptNo1, deptNo2));

		query.fields().include("empNo");
		query.fields().include("eName");		
		query.fields().include("sal");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// select empNo, deptNo from Employee where deptNo = ? and sal > ?
	@GetMapping(value = "/and/{deptNo}/{minSal}")
	public List<Employee> findAndCondition(@PathVariable int deptNo, @PathVariable double minSal) {
		log.info("deptNo={}, minSal={}", deptNo, minSal);
		Query query = new Query();
		query.addCriteria(Criteria.where("deptNo").is(deptNo).and("sal").gt(minSal));
		query.fields().include("empNo");
		query.fields().include("deptNo");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	
	// select empNo, comm from Employee where comm is exists
	@GetMapping(value = "/find/exists")
	public List<Employee> findExists() {
		Query query = new Query();
		query.addCriteria(Criteria.where("comm").exists(true));
		query.fields().include("empNo");
		query.fields().include("comm");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// select empNo, comm from Employee where comm type is double
	@GetMapping(value = "/find/type/{fieldName}/{typeCode}")
	public List<Employee> findType(@PathVariable String fieldName, @PathVariable int typeCode) {
		log.info("fieldName={}, typeCode={}", fieldName, typeCode);
		Query query = new Query();
		query.addCriteria(Criteria.where(fieldName).type(typeCode));
		query.fields().include("empNo");
		query.fields().include("comm");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// pattern : 대소문자 무시
	@GetMapping(value = "/find/regex1")
	public List<Employee> findRegex1() {
		Query query = new Query();
		query.addCriteria(Criteria.where("eName").regex("S.*H", "i" ) );
		query.fields().include("empNo");
		query.fields().include("comm");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// pattern : 대소문자 무시
	@GetMapping(value = "/find/regex2")
	public List<Employee> findRegex2() {
		Query query = new Query();
		query.addCriteria(Criteria.where("eName").regex("s.*h", "i" ) );
		query.fields().include("empNo");
		query.fields().include("comm");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// pattern : 대소문자 식별
	@GetMapping(value = "/find/regex3")
	public List<Employee> findRegex3() {
		Query query = new Query();
		query.addCriteria(Criteria.where("eName").regex("S.*H", "m" ) );
		query.fields().include("empNo");
		query.fields().include("comm");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// pattern : 대소문자 식별
	@GetMapping(value = "/find/regex4")
	public List<Employee> findRegex4() {
		Query query = new Query();
		query.addCriteria(Criteria.where("eName").regex("s.*h", "m" ) );
		query.fields().include("empNo");
		query.fields().include("comm");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
	
	// insert into
	@PostMapping(value = "/insert")
	public Employee insertEmployee(@RequestBody Employee employee) {
		return mongoTemplate.insert(employee, "Employee");
	}
	
}
