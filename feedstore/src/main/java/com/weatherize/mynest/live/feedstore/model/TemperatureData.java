package com.weatherize.mynest.live.feedstore.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class TemperatureData {

	@Id 
	private LocalDateTime timeofcapture;

	private Float temperature;
	private Float timetotarget;
	private Float humidity;
	private boolean hvacCycleOn;
	private String mode;


	public TemperatureData() {

	}

	public TemperatureData(LocalDateTime timeofcapture, Float temperature, Float timetotarget, Float humidity,
			boolean hvacCycleOn, String mode) {
		super();
		this.timeofcapture = timeofcapture;
		this.temperature = temperature;
		this.timetotarget = timetotarget;
		this.humidity = humidity;
		this.hvacCycleOn = hvacCycleOn;
		this.mode = mode;
	}
	
	public TemperatureData(TemperatureData data) {
		super();
		this.timeofcapture = data.getTimeofcapture();
		this.temperature = data.getTemperature();
		this.timetotarget =data.getTimetotarget();
		this.humidity =data.getHumidity();
		this.hvacCycleOn =data.isHvacCycleOn();
		this.mode = data.getMode();
	}

	public boolean isHvacCycleOn() {
		return hvacCycleOn;
	}

	public void setHvacCycleOn(boolean hvacCycleOn) {
		this.hvacCycleOn = hvacCycleOn;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}


	public LocalDateTime getTimeofcapture() {
		return timeofcapture;
	}

	public void setTimeofcapture(LocalDateTime timeofcapture) {
		this.timeofcapture = timeofcapture;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Float getTimetotarget() {
		return timetotarget;
	}

	public void setTimetotarget(Float timetotarget) {
		this.timetotarget = timetotarget;
	}

	public Float getHumidity() {
		return humidity;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "TemperatureData [timeofcapture=" + timeofcapture + ", temperature=" + temperature + ", timetotarget="
				+ timetotarget + ", humidity=" + humidity + ", hvacCycleOn=" + hvacCycleOn + ", mode=" + mode + "]";
	}

}