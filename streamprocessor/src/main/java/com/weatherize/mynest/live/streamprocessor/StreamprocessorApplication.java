package com.weatherize.mynest.live.streamprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamprocessorApplication implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(StreamprocessorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StreamprocessorApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		
	}

	
}
