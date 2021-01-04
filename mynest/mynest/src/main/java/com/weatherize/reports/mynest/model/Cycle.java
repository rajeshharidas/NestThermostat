package com.weatherize.reports.mynest.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.mongodb.core.mapping.Field;


public class Cycle {

	@Field(value = "caption.plainText")
	private String plainText;

	@Field(value = "duration")
	private String duration;

	@Field(value = "heat1")
	private Boolean heatOn;
	
	@Field(value = "cool1")
	private Boolean acOn;
	
	@Field(value = "fan")
	private Boolean fanOn;
	
	public Cycle(String plainText, String duration, Boolean heatOn, Boolean acOn, Boolean fanOn, String startTime,
			String endTime, String captionText) {
		super();
		this.plainText = plainText;
		this.duration = duration;
		this.heatOn = heatOn;
		this.acOn = acOn;
		this.fanOn = fanOn;
		this.startTime = startTime;
		this.endTime = endTime;
		this.captionText = captionText;
	}

	public Boolean getHeatOn() {
		return this.heatOn;
	}

	public void setHeatOn(Boolean heatOn) {
		this.heatOn = heatOn;
	}

	public Boolean getAcOn() {
		return this.acOn;
	}

	public void setAcOn(Boolean acOn) {
		this.acOn = acOn;
	}

	public Boolean getFanOn() {
		return this.fanOn;
	}

	public void setFanOn(Boolean fanOn) {
		this.fanOn = fanOn;
	}
	
	@Field(value = "caption.parameters.startTime")
	private String startTime;

	@Field(value = "caption.parameters.endTime")
	private String endTime;

	private String captionText;

	public String getDuration() {
		return duration;
	}

	private String convertDate(String dateTime) {

		try {
			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date = utcFormat.parse(dateTime);

			DateFormat cstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			cstFormat.setTimeZone(TimeZone.getTimeZone("CST"));

			return cstFormat.format(date);
		} catch (ParseException pex) {
			System.out.println(pex.getMessage());
		}
		return dateTime;
	}

	public String getStartTime() {
		return this.convertDate(startTime);
	}

	public String getEndTime() {
		return this.convertDate(endTime);
	}

	private String getTime(String dateTime) {
		try {
			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date = utcFormat.parse(dateTime);

			DateFormat cstFormat = new SimpleDateFormat("hh:mm a");
			cstFormat.setTimeZone(TimeZone.getTimeZone("CST"));
			
			return cstFormat.format(date.getTime());

		} catch (ParseException pex) {
			System.out.println(pex.getMessage());
		}
		
		return dateTime;
	}
	
	public String getCaptionText() {
		
		
		if (this.getTime(endTime).contains("AM"))
		{
			this.captionText = "Morning" ;				
		}
		else if (this.getTime(endTime).contains("PM"))
		{
			this.captionText = "Afternoon" ;		
		}
		else if (this.getTime(endTime).contains("12:00 PM"))
		{
			this.captionText = "Noon" ;	
		}		
		
		if (this.heatOn == true)
			this.captionText = this.captionText + " Heating";
		else if (this.acOn == true)
			this.captionText = this.captionText + " Cooling";
		else if (this.fanOn == true)
			this.captionText = this.captionText + " Fan On";
		else 
			this.captionText = this.captionText + " Cycle";
			
		return this.captionText;
	}

	public Cycle() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Cycle [plainText=" + plainText + ", duration=" + duration + ", HeatOn=" + heatOn + ", AcOn=" + acOn
				+ ", FanOn=" + fanOn + ", startTime=" + startTime + ", endTime=" + endTime + ", captionText="
				+ captionText + "]";
	}

	public String getPlainText() {
		return plainText;
	}

}
