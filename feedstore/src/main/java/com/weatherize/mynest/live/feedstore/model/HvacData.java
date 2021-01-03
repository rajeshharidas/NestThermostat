package com.weatherize.mynest.live.feedstore.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class HvacData {
	
	@PrimaryKeyColumn(name = "eventid", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private UUID eventid;
	
	private LocalDateTime timeofevent;

	private Float temperature;
	private Float humidity;
	private boolean hvacCycleOn;
	private String mode;

	public HvacData() {

	}

	public HvacData(UUID eventid, LocalDateTime timeofevent, Float temperature, Float humidity, boolean hvacCycleOn, String mode) {
		super();
		this.eventid = eventid;
		this.timeofevent = timeofevent;
		this.temperature = temperature;
		this.humidity = humidity;
		this.hvacCycleOn = hvacCycleOn;
		this.mode = mode;
	}

	public UUID getEventid() {
		return eventid;
	}

	public void setEventid(UUID eventid) {
		this.eventid = eventid;
	}

	public LocalDateTime getTimeofevent() {
		return timeofevent;
	}

	public void setTimeofevent(LocalDateTime timeofevent) {
		this.timeofevent = timeofevent;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
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

	public Float getHumidity() {
		return humidity;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "HvacData [eventid=" + eventid + ", timestamp=" + timeofevent + ", temperature=" + temperature
				+ ", humidity=" + humidity + ", hvacCycleOn=" + hvacCycleOn + ", mode=" + mode + "]";
	}

}