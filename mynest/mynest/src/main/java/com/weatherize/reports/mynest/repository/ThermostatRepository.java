package com.weatherize.reports.mynest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.weatherize.reports.mynest.model.Thermostat;

@Repository
public interface ThermostatRepository extends MongoRepository<Thermostat, String> {
}
