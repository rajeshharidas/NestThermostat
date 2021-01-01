package com.weatherize.mynest.live.feedstore.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.weatherize.mynest.live.feedstore.model.FeedResponse;
import com.weatherize.mynest.live.feedstore.model.TemperatureData;
import com.weatherize.mynest.live.feedstore.repository.MyNestThermostatLiveRepository;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/dataapi")
public class TemperatureDataController {

	private static final Logger logger = LoggerFactory.getLogger(TemperatureDataController.class);

	@Autowired
	MyNestThermostatLiveRepository thermostatRepository;

	@GetMapping("/temperaturedata")
	public ResponseEntity<FeedResponse<TemperatureData>> getAllTemperatureData() {
		try {
			List<TemperatureData> temperatureData = new ArrayList<TemperatureData>();

			FeedResponse<TemperatureData> nestResponse = new FeedResponse<TemperatureData>();

			final PageRequest pageRequest = PageRequest.of(0, 5000);

			Pageable pageable = CassandraPageRequest.of(pageRequest, null);

			Slice<TemperatureData> pageData = thermostatRepository.findByDatasetidOrderByTimeofcaptureDesc(0, pageable);

			pageData.forEach(temperatureData::add);

			do {

				// consume slice
				if (pageData.hasNext()) {
					pageable = pageData.nextPageable();
					pageData = thermostatRepository.findByDatasetidOrderByTimeofcaptureDesc(0, pageable);
					pageData.forEach(temperatureData::add);

				} else {
					break;
				}
			} while (!pageData.getContent().isEmpty());
		

			nestResponse.setValues(temperatureData);

			if (temperatureData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(nestResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/temperaturedata/{timeofcapture}")
	public ResponseEntity<TemperatureData> getTemperatureData(@PathVariable("timeofcapture") Date timeStamp) {
		Optional<TemperatureData> temperatureData = thermostatRepository.findById(timeStamp);

		if (temperatureData.isPresent()) {
			return new ResponseEntity<>(temperatureData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/temperaturedata")
	public ResponseEntity<TemperatureData> createTemperatureData(@RequestBody TemperatureData tempData) {
		try {
			TemperatureData _temperatureData = thermostatRepository.save(new TemperatureData(tempData));
			return new ResponseEntity<>(_temperatureData, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/temperaturedata/{timeofcapture}")
	public ResponseEntity<TemperatureData> updateTemperatureData(@PathVariable("timeofcapture") Date timeStamp,
			@RequestBody TemperatureData tempData) {
		Optional<TemperatureData> temperatureData = thermostatRepository.findById(timeStamp);

		if (temperatureData.isPresent()) {
			TemperatureData _temperatureData = temperatureData.get();
			_temperatureData.setTimetotarget(tempData.getTimetotarget());
			_temperatureData.setTemperature(tempData.getTemperature());
			_temperatureData.setHvacCycleOn(tempData.isHvacCycleOn());
			_temperatureData.setHumidity(tempData.getHumidity());
			_temperatureData.setMode(tempData.getMode());

			return new ResponseEntity<>(thermostatRepository.save(_temperatureData), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/temperaturedata/{timeofcapture}")
	public ResponseEntity<HttpStatus> deleteTemperatureData(@PathVariable("timeofcapture") Date timeStamp) {
		try {
			thermostatRepository.deleteById(timeStamp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/temperaturedata")
	public ResponseEntity<HttpStatus> deleteAllTemperatureData() {
		try {
			thermostatRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/temperaturedata/mode")
	public ResponseEntity<List<TemperatureData>> findByMode(@PathVariable("mode") String mode) {
		try {
			List<TemperatureData> temperatureData = thermostatRepository.findByMode(mode);

			if (temperatureData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(temperatureData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	@KafkaListener(topics = "myNestTopic")
	public void listen(ConsumerRecord<?, ?> cr) throws Exception {
		String json = cr.value().toString();
		logger.info("Incoming json string from nest kafka topic: " + json);
		JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);

		if (convertedObject.isJsonObject()) {
			try {
				logger.info("Converted json object: " + convertedObject.toString());
				TemperatureData tempData = new TemperatureData();
				tempData.setDatasetid(0);
				tempData.setHumidity(convertedObject.get("humidity").getAsFloat());
				tempData.setTemperature(convertedObject.get("temperature").getAsFloat());

				DateFormat cstFormat = new SimpleDateFormat("MM/dd/yy','HH:mm a");
				cstFormat.setTimeZone(TimeZone.getTimeZone("CST"));
				LocalDateTime date = convertToLocalDateTimeViaInstant(
						cstFormat.parse(convertedObject.get("timeofcapture").getAsString()));
				tempData.setTimeofcapture(date);
				tempData.setMonth(date.getMonthValue());
				tempData.setYear(date.getYear());
				tempData.setMode(convertedObject.get("mode").getAsString());
				tempData.setHvacCycleOn(convertedObject.get("hvaccycleon").getAsBoolean());
				tempData.setTimetotarget(convertedObject.get("timetotarget").getAsFloat());

				logger.info("Saving data - ", date);

				TemperatureData _temperatureData = thermostatRepository.save(tempData);
				logger.info("Parsed json string as Temperature object: " + _temperatureData.toString());

			} catch (Exception e) {
				logger.error("Kafka Listener error: " + e.getMessage());
			}
		}
	}
}