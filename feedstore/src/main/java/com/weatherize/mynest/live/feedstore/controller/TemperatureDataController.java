package com.weatherize.mynest.live.feedstore.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherize.mynest.live.feedstore.model.TemperatureData;
import com.weatherize.mynest.live.feedstore.repository.MyNestThermostatLiveRepository;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/dataapi")
public class TemperatureDataController {

	@Autowired
	MyNestThermostatLiveRepository thermostatRepository;

	@GetMapping("/temperaturedata")
	public ResponseEntity<List<TemperatureData>> getAllTemperatureData(@RequestParam(required = false) Date timeStamp) {
		try {
			List<TemperatureData> temperatureData = new ArrayList<TemperatureData>();

			if (timeStamp == null)
				thermostatRepository.findAll().forEach(temperatureData::add);
			else
				thermostatRepository.findById(timeStamp).stream().forEach(temperatureData::add);

			if (temperatureData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(temperatureData, HttpStatus.OK);
		} catch (Exception e) {
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

}