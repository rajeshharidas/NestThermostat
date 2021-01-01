package com.weatherize.mynest.live.feedstore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

	public Double getRandomNumber(int min, int max) {
	    return (Double) ((Math.random() * (max - min)) + min);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		TemperatureData data = new TemperatureData();
		
		data.setDatasetid(0);
		data.setHumidity(getRandomNumber(0,100).floatValue());
		data.setTemperature(getRandomNumber(0,100).floatValue());
		data.setHvacCycleOn(false);
		
		DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date date = Calendar.getInstance().getTime();

		DateFormat cstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		cstFormat.setTimeZone(TimeZone.getTimeZone("CST"));

		data.setTimeofcapture(LocalDateTime.parse(cstFormat.format(date)));
		data.setMode("Heat");
		data.setMonth(12);
		data.setYear(2020);
		data.setTimetotarget(getRandomNumber(0,30).floatValue());
		
		logger.info("Saving data - ", LocalDateTime.parse(cstFormat.format(date)));

		//myNestThermostatLiveRepository.save(data);

		var thermostatEntries = myNestThermostatLiveRepository.findAll();

		logger.info("All entries -> {}", thermostatEntries);

	}
}
