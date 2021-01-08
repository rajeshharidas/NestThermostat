package com.weatherize.reports.mynest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.weatherize.reports.mynest.model.NestResponse;
import com.weatherize.reports.mynest.model.SensorData;
import com.weatherize.reports.mynest.repository.SensorDataRepository;

@Service
public class SensorDataService {

	@Autowired
	SensorDataRepository sensorRepository;
	
	public NestResponse<SensorData> GetAllSensorData(int page, int size) {
		
		List<SensorData> entries = new ArrayList<SensorData>();
		Pageable paging = PageRequest.of(page, size,
				Sort.by("Date").descending().and(Sort.by("Time").descending()));

		Page<SensorData> pageData = sensorRepository.findAllByOrderByDateCapturedDesc(paging);
		
		NestResponse<SensorData> nestResponse = new NestResponse<SensorData>();
		
		nestResponse.setTotalElements(pageData.getTotalElements());
		nestResponse.setNumber(pageData.getNumber());
		nestResponse.setTotalPages(pageData.getTotalPages());
		nestResponse.setSize(pageData.getSize());
		
		pageData.forEach(data -> {
			if (data.getAvgTemp() != null && data.getAvgHumidity() != null)
				entries.add(data);
		});
		
		nestResponse.setValues(entries);
		
		return nestResponse;
	}
}
