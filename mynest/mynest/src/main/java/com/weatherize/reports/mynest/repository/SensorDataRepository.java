package com.weatherize.reports.mynest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.weatherize.reports.mynest.model.SensorData;

public interface SensorDataRepository extends MongoRepository<SensorData, String> {

}
