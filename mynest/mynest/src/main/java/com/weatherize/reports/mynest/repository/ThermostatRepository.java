package com.weatherize.reports.mynest.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.weatherize.reports.mynest.model.Cycle;
import com.weatherize.reports.mynest.model.Thermostat;

public interface ThermostatRepository extends MongoRepository<Thermostat, String> {
}
