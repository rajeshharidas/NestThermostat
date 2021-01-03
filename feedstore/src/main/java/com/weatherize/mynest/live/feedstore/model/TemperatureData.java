package com.weatherize.mynest.live.feedstore.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("TemperatureData")
public class TemperatureData {


	@Id
	private int datasetid;
	private int year;
	private int month;
	private LocalDateTime timeofcapture;
	private Float temperature;
	private Float timetotarget;
	private Float humidity;
	private boolean hvacCycleOn;
	private String mode;	
	
	
	public TemperatureData(int year, int datasetid, int month, Float temperature, Float timetotarget, Float humidity,
			boolean hvacCycleOn, String mode, LocalDateTime timeofcapture) {
		super();
		this.year = year;
		this.datasetid = datasetid;
		this.month = month;
		this.temperature = temperature;
		this.timetotarget = timetotarget;
		this.humidity = humidity;
		this.hvacCycleOn = hvacCycleOn;
		this.mode = mode;
		this.timeofcapture = timeofcapture;
	}

	
	
	public int getDatasetid() {
		return datasetid;
	}


	public void setDatasetid(int datasetid) {
		this.datasetid = datasetid;
	}

	
	
	public TemperatureData(int month, int year, LocalDateTime timeofcapture, Float temperature, Float timetotarget,
			Float humidity, boolean hvacCycleOn, String mode) {
		super();
		this.month = month;
		this.year = year;
		this.timeofcapture = timeofcapture;
		this.temperature = temperature;
		this.timetotarget = timetotarget;
		this.humidity = humidity;
		this.hvacCycleOn = hvacCycleOn;
		this.mode = mode;
	}

	public TemperatureData(int year, int datasetid, int month, LocalDateTime timeofcapture, Float temperature,
			Float timetotarget, Float humidity, boolean hvacCycleOn, String mode) {
		super();
		this.year = year;
		this.datasetid = datasetid;
		this.month = month;
		this.timeofcapture = timeofcapture;
		this.temperature = temperature;
		this.timetotarget = timetotarget;
		this.humidity = humidity;
		this.hvacCycleOn = hvacCycleOn;
		this.mode = mode;
	}




	public TemperatureData() {

	}

	
	public TemperatureData(TemperatureData data) {
		super();
		this.timeofcapture = data.getTimeofcapture();
		this.temperature = data.getTemperature();
		this.timetotarget =data.getTimetotarget();
		this.humidity =data.getHumidity();
		this.hvacCycleOn =data.isHvacCycleOn();
		this.mode = data.getMode();
		this.month = data.getMonth();
		this.year = data.getYear();
	}

	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
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
		temperature = (temperature * (9/5)) + 32; 
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
		return "TemperatureData [year=" + year + ", datasetid=" + datasetid + ", month=" + month + ", temperature="
				+ temperature + ", timetotarget=" + timetotarget + ", humidity=" + humidity + ", hvacCycleOn="
				+ hvacCycleOn + ", mode=" + mode + ", timeofcapture=" + timeofcapture + "]";
	}

}