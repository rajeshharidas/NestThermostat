package com.weatherize.reports.mynest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.weatherize.reports.mynest.model.NestResponse;
import com.weatherize.reports.mynest.model.Thermostat;
import com.weatherize.reports.mynest.repository.ThermostatRepository;

@Service
public class ThermostatService {

	@Autowired
	ThermostatRepository nestdataRepository;

	public NestResponse<Thermostat> GetAllThermostatEntries(int page, int size) {
		List<Thermostat> entries = new ArrayList<Thermostat>();
		Pageable paging = PageRequest.of(page, size);

		Page<Thermostat> pageData = nestdataRepository.findAll(paging);

		NestResponse<Thermostat> nestResponse = new NestResponse<Thermostat>();

		nestResponse.setTotalElements(pageData.getTotalElements());
		nestResponse.setNumber(pageData.getNumber());
		nestResponse.setTotalPages(pageData.getTotalPages());
		nestResponse.setSize(pageData.getSize());

		pageData.forEach(entries::add);
		nestResponse.setValues(entries);

		return nestResponse;
	}
}
