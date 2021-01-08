package com.weatherize.mynest.live.streamprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.weatherize.mynest.live.streamprocessor.controller.NestStreamProcessorController;
import com.weatherize.mynest.live.streamprocessor.util.GCloudUtil;

@SpringBootApplication
@EnableConfigurationProperties
public class StreamprocessorApplication implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(StreamprocessorApplication.class);

	@Autowired
	NestStreamProcessorController nestStreamController;

	public static void main(String[] args) {
		SpringApplication.run(StreamprocessorApplication.class, args);

	}

	@Bean
	public GCloudUtil gcpUtility() {
		return new GCloudUtil();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public void run(String... args) throws Exception {
		// nestStreamController.loadtemperaturedatafroms3();
		// nestStreamController.loadhvaceventsfroms3();
	}

}
