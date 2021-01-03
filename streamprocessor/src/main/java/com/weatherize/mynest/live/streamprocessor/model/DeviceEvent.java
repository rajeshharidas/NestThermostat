package com.weatherize.mynest.live.streamprocessor.model;

public class DeviceEvent {

	private String timeStamp;
	
	private float ambientHumidityPercent;
	private String connectivityStatus;
	private String fanTimerMode;
	private String thermostatMode;
	private String hvacStatus;
	private float ambientTemperatureCelsius;
	
	
	public DeviceEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public DeviceEvent(String timeStamp, float ambientHumidityPercent, String connectivityStatus, String fanTimerMode,
			String thermostatMode, String hvacStatus, float ambientTemperatureCelsius) {
		super();
		this.timeStamp = timeStamp;
		this.ambientHumidityPercent = ambientHumidityPercent;
		this.connectivityStatus = connectivityStatus;
		this.fanTimerMode = fanTimerMode;
		this.thermostatMode = thermostatMode;
		this.hvacStatus = hvacStatus;
		this.ambientTemperatureCelsius = ambientTemperatureCelsius;
	}


	public String getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public float getAmbientHumidityPercent() {
		return ambientHumidityPercent;
	}
	public void setAmbientHumidityPercent(float ambientHumidityPercent) {
		this.ambientHumidityPercent = ambientHumidityPercent;
	}
	public String getConnectivityStatus() {
		return connectivityStatus;
	}
	public void setConnectivityStatus(String connectivityStatus) {
		this.connectivityStatus = connectivityStatus;
	}
	public String getFanTimerMode() {
		return fanTimerMode;
	}
	public void setFanTimerMode(String fanTimerMode) {
		this.fanTimerMode = fanTimerMode;
	}
	public String getThermostatMode() {
		return thermostatMode;
	}
	public void setThermostatMode(String thermostatMode) {
		this.thermostatMode = thermostatMode;
	}
	public String getHvacStatus() {
		return hvacStatus;
	}
	public void setHvacStatus(String hvacStatus) {
		this.hvacStatus = hvacStatus;
	}
	public float getAmbientTemperatureCelsius() {
		return ambientTemperatureCelsius;
	}
	public void setAmbientTemperatureCelsius(float ambientTemperatureCelsius) {
		this.ambientTemperatureCelsius = ambientTemperatureCelsius;
	}
}
