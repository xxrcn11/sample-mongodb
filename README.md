# 참고 사이트
 - https://www.devglan.com/spring-boot/spring-boot-mongodb-crud
 - https://lishman.io/spring-data-mongotemplate-queries
 - https://www.baeldung.com/queries-in-spring-data-mongodb
 - https://www.devglan.com/spring-boot/spring-boot-mongodb-configuration


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
> db.employees.insert({empno:7369 , ename : "SMITH", job : "CLERK", manager : "FORD", hiredate : "17-12-1980", sal : 800, deptno : 20 }) 
> db.employees.insert({empno:7499 , ename : "ALLEN", job : "SALESMAN", manager : "BLAKE", hiredate : "20-02-1981", sal :1600, comm : 300, deptno : 30 })
> db.employees.insert({empno:7521 , ename : "WARD", job : "SALESMAN", manager : "BLAKE", hiredate : "22-02-1981", sal : 1250, comm : 500, deptno : 30 })
> db.employees.insert({empno:7566 , ename : "JONES", job : "MANAGER", manager : "KING", hiredate : "02-04-1981", sal : 2975, deptno : 20 })
> db.employees.insert({empno:7654 , ename : "MARTIN", job : "SALESMAN", manager : "BLAKE", hiredate : "28-09-1981", sal : 1250, comm : 1400, deptno : 30 })
> db.employees.insert({empno:7698 , ename : "BLAKE", job : "MANAGER", manager : "KING", hiredate : "01-05-1981", sal : 2850, deptno : 30 })
> db.employees.insert({empno:7782 , ename : "CLARK", job : "MANAGER", manager : "KING", hiredate : "09-06-1981", sal : 2450, deptno : 10 })
> db.employees.insert({empno:7788 , ename : "SCOTT", job : "ANALYST", manager : "JONES", hiredate : "13-06-1987", sal : 3000, deptno : 20 })
> db.employees.insert({empno:7839 , ename : "KING", job : "CEO", manager : "", hiredate : "17-11-1981", sal : 5000, deptno : 10 })
> db.employees.insert({empno:7844 , ename : "TURNER", job : "SALESMAN", manager : "BLAKE", hiredate : "08-09-1981", sal : 1500, deptno : 30 }) 
> db.employees.insert({empno:7876 , ename : "ADAMS", job : "CLERK", manager : "SCOTT", hiredate : "13-06-1987", sal : 1100, deptno : 20 })
> db.employees.insert({empno:7900 , ename : "JAMES", job : "CLERK", manager : "BLAKE", hiredate : "03-12-1981", sal : 950, deptno : 30 })
> db.employees.insert({empno:7902 , ename : "FORD", job : "ANALYST", manager : "JONES", hiredate : "03-12-1981", sal : 3000, deptno : 20 })
> db.employees.insert({empno:7934 , ename : "CLERK", job : "CLERK", manager : "KING", hiredate : "23-01-1982", sal : 1300, deptno : 10 })

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
























    # sample-mongodb
