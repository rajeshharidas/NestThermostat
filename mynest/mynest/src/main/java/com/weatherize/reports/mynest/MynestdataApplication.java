package com.weatherize.reports.mynest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.weatherize.reports.mynest.repository.SensorDataRepository;
import com.weatherize.reports.mynest.repository.ThermostatRepository;

@SpringBootApplication
public class MynestdataApplication implements CommandLineRunner{

	@Autowired
	ThermostatRepository thermostatRepository;
	
	@Autowired
	SensorDataRepository sensorDataRepository;
	
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
		logger.info("All thermostat entries -> {}", thermostatEntries);

		Pageable paging = PageRequest.of(0, 20,
				Sort.by("Date").descending().and(Sort.by("Time").descending()));
		var sensorDatas = sensorDataRepository.findAllByOrderByDateCapturedDesc(paging);
		sensorDatas.stream().forEach(entry -> {
			System.out.println("[");
			System.out.println(entry.getDateCaptured());
			System.out.println(entry.getTimeCaptured());
			System.out.println(entry.getAvgTemp());
			System.out.println(entry.getAvgHumidity());			
			System.out.println("]");
		});
		logger.info("All sensor entries -> {}", sensorDatas);
	}

}
