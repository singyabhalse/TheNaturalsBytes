package com.userexprior.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee {

	@Id
	private Long employeeId;
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Activity> activities = null;

	public Long getEmployeeId() {
		return employeeId;
	}

	@Version
    private int version;
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	

}