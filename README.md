# 참고 사이트
 - https://www.devglan.com/spring-boot/spring-boot-mongodb-crud
 - https://lishman.io/spring-data-mongotemplate-queries
 - https://www.baeldung.com/queries-in-spring-data-mongodb
 - https://www.devglan.com/spring-boot/spring-boot-mongodb-configuration
 - https://www.devglan.com/spring-boot/spring-data-mongodb-queries

# online README.md editor
 - https://stackedit.io/app#

# index 확인
- db.employees.getIndices()
> [
        {
                "v" : 2,
                "key" : {
                        "_id" : 1
                },
                "name" : "_id_",
                "ns" : "test.employees"
        },
        {
                "v" : 2,
                "key" : {
                        "ename" : 1
                },
                "name" : "ename_1",
                "ns" : "test.employees"
        },
        {
                "v" : 2,
                "key" : {
                        "sal" : 1
                },
                "name" : "sal_1",
                "ns" : "test.employees"
        }
]



# Sample Data
> db.Employee.drop()
> db.Employee.insert({empNo:7369 , eName : "SMITH", job : "CLERK", manager : "FORD", hiredate : "17-12-1980", sal : 800, deptNo : 20 }) 
> db.Employee.insert({empNo:7499 , eName : "ALLEN", job : "SALESMAN", manager : "BLAKE", hiredate : "20-02-1981", sal :1600, comm : 300, deptNo : 30 })
> db.Employee.insert({empNo:7521 , eName : "WARD", job : "SALESMAN", manager : "BLAKE", hiredate : "22-02-1981", sal : 1250, comm : 500, deptNo : 30 })
> db.Employee.insert({empNo:7566 , eName : "JONES", job : "MANAGER", manager : "KING", hiredate : "02-04-1981", sal : 2975, deptNo : 20 })
> db.Employee.insert({empNo:7654 , eName : "MARTIN", job : "SALESMAN", manager : "BLAKE", hiredate : "28-09-1981", sal : 1250, comm : 1400, deptNo : 30 })
> db.Employee.insert({empNo:7698 , eName : "BLAKE", job : "MANAGER", manager : "KING", hiredate : "01-05-1981", sal : 2850, deptNo : 30 })
> db.Employee.insert({empNo:7782 , eName : "CLARK", job : "MANAGER", manager : "KING", hiredate : "09-06-1981", sal : 2450, deptNo : 10 })
> db.Employee.insert({empNo:7788 , eName : "SCOTT", job : "ANALYST", manager : "JONES", hiredate : "13-06-1987", sal : 3000, deptNo : 20 })
> db.Employee.insert({empNo:7839 , eName : "KING", job : "CEO", manager : "", hiredate : "17-11-1981", sal : 5000, deptNo : 10 })
> db.Employee.insert({empNo:7844 , eName : "TURNER", job : "SALESMAN", manager : "BLAKE", hiredate : "08-09-1981", sal : 1500, deptNo : 30 }) 
> db.Employee.insert({empNo:7876 , eName : "ADAMS", job : "CLERK", manager : "SCOTT", hiredate : "13-06-1987", sal : 1100, deptNo : 20 })
> db.Employee.insert({empNo:7900 , eName : "JAMES", job : "CLERK", manager : "BLAKE", hiredate : "03-12-1981", sal : 950, deptNo : 30 })
> db.Employee.insert({empNo:7902 , eName : "FORD", job : "ANALYST", manager : "JONES", hiredate : "03-12-1981", sal : 3000, deptNo : 20 })
> db.Employee.insert({empNo:7934 , eName : "CLERK", job : "CLERK", manager : "KING", hiredate : "23-01-1982", sal : 1300, deptNo : 10 })
> db.Employee.find()

# MongoDB Version
# command: mongod --version
**db version v4.2.1**
- git version: edf6d45851c0b9ee15548f0f847df141764a317e
- OpenSSL version: OpenSSL 1.1.1  11 Sep 2018
- allocator: tcmalloc
- modules: none
- build environment:
-     distmod: ubuntu1804
-     distarch: x86_64
-     target_arch: x86_64
    
    

# SpringBoot MongoDB CRUD Example

**Spring Data MongoRepository**를 이용한 방식과 **MongoTemplate**를 이용한 2가지의 방식에 대해 설명한다.
동일한 CRUD에 대해 각각의 방식으로 구현한다.


#  OR Operation
- select .. from Employee where empno=7782 or empno=7844
## mongoTemplate style
> query.addCriteria( new Criteria().orOperator(
				Criteria.where("empNo").is(empno1), Criteria.where("empNo").is(empno2) ) 
		);
## @Query style
> @Query("{$or: [{empNo:?0}, {empNo:?1}]}")


# select .. from Employee where deptNo=10 or sal >= 3000
## mongoTemplate style
> query.addCriteria( new Criteria().orOperator(Criteria.where("deptNo").is(deptNo), Criteria.where("sal").gte(sal) ));  
> Sending command '{"find": "Employee", "filter": {"$or": [{"deptNo": 20}, {"sal": {"$gte": 3000}}]}, "$db": "test"}'

## @Query style
> @Query("{$or: [{deptNo:?0}, {sal: {$gte: ?1}}  ]}")






# $gt and $lte
- select .. from Employee where sal > 1000 and sal <= 8000
## mongoTemplate style
> query.addCriteria( Criteria.where("sal").gt(minSal).lte(maxSal) );

## @Query style
> @Query("{sal: {$gt: ?0, $lte: ?1}}")


# count
- select count(*) from Employee
## mongoTemplate style
> mongoTemplate.count(new Query(), "Employee");
  mongoTemplate.count(new Query(), Employee.class);

## @Query style
> @Query(value = "{}", count = true)

# count with where
- select count(*) from Employee where sal >1200 and  sal <= 8000
## mongoTemplate style
  query.addCriteria( Criteria.where("sal").gt(minSal).lte(maxSal) );
  mongoTemplate.count(query, "Employee");

## @Query style
> @Query(value = "{sal: {$gt: ?0, $lte: ?1}}", count = true)
  long countEmployeeBySalRange(int minSal, int maxSal);



# distinct with where
- select distinct deptno from Employee where sal > 3000

## mongoTemplate style
> 	Query query = new Query();
	query.addCriteria( Criteria.where("sal").gt(minSal) );
	return mongoTemplate.findDistinct(query, "deptNo", Employee.class, Integer.class);

## @Query style
> 못 찾음


# select field, sort desc / asc
- select empNo, eName, sal from Employee where deptNo = 20 order by sal desc

## mongoTemplate style
> Sending command '{"find": "Employee", "filter": {"deptNo": 30}, "sort": {"sal": -1}, "projection": {"eName": 1, "empNo": 1, "sal": 1}, "$db": "test"}'

>	query.addCriteria(Criteria.where("deptNo").is(deptNo));
	query.with(Sort.by(Direction.DESC, "sal"));
	query.fields().include("empNo");
	query.fields().include("eName");		
	return mongoTemplate.find(query, Employee.class);

## @Query style
> @Query(value = "{deptNo: ?0}", sort = "{sal: -1}", fields = "{empNo:1, eName:1, sal:1}" )
> '{"find": "Employee", "filter": {"deptNo": 30}, "sort": {"sal": -1}, "projection": {"empNo": 1, "eName": 1, "sal": 1}, "$db": "test"}'


# select field, sort asc
- select empNo, eName, sal from Employee where deptNo = 20 order by sal asc

## mongoTemplate style
>	query.addCriteria(Criteria.where("deptNo").is(deptNo));
	query.with(Sort.by(Direction.DESC, "sal"));
	query.fields().include("empNo");
	query.fields().include("eName");		
	return mongoTemplate.find(query, Employee.class);

> '{"find": "Employee", "filter": {"deptNo": 30}, "sort": {"sal": 1}, "projection": {"empNo": 1, "eName": 1, "sal": 1}, "$db": "test"}'

## @Query style
> @Query(value = "{deptNo: ?0}", sort = "{sal: 1}", fields = "{empNo:1, eName:1, sal:1}" )
> '{"find": "Employee", "filter": {"deptNo": 30}, "sort": {"sal": 1}, "projection": {"empNo": 1, "eName": 1, "sal": 1}, "$db": "test"}'


# select field where in
- select empNo, eName, sal from Employee where deptNo in (10, 30)

## mongoTemplate style
> 	public List<Employee> findIn(@PathVariable int deptNo1, @PathVariable int deptNo2) {
		log.info("deptNo1={}, deptNo2={}", deptNo1, deptNo2);
		
		
		Query query = new Query();
		query.addCriteria(Criteria.where("deptNo").in(deptNo1, deptNo2));

		query.fields().include("empNo");
		query.fields().include("eName");		
		query.fields().include("sal");
		
		return mongoTemplate.find(query, Employee.class);
	}

> [
    {
        "id": "5dec9ea325f36898b9626fdd",
        "empNo": 7499,
        "eName": "ALLEN",
        "job": null,
        "manager": null,
        "hiredate": null,
        "sal": 1600,
        "deptNo": 0,
        "comm": 0
    },
    {
        "id": "5dec9ea325f36898b9626fde",
        "empNo": 7521,
        "eName": "WARD",
        "job": null,
        "manager": null,
        "hiredate": null,
        "sal": 1250,
        "deptNo": 0,
        "comm": 0
    }, ... ]
    
## @Query style
> @Query(value = "{deptNo: { $in:[ ?0, ?1 ] } }", fields = "{eName:1, eName:1, sal:1, _id:0}" )
	List<Employee> findIn(int deptNo1, int deptNo2);

> [
    {
        "id": null,
        "empNo": 0,
        "eName": "ALLEN",
        "job": null,
        "manager": null,
        "hiredate": null,
        "sal": 1600,
        "deptNo": 0,
        "comm": 0
    },
    {
        "id": null,
        "empNo": 0,
        "eName": "WARD",
        "job": null,
        "manager": null,
        "hiredate": null,
        "sal": 1250,
        "deptNo": 0,
        "comm": 0
    }, .... ]


# select empNo, deptNo from Employee where deptNo = ? and sal > ?

## mongoTemplate style
> 	@GetMapping(value = "/and/{deptNo}/{minSal}")
	public List<Employee> findAndCondition(@PathVariable int deptNo, @PathVariable double minSal) {
		log.info("deptNo={}, minSal={}", deptNo, minSal);

		Query query = new Query();
		query.addCriteria(Criteria.where("deptNo").is(deptNo).and("sal").gt(minSal));
		query.fields().include("empNo");
		query.fields().include("deptNo");
		query.fields().exclude("_id");
		
		return mongoTemplate.find(query, Employee.class);
	}
> [
    {
        "id": null,
        "empNo": 7839,
        "eName": null,
        "job": null,
        "manager": null,
        "hiredate": null,
        "sal": 0,
        "deptNo": 10,
        "comm": 0
    }
]	

## @Query style
> @Query(value = "{$and: [{deptNo:?0}, {sal: {$gt: ?1}}] }", fields = "{deptNo:1, empNo:1, _id:0}" )
  List<Employee> findAndCondition(int deptNo, double minSal);



# select empNo, comm from Employee where comm is exists

## mongoTemplate style
> query.addCriteria(Criteria.where("comm").exists(true));

## @Query style
> @Query(value = "{comm: {$exists:true}}", fields = "{empNo:1, comm:1, _id:0}" )



# field type으로 조회. 특정 field가 없는 경우에 filtering됨

## mongoTemplate style
> 	query.addCriteria(Criteria.where(fieldName).type(typeCode));

## @Query style
> @Query(value = "{comm: {$type:1}}", fields = "{empNo:1, comm:1, _id:0}" )


#  pattern : 대소문자 무시
## mongoTemplate style
> query.addCriteria(Criteria.where("eName").regex("S.*H", "i" ) );
## Query style
> @Query(value = "{eName: {$regex:'S.*H', $options: 'i'}}", fields = "{empNo:1, comm:1, _id:0}" )


#  pattern : 대소문자 무시
## mongoTemplate style
> query.addCriteria(Criteria.where("eName").regex("s.*h", "i" ) );
## Query style
> @Query(value = "{eName: {$regex:'s.*h', $options: 'i'}}", fields = "{empNo:1, comm:1, _id:0}" )



#  pattern : 대소문자 식별
## mongoTemplate style
> query.addCriteria(Criteria.where("eName").regex("S.*H", "m" ) );
## Query style
> @Query(value = "{eName: {$regex:'S.*H', $options: 'm'}}", fields = "{empNo:1, comm:1, _id:0}" )

#  pattern : 대소문자 식별
## mongoTemplate style
> query.addCriteria(Criteria.where("eName").regex("s.*h", "m" ) );
## Query style
> @Query(value = "{eName: {$regex:'s.*h', $options: 'm'}}", fields = "{empNo:1, comm:1, _id:0}" )

#  insert into Employee
## mongoTemplate style
> mongoTemplate.insert(employee, "Employee");
## Query style
> Employee insert(Employee employee);


#  select * from Employee empNo != 7783
## mongoTemplate style
> query.addCriteria(Criteria.where("empNo").ne(empNo) );
  return mongoTemplate.find(query, Employee.class);
## Query style
> @Query(value = "{empNo: {$ne: 7793}}" )



#  select .. from Employee where hiredate like '%1999%'
## mongoTemplate style
> query.addCriteria( Criteria.where("hiredate").regex(".*1999"));
> Sending command '{"find": "Employee", "filter": {"hiredate": {"$regex": ".*1999"}}, "$db": "test"}'
## Query style
> @Query("{hiredate: {$regex: '.*?0'}}")



#  select .. from Employee where hiredate like '1999%'
## mongoTemplate style
> query.addCriteria( Criteria.where("hiredate").regex(year + ".*"));
> Sending command '{"find": "Employee", "filter": {"hiredate": {"$regex": "19.*"}}, "$db": "test"}'
## Query style
> @Query("{hiredate: {$regex: '?0.*'}}")



# rownum = 1
- select * from Employee where rownum = 1

## mongoTemplate style
> mongoTemplate.findOne(new Query(), Employee.class);
> Sending command '{"find": "Employee", "limit": 3, "$db": "test"}'

## @Query style1 : 조건 없이 1개만 조회하는 방법은 없음
> return employeeRepository.findAll().stream().findFirst();
> List<Employee> findAll();

## @Query style2 : 명명 규칙을 이용해서 하나의 조건을 주고 First, Top을 이용해서 필터링하는 방법
> Employee findFirstByDeptNo(int deptNo); 






# limit < 3
- select * from Employee where limit < 3

## mongoTemplate style
> mongoTemplate.find(new Query().limit(limit), Employee.class);
> Sending command '{"find": "Employee", "limit": 3, "$db": "test"}'

## @Query style : 동적으로 limit 개수를 조절할 방법을 못 찾음
> List<Employee> findFirst5ByDeptNo(int deptNo);
> Sending command '{"find": "Employee", "filter": {"deptNo": 30}, "limit": 5, "$db": "test"}'














	