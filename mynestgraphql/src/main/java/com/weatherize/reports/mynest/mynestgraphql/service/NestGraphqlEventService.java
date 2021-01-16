package com.weatherize.reports.mynest.mynestgraphql.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.weatherize.reports.mynest.mynestgraphql.model.HvacData;
import com.weatherize.reports.mynest.mynestgraphql.repository.NestEventsRepository;

@Component
public class NestGraphqlEventService {

	private static final Logger logger = LoggerFactory.getLogger(NestGraphqlEventService.class);

	@Autowired
	private NestEventsRepository nestEventsRepository;

	
	public List<HvacData> getEvents() {

		List<HvacData> hvacData = new ArrayList<HvacData>();

		final PageRequest pageRequest = PageRequest.of(0, 200);

		Pageable pageable = CassandraPageRequest.of(pageRequest, null);

		logger.info("Data from the source. Cache miss!");

		Slice<HvacData> pageData = nestEventsRepository.findAll(pageable);

		pageData.forEach(hvacData::add);

		do {

			// consume slice
			if (pageData.hasNext()) {
				pageable = pageData.nextPageable();
				pageData = nestEventsRepository.findAll(pageable);
				pageData.forEach(hvacData::add);

			} else {
				break;
			}
		} while (!pageData.getContent().isEmpty());

		return hvacData;
	}
}
