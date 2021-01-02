package com.weatherize.mynest.live.feedstore.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.weatherize.mynest.live.feedstore.controller.TemperatureDataController;
import com.weatherize.mynest.live.feedstore.model.FeedResponse;
import com.weatherize.mynest.live.feedstore.model.TemperatureData;
import com.weatherize.mynest.live.feedstore.repository.MyNestThermostatLiveRepository;

@Service
public class TemperatureDataService {

	private static final Logger logger = LoggerFactory.getLogger(TemperatureDataController.class);

	@Autowired
	MyNestThermostatLiveRepository thermostatRepository;

	@Cacheable("TemperatureData")
	public List<TemperatureData> GetAllTemperatureData() {
		List<TemperatureData> temperatureData = new ArrayList<TemperatureData>();

		final PageRequest pageRequest = PageRequest.of(0, 200);

		Pageable pageable = CassandraPageRequest.of(pageRequest, null);

		logger.info("Data from the source. Cache miss!");

		Slice<TemperatureData> pageData = thermostatRepository.findByDatasetidOrderByTimeofcaptureDesc(0, pageable);

		pageData.forEach(temperatureData::add);

		do {

			// consume slice
			if (pageData.hasNext()) {
				pageable = pageData.nextPageable();
				pageData = thermostatRepository.findByDatasetidOrderByTimeofcaptureDesc(0, pageable);
				pageData.forEach(temperatureData::add);

			} else {
				break;
			}
		} while (!pageData.getContent().isEmpty());

		return temperatureData;

	}
}
