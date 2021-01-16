package com.weatherize.reports.mynest.mynestgraphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.weatherize.reports.mynest.mynestgraphql.controller.EventsResolver;
import com.weatherize.reports.mynest.mynestgraphql.controller.HelloQueryResolver;

@SpringBootApplication
public class MynestgraphqlApplication implements CommandLineRunner{
	
	@Autowired
	EventsResolver nestEventsService;
	
	@Autowired
	HelloQueryResolver helloResolver;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(MynestgraphqlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		String query = "{allEvents {timeofevent,traitkey,traitvalue}}";
		
		var events = nestEventsService.getAllEvents();

		events.forEach((dataitem) -> {
			System.out.println(String.format("Event" + ":" + dataitem));
		});
		
		System.out.println(helloResolver.hello());
	}
}
