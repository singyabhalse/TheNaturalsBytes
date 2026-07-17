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

/**
 * Service implementation for employee-related business operations.
 * Handles employee data persistence and retrieval from JSON files and database.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	/**
	 * Repository for database operations on Employee entities.
	 * Injected automatically by Spring's dependency injection.
	 */
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * Counter to track batch processing of employees.
	 * Used to save in batches of 20 for performance optimization.
	 */
	int count = 0;

	/**
	 * Saves employee data from JSON files to the database.
	 * Processes files in parallel, deserializes JSON to Employee objects,
	 * and saves in batches of 20 for optimal database performance.
	 *
	 * @param result List of file paths containing employee JSON data
	 */
	@Override
	@Transactional
	public void save(List<String> result) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Employee> employeeList = new ArrayList<Employee>();

		// Process files in parallel, maintaining order
		result.parallelStream().forEachOrdered(file -> {
			try {
				// Deserialize JSON file to Employee object
				Employee employee = objectMapper.readValue(new File(file), Employee.class);
				
				// Validate employee has a valid ID
				if (employee.getEmployeeId() != 0) {
					// Set bidirectional relationship between employee and activities
					employee.getActivities().parallelStream().forEach(activity -> {
						activity.setEmployee(employee);
					});
					employeeList.add(employee);
					count++;
					
					// Save in batches of 20 or when processing final batch
					if ((count + 1) % 20 == 0 || (count + 1) == result.size()) {
						employeeRepository.saveAll(employeeList);
						employeeList.clear();
					}
				} else {
					throw new ExceptionHandling("Invalid JSON Object in this file " + file);
				}
			} catch (IOException | ExceptionHandling e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Retrieves all employees with their activities from the last 7 days.
	 * Uses a custom SQL query to fetch data across employee and activity tables.
	 *
	 * @return List of Object arrays containing employee and activity data
	 */
	@Override
	public List<Object[]> findAllEmployee() {
		return employeeRepository.findAllEmployee();
	}

}
