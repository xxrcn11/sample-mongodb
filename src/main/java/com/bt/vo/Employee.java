package com.bt.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Employee")
public class Employee {

	@Id
	private String id;
	
	@Indexed(name = "ename_1")
	private int empNo;
	private String eName;
	private String job;
	private String manager;
	private String hiredate;
	@Indexed(name = "sal_1")
	private int	sal;
	private int deptNo;
	private int comm;
	
	public Employee() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
		this.sal = sal;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public int getComm() {
		return comm;
	}

	public void setComm(int comm) {
		this.comm = comm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [id=").append(id).append(", empNo=").append(empNo).append(", eName=").append(eName)
				.append(", job=").append(job).append(", manager=").append(manager).append(", hiredate=")
				.append(hiredate).append(", sal=").append(sal).append(", deptNo=").append(deptNo).append(", comm=")
				.append(comm).append("]");
		return builder.toString();
	}

	
	
}
