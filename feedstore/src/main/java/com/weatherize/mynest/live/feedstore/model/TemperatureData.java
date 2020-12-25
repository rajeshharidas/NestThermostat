package com.weatherize.mynest.live.feedstore.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class TemperatureData {

	@Id 
	private Date timeofcapture;

	private Integer temperature;
	private Integer timetotarget;
	private Integer humidity;
	private boolean hvacCycleOn;
	private String mode;


	public TemperatureData() {

	}

	public TemperatureData(Date timeofcapture, Integer temperature, Integer timetotarget, Integer humidity,
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


	public Date getTimeofcapture() {
		return timeofcapture;
	}

	public void setTimeofcapture(Date timeofcapture) {
		this.timeofcapture = timeofcapture;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public Integer getTimetotarget() {
		return timetotarget;
	}

	public void setTimetotarget(Integer timetotarget) {
		this.timetotarget = timetotarget;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "TemperatureData [timeofcapture=" + timeofcapture + ", temperature=" + temperature + ", timetotarget="
				+ timetotarget + ", humidity=" + humidity + ", hvacCycleOn=" + hvacCycleOn + ", mode=" + mode + "]";
	}

}