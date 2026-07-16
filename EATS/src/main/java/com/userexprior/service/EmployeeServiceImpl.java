package com.userexprior.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userexprior.exception.ExceptionHandling;
import com.userexprior.model.Employee;
import com.userexprior.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	
	
	int count = 0;

	@Override
	@Transactional
	public void save(List<String> result) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Employee> employeeList = new ArrayList<Employee>();

		result.parallelStream().forEachOrdered(file -> {
			try {
				Employee employee = objectMapper.readValue(new File(file), Employee.class);
				if (employee.getEmployeeId() != 0) {
					employee.getActivities().parallelStream().forEach(activity -> {
						activity.setEmployee(employee);
					});
					employeeList.add(employee);
					count++;
					if ((count + 1) % 20 == 0 || (count + 1) == result.size()) {
						employeeRepository.saveAll(employeeList);
						employeeList.clear();
					}
				} else {
					throw new ExceptionHandling("Invalid JSON Object in this file "+file);
				}
			} catch (IOException | ExceptionHandling e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<Object[]> findAllEmployee() {
		return employeeRepository.findAllEmployee();
	}

}
