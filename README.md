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
	List<Employee> findEmployeeByEmpNoOrEmpNo(int empNo1, int empNo2);


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
> Query query = new Query();
  query.addCriteria( Criteria.where("sal").gt(minSal).lte(maxSal) );
  mongoTemplate.count(query, "Employee");
  mongoTemplate.count(new Query(), Employee.class);

## @Query style
> @Query(value = "{sal: {$gt: ?0, $lte: ?1}}", count = true)
  long countEmployeeBySalRange(int minSal, int maxSal);














