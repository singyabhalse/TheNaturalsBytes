package com.userexprior.controller;

import java.util.List;
import java.util.Map;

import com.userexprior.service.EmployeeAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userexprior.service.EmployeeService;


@RestController
public class EmployeeController {

	/**
	 * Service for accessing employee business logic and data operations.
	 * Injected automatically by Spring's dependency injection.
	 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Adapter for transforming employee data into UI-friendly format.
	 * Converts raw employee data into structured response objects.
	 */
	@Autowired
	private EmployeeAdaptor employeeAdaptor;

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
