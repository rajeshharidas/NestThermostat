package com.weatherize.mynest.live.streamprocessor.model;

import java.util.Map;

public class DeviceEvent {

	private String eventId;
	
	private String timeStamp;
	
	private Map<String,String> eventTraits;
		
	public DeviceEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public DeviceEvent(String eventId, String timeStamp, Map<String,String> eventTraits) {
		super();
		this.eventId = eventId;
		this.timeStamp = timeStamp;
		this.eventTraits = eventTraits;
	}


	public String getEventId() {
		return eventId;
	}


	public void setEventId(String eventId) {
		this.eventId = eventId;
	}


	public String getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}


	public Map<String, String> getEventTraits() {
		return eventTraits;
	}


	public void setEventTraits(Map<String, String> eventTraits) {
		this.eventTraits = eventTraits;
	}
	
}
