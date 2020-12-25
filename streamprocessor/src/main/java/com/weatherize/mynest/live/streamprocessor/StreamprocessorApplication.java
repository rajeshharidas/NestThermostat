package com.weatherize.mynest.live.streamprocessor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class StreamprocessorApplication implements CommandLineRunner {

	@Autowired
	private KafkaTemplate<String, String> template;
	
	private final CountDownLatch latch = new CountDownLatch(3);
	
	public static Logger logger = LoggerFactory.getLogger(StreamprocessorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StreamprocessorApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		this.template.send("myTopic", "foo11");
		this.template.send("myTopic", "foo12");
		this.template.send("myTopic", "foo13");
		latch.await(60, TimeUnit.SECONDS);
		logger.info("All received");
		
	}

	@KafkaListener(topics = "myTopic")
	public void listen(ConsumerRecord<?, ?> cr) throws Exception {
		logger.info(cr.toString());
		latch.countDown();
	}
}
