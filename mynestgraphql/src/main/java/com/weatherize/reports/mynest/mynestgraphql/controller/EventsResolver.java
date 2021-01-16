package com.weatherize.reports.mynest.mynestgraphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.weatherize.reports.mynest.mynestgraphql.model.HvacData;
import com.weatherize.reports.mynest.mynestgraphql.service.NestGraphqlEventService;

@Service
public class EventsResolver implements GraphQLQueryResolver {
	private static Logger logger = LoggerFactory.getLogger(EventsResolver.class);

	@Autowired
	private NestGraphqlEventService nestEventsService;

	public List<HvacData> getAllEvents() {
		logger.info("Entering getAllEvents@EventsController");

		List<HvacData> hvacData = new ArrayList<HvacData>();

		try {

			hvacData = nestEventsService.getEvents();

			logger.info("Return data from controller!");

			return hvacData;

		} catch (Exception e) {
			logger.error(e.getMessage());

			return hvacData;
		}
	}

}