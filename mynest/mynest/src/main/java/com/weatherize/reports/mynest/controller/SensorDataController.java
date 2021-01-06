package com.weatherize.reports.mynest.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherize.reports.mynest.model.NestResponse;
import com.weatherize.reports.mynest.model.SensorData;
import com.weatherize.reports.mynest.repository.SensorDataRepository;

@RestController
@RequestMapping("/api")
public class SensorDataController {

	@Autowired
	SensorDataRepository sensorRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sensordata")
	public ResponseEntity<NestResponse<SensorData>> getEntries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size) {
		try {
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
			
			logger.info("Sensor data from mongodb");

			if (entries.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(nestResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
