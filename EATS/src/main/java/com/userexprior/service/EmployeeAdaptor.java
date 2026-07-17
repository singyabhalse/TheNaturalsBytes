package com.userexprior.service;

import com.userexprior.dto.ActivitiesDTO;
import com.userexprior.dto.EmployeeDto;
import com.userexprior.dto.TodaysActivitiesDto;
import com.userexprior.model.Activity;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Adapter for transforming raw employee and activity data into UI-friendly DTOs.
 * Separates today's activities from historical activities (last 7 days),
 * groups activities by name, and sorts data for presentation.
 */
@Component
public class EmployeeAdaptor {
	
	/**
	 * Transforms raw database query results into structured UI DTOs.
	 * Separates activities into two categories:
	 * 1. Today's activities - grouped by employee with activities for today
	 * 2. Last 7 days statistics - activity occurrence counts grouped by activity name
	 *
	 * @param employeeList Raw Object arrays from database query containing
	 *                     {id, duration, name, time, employeeId}
	 * @return Map with two keys:
	 *         - "last_7_days_statistics": List of activity occurrence counts
	 *         - "todays_activities": List of employees with today's activities
	 */
	public Map<String, List<?>> employeeListToEmployeeUIDto(List<Object[]> employeeList) {

		// Date formatter for comparing activity dates
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		// Calendar for current activity (action[3])
		Calendar cal1 = Calendar.getInstance();
		
		// Calendar for today's date
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(cal2.getTime());

		// Response map to hold both statistics and today's activities
		Map<String, List<?>> map = new LinkedHashMap<String, List<?>>();
		
		// Track processed employee IDs to avoid duplicates
		List<Long> idList = new ArrayList<Long>();

		// Store activities from past 7 days for statistics
		List<Activity> activityList = new ArrayList<Activity>();
		
		// Store today's activities (temporary list for each employee)
		List<TodaysActivitiesDto> todaysActivitiesDtoList = new ArrayList<TodaysActivitiesDto>();
		
		// Store all employees with today's activities
		List<EmployeeDto> currentActivityEmployeeList = new ArrayList<EmployeeDto>();

		// Process each activity record from database
		for (Object[] action : employeeList) {
			Activity ac = new Activity();
			TodaysActivitiesDto TodaysActivitiesDto = new TodaysActivitiesDto();
			
			// Parse activity timestamp (action[3])
			cal1.setTimeInMillis(Long.valueOf(action[3].toString()));
			
			// Check if activity is from today
			if (formatter.format(cal1.getTime()).equals(formatter.format(cal2.getTime()))) {
				EmployeeDto emp = new EmployeeDto();
				
				// Check if this is a new employee
				if (!idList.contains(Long.valueOf(action[4].toString()))) {
					// Initialize new activity list for this employee
					todaysActivitiesDtoList = new ArrayList<TodaysActivitiesDto>();
					TodaysActivitiesDto.setName(action[2].toString());
					TodaysActivitiesDto.setStart_time(Long.valueOf(action[3].toString()));
					todaysActivitiesDtoList.add(TodaysActivitiesDto);
					idList.add(Long.valueOf(action[4].toString()));
					currentActivityEmployeeList.add(emp);
					emp.setEmployeeId(Long.valueOf(action[4].toString()));
					emp.setActivities(todaysActivitiesDtoList);

				} else {
					// Add activity to existing employee's today's activities
					TodaysActivitiesDto.setName(action[2].toString());
					TodaysActivitiesDto.setStart_time(Long.valueOf(action[3].toString()));
					todaysActivitiesDtoList.add(TodaysActivitiesDto);
					emp.setEmployeeId(Long.valueOf(action[4].toString()));
					emp.setActivities(todaysActivitiesDtoList);
				}

			} else {
				// Activity from past 7 days - add to statistics
				ac.setId(Long.valueOf(action[0].toString()));
				ac.setDuration(Long.valueOf(action[1].toString()));
				ac.setName(action[2].toString());
				ac.setTime(Long.valueOf(action[3].toString()));
				activityList.add(ac);
			}
		}

		// Sort activities by time
		List<Activity> sortedList = activityList.stream()
				.sorted(Comparator.comparingLong(Activity::getTime))
				.collect(Collectors.toList());
		
		// Group activities by name and count occurrences, then sort by count (descending)
		List<ActivitiesDTO> list = new ArrayList<ActivitiesDTO>();
		sortedList.stream()
				.collect(Collectors.groupingBy(Activity::getName, Collectors.counting()))
				.entrySet()
				.parallelStream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(pair -> {
					ActivitiesDTO activity = new ActivitiesDTO();
					activity.setActivity_name(pair.getKey());
					activity.setOccurrences(pair.getValue());
					list.add(activity);
				});

		// Sort employees by ID in descending order
		List<EmployeeDto> employeeSortDto = currentActivityEmployeeList.stream()
				.sorted(Comparator.comparingLong(EmployeeDto::getEmployeeId).reversed())
				.collect(Collectors.toList());

		// Sort each employee's today's activities by start time
		employeeSortDto.stream().forEach(data -> {
			data.setActivities(data.getActivities().stream()
					.sorted(Comparator.comparingLong(TodaysActivitiesDto::getStart_time))
					.collect(Collectors.toList()));
		});

		// Build response map
		map.put("last_7_days_statistics", list);
		map.put("todays_activities", employeeSortDto);

		return map;
	}
}
