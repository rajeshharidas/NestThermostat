package com.weatherize.mynest.live.feedstore.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.weatherize.mynest.live.feedstore.model.TemperatureData;

public interface MyNestThermostatLiveRepository extends CassandraRepository<TemperatureData, Date> {
	@AllowFiltering
	List<TemperatureData> findByMode(String mode);

	@AllowFiltering
	List<TemperatureData> findByHvacCycleOn(boolean hvacCycleOn);

	Slice<TemperatureData> findAll(Pageable pageable);
}
