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

	@Autowired
	EmployeeService employeeService;

	 
	
	EmployeeAdaptor employeeAdaptor = new EmployeeAdaptor();

	@GetMapping("/getData")
	public Map<String, List<?>> getAllEmployee() {
		return employeeAdaptor.employeeListToEmployeeUIDto(employeeService.findAllEmployee());
	}
}
