package com.weatherize.reports.mynest;

import java.text.DateFormat;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.weatherize.reports.mynest.repository.ThermostatRepository;

@SpringBootApplication
public class MynestdataApplication implements CommandLineRunner{

	@Autowired
	ThermostatRepository thermostatRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(MynestdataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	
		var thermostatEntries = thermostatRepository.findAll();
		thermostatEntries.stream().forEach(entry -> {
			System.out.println("[");
			System.out.println("Start Time: " + entry.getStartTs());
			System.out.println("End Time: " + entry.getEndTs());
			for (var cycle : entry.getCycles())
			{
				System.out.println("	--------------------------------");
				System.out.println("	Cycle: " + cycle.getCaptionText());
				System.out.println("	Duration: " + cycle.getDuration().replace('s', ' ').trim());
				System.out.println("    	Start: " + cycle.getStartTime());
				System.out.println("    	End: " + cycle.getEndTime());
				System.out.println("	--------------------------------");
			}
			System.out.println("]");
		});
		logger.info("All entries -> {}", thermostatEntries);

	}

}
