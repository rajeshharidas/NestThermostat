package com.weatherize.mynest.live.streamprocessor.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.weatherize.mynest.live.streamprocessor.config.GCPConfig;
import com.weatherize.mynest.live.streamprocessor.model.DeviceEvent;
import com.weatherize.mynest.live.streamprocessor.model.DeviceStat;
import com.weatherize.mynest.live.streamprocessor.model.TemperatureData;
import com.weatherize.mynest.live.streamprocessor.util.CVSFilesfromAWSS3;
import com.weatherize.mynest.live.streamprocessor.util.GCloudUtil;
import com.weatherize.mynest.live.streamprocessor.util.JsonFilesfromAWSS3;

@RestController
@RequestMapping("/streamapi")
public class NestStreamProcessorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NestStreamProcessorController.class);

	private final PubSubTemplate pubSubTemplate;

	private final ArrayList<Subscriber> allSubscribers;

	@Autowired
	private GCPConfig gcpConfig;

	@Autowired
	private GCloudUtil gcpUtility;

	@Autowired
	private KafkaTemplate<String, String> template;

	public NestStreamProcessorController(PubSubTemplate pubSubTemplate) {
		this.pubSubTemplate = pubSubTemplate;
		this.allSubscribers = new ArrayList<>();
	}

	@GetMapping("/devicestat")
	public ResponseEntity<String> pull(@RequestHeader("authCode") String authCode) {

		try {
			String authToken = gcpUtility.GetSDMAuthToken(authCode);

			DeviceStat deviceStat = gcpUtility.GetSDMDeviceStat(authToken);

			String convertedObject = new Gson().toJson(deviceStat, DeviceStat.class);

			return new ResponseEntity<>(convertedObject, HttpStatus.OK);

		} catch (HttpClientErrorException e) {
			String message = e.getMostSpecificCause().getMessage();
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/pull")
	public ResponseEntity<String> pull() {

		try {
			Collection<AcknowledgeablePubsubMessage> messages = this.pubSubTemplate
					.pull(gcpConfig.getSdmDeviceSubscription(), 10, true);

			if (messages.isEmpty()) {
				String message = "No messages available for retrieval.";
				return new ResponseEntity<>(message, HttpStatus.OK);
			}

			try {
				ListenableFuture<Void> ackFuture = this.pubSubTemplate.ack(messages);
				ackFuture.get();
				String message = String.format("Pulled and acked %s message(s)", messages.size());
				return new ResponseEntity<>(message, HttpStatus.OK);
			} catch (Exception ex) {
				LOGGER.warn("Acking failed.", ex);
				String message = "Acking failed";
				return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			LOGGER.error("Acking failed.", ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/subscribe")
	public ResponseEntity<String> subscribe() {
		try {
			Subscriber subscriber = this.pubSubTemplate.subscribe(gcpConfig.getSdmDeviceSubscription(), (message) -> {

				String json = message.getPubsubMessage().getData().toStringUtf8();

				LOGGER.info("Message received from " + gcpConfig.getSdmDeviceSubscription() + " subscription: " + json);

				try {
					DeviceEvent deviceEvent = gcpUtility.GetSDMDeviceEvent(json);
					String convertedObject = new Gson().toJson(deviceEvent, DeviceEvent.class);
					LOGGER.info("Sending to device: " + convertedObject);
					this.template.send("myNestEventTopic", convertedObject);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				message.ack();
			});

			this.allSubscribers.add(subscriber);
			return new ResponseEntity<>("Subscribed to hvac events!", HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			String message = e.getMostSpecificCause().getMessage();
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/loadtemperaturedatafroms3")
	public ResponseEntity<String> loadtemperaturedatafroms3() {

		try {
			CVSFilesfromAWSS3 csvFilefromAWSS3 = new CVSFilesfromAWSS3();
			List<TemperatureData> data = csvFilefromAWSS3.GetTemperatureDatafromS3();

			data.forEach(item -> {

				if (item != null) {
					JsonObject json = new JsonObject();
					json.addProperty("timeofcapture", DateFormat.getInstance().format(item.getTimeofcapture()));
					json.addProperty("temperature", item.getTemperature());
					json.addProperty("humidity", item.getHumidity());
					json.addProperty("timetotarget", item.getTimetotarget());
					json.addProperty("mode", item.getMode());
					json.addProperty("hvaccycleon", false);

					this.template.send("myNestTopic", json.toString());
				}
			});

			return new ResponseEntity<>("Uploaded temperature data!", HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			String message = e.getMostSpecificCause().getMessage();
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/loadhvaceventsfroms3")
	public ResponseEntity<String> loadhvaceventsfroms3() {

		try {
			JsonFilesfromAWSS3 jsonFilefromAWSS3 = new JsonFilesfromAWSS3();
			List<DeviceEvent> deviceEvents = jsonFilefromAWSS3.GetDeviceEventDatafromS3();

			deviceEvents.forEach(item -> {

				if (item != null) {
					String convertedObject = new Gson().toJson(item, DeviceEvent.class);
					LOGGER.info("Sending to device: " + convertedObject);
					this.template.send("myNestEventTopic", convertedObject);
				}
			});

			return new ResponseEntity<>("Uploaded hvac events and temperature data!", HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			String message = e.getMostSpecificCause().getMessage();
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}