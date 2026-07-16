package com.userexprior.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.userexprior.adaptor.EmployeeAdaptor;
import com.userexprior.service.EmployeeService;


@RestController
public class EmployeeController {

	/**
	 * Service for accessing employee business logic and data operations.
	 * Injected automatically by Spring's dependency injection.
	 */
	@Autowired
	EmployeeService employeeService;

	/**
	 * Adapter for transforming employee data into UI-friendly format.
	 * Converts raw employee data into structured response objects.
	 */
	EmployeeAdaptor employeeAdaptor = new EmployeeAdaptor();

	/**
	 * Retrieves all employee data and their activities.
	 * Fetches employees from service and transforms the data using the adaptor.
	 *
	 * @return Map containing:
	 *         - "last_7_days_statistics": List of activities from past 7 days with occurrence counts
	 *         - "todays_activities": List of today's activities grouped by employee
	 */
	@GetMapping("/getData")
	public Map<String, List<?>> getAllEmployee() {
		return employeeAdaptor.employeeListToEmployeeUIDto(employeeService.findAllEmployee());
	}
}
