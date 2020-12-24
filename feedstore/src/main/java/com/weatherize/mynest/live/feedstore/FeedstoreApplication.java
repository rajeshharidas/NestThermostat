package com.weatherize.mynest.live.feedstore;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.weatherize.mynest.live.feedstore.model.TemperatureData;
import com.weatherize.mynest.live.feedstore.repository.MyNestThermostatLiveRepository;

@SpringBootApplication
public class FeedstoreApplication implements CommandLineRunner{

	@Autowired
	MyNestThermostatLiveRepository myNestThermostatLiveRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(FeedstoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	
		TemperatureData data = new TemperatureData();
		data.setHumidity(35);
		data.setTemperature(18);
		data.setHvacCycleOn(false);
		data.setTimeofcapture(Calendar.getInstance().getTime());
		data.setMode("Heat");
		data.setTimetotarget(10);
		
		myNestThermostatLiveRepository.save(data);

		var thermostatEntries = myNestThermostatLiveRepository.findAll();

		logger.info("All entries -> {}", thermostatEntries);

	}
}
