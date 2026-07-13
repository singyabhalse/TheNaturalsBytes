
package com.userexprior.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long time;
	private Long duration;
	@ManyToOne(cascade=CascadeType.ALL)
	private Employee employee;

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", time=" + time + ", duration=" + duration + ", employee="
				+ employee + "]";
	}
// my test
}
