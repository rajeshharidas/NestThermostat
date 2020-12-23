package com.weatherize.reports.mynest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherize.reports.mynest.annotation.CrossOriginsList;
import com.weatherize.reports.mynest.model.Thermostat;
import com.weatherize.reports.mynest.repository.ThermostatRepository;

@RestController
@RequestMapping("/api")
public class ThermostatController {

	@Autowired
	ThermostatRepository nestdataRepository;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/thermostats")
	public ResponseEntity<List<Thermostat>> getEntries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "15") int size) {
		try {
			List<Thermostat> entries = new ArrayList<Thermostat>();
			Pageable paging = PageRequest.of(page, size);

			nestdataRepository.findAll(paging).forEach(entries::add);

			if (entries.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(entries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
