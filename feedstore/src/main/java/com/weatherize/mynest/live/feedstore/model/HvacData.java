package com.weatherize.mynest.live.feedstore.model;

import java.util.Date;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class HvacData {

	@PrimaryKeyColumn(name = "timeofevent", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private Date timestamp;

	private Integer temperature;
	private Integer humidity;
	private boolean hvacCycleOn;
	private String mode;

	public HvacData() {

	}

	public HvacData(Date timestamp, Integer temperature, Integer humidity, boolean hvacCycleOn, String mode) {
		super();
		this.timestamp = timestamp;
		this.temperature = temperature;
		this.humidity = humidity;
		this.hvacCycleOn = hvacCycleOn;
		this.mode = mode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
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

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "HvacData [timestamp=" + timestamp + ", temperature=" + temperature + ", humidity=" + humidity
				+ ", hvacCycleOn=" + hvacCycleOn + "]";
	}

}