package com.weatherize.reports.mynest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherize.reports.mynest.model.NestResponse;
import com.weatherize.reports.mynest.model.SensorData;
import com.weatherize.reports.mynest.repository.SensorDataRepository;
import com.weatherize.reports.mynest.service.SensorDataService;

@RestController
@RequestMapping("/api")
public class SensorDataController {

	@Autowired
	SensorDataRepository sensorRepository;

	@Autowired
	SensorDataService sensorDataService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sensordata")
	public ResponseEntity<NestResponse<SensorData>> getEntries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size) {
		try {

			NestResponse<SensorData> nestResponse = sensorDataService.GetAllSensorData(page, size);

			logger.info("Sensor data from mongodb");

			if (nestResponse.getValues().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(nestResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
