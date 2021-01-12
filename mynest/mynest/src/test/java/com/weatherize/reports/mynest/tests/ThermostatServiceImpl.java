package com.weatherize.reports.mynest.tests;

import com.weatherize.reports.mynest.service.ThermostatService;

public class ThermostatServiceImpl {
	public ThermostatService thermostatService;
	
	public ThermostatServiceImpl(ThermostatService thermostatService) {
		super();
		this.thermostatService = thermostatService;
	}
}