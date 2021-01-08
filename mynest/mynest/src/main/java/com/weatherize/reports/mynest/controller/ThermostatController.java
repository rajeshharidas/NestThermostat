package com.weatherize.reports.mynest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherize.reports.mynest.model.NestResponse;
import com.weatherize.reports.mynest.model.Thermostat;
import com.weatherize.reports.mynest.repository.ThermostatRepository;
import com.weatherize.reports.mynest.service.ThermostatService;

@RestController
@RequestMapping("/api")
public class ThermostatController {

	@Autowired
	ThermostatRepository nestdataRepository;

	@Autowired
	ThermostatService thermostatService;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/thermostats")
	public ResponseEntity<NestResponse<Thermostat>> getEntries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size) {
		try {

			NestResponse<Thermostat> nestResponse = thermostatService.GetAllThermostatEntries(page, size);

			if (nestResponse.getValues().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(nestResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
