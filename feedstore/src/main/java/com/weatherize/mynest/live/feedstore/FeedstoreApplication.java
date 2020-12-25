package com.weatherize.mynest.live.feedstore;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.weatherize.mynest.live.feedstore.model.TemperatureData;
import com.weatherize.mynest.live.feedstore.repository.MyNestThermostatLiveRepository;

@SpringBootApplication
public class FeedstoreApplication implements CommandLineRunner {

	@Autowired
	MyNestThermostatLiveRepository myNestThermostatLiveRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(FeedstoreApplication.class, args);
	}

	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		TemperatureData data = new TemperatureData();
		
		
		data.setHumidity(getRandomNumber(0,100));
		data.setTemperature(getRandomNumber(0,100));
		data.setHvacCycleOn(false);
		data.setTimeofcapture(Calendar.getInstance().getTime());
		data.setMode("Heat");
		data.setTimetotarget(getRandomNumber(0,30));

		myNestThermostatLiveRepository.save(data);

		var thermostatEntries = myNestThermostatLiveRepository.findAll();

		logger.info("All entries -> {}", thermostatEntries);

	}
}
