package com.userexprior.adaptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.userexprior.dto.ActivitiesDTO;
import com.userexprior.dto.EmployeeDto;
import com.userexprior.dto.TodaysActivitiesDto;
import com.userexprior.model.Activity;

@Component
public class EmployeeAdaptor {
	public Map<String, List<?>> employeeListToEmployeeUIDto(List<Object[]> employeeList) {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//test
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(cal2.getTime());

		Map<String, List<?>> map = new LinkedHashMap<String, List<?>>();
		List<Long> idList = new ArrayList<Long>();

		List<Activity> activityList = new ArrayList<Activity>();
		List<TodaysActivitiesDto> todaysActivitiesDtoList = new ArrayList<TodaysActivitiesDto>();
		List<EmployeeDto> currentActivityEmployeeList = new ArrayList<EmployeeDto>();


		for (Object[] action : employeeList) {
			Activity ac = new Activity();
			TodaysActivitiesDto TodaysActivitiesDto = new TodaysActivitiesDto();
			cal1.setTimeInMillis(Long.valueOf(action[3].toString()));
			if (formatter.format(cal1.getTime()).equals(formatter.format(cal2.getTime()))) {
				EmployeeDto emp = new EmployeeDto();
				if (!idList.contains(Long.valueOf(action[4].toString()))) {
					todaysActivitiesDtoList = new ArrayList<TodaysActivitiesDto>();
					TodaysActivitiesDto.setName(action[2].toString());
					TodaysActivitiesDto.setStart_time(Long.valueOf(action[3].toString()));
					todaysActivitiesDtoList.add(TodaysActivitiesDto);
					idList.add(Long.valueOf(action[4].toString()));
					currentActivityEmployeeList.add(emp);
					emp.setEmployeeId(Long.valueOf(action[4].toString()));
					emp.setActivities(todaysActivitiesDtoList);

				} else {
					TodaysActivitiesDto.setName(action[2].toString());
					TodaysActivitiesDto.setStart_time(Long.valueOf(action[3].toString()));
					todaysActivitiesDtoList.add(TodaysActivitiesDto);
					emp.setEmployeeId(Long.valueOf(action[4].toString()));
					emp.setActivities(todaysActivitiesDtoList);
				}

			} else {
				ac.setId(Long.valueOf(action[0].toString()));
				ac.setDuration(Long.valueOf(action[1].toString()));
				ac.setName(action[2].toString());
				ac.setTime(Long.valueOf(action[3].toString()));
				activityList.add(ac);

			}
		}


		List<Activity> sortedList = activityList.stream().sorted(Comparator.comparingLong(Activity::getTime))
				.collect(Collectors.toList());
		List<ActivitiesDTO> list = new ArrayList<ActivitiesDTO>();
		sortedList.stream().collect(Collectors.groupingBy(Activity::getName, Collectors.counting())).entrySet()
				.parallelStream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(pair -> {
					ActivitiesDTO activity = new ActivitiesDTO();
					activity.setActivity_name(pair.getKey());
					activity.setOccurrences(pair.getValue());
					list.add(activity);
				});

		List<EmployeeDto> employeeSortDto = currentActivityEmployeeList.stream()
				.sorted(Comparator.comparingLong(EmployeeDto::getEmployeeId).reversed()).collect(Collectors.toList());

		employeeSortDto.stream().forEach(data -> {
			data.setActivities(data.getActivities().stream()
					.sorted(Comparator.comparingLong(TodaysActivitiesDto::getStart_time)).collect(Collectors.toList()));
		});

		map.put("last_7_days_statistics", list);
		map.put("todays_activities", employeeSortDto);

		return map;
	}
}
