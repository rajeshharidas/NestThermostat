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
			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
			this.captionText = "Morning Cycle" ;		
		}
		else if (this.getTime(endTime).contains("PM"))
		{
			this.captionText = "Afternoon Cycle" ;		
		}
		else if (this.getTime(endTime).contains("12:00 PM"))
		{
			this.captionText = "Noon Cycle" ;	
		}		
			
		return this.captionText;
	}

	public Cycle(String plainText, String duration, String startTime, String endTime, String captionText) {
		super();
		this.plainText = plainText;
		this.duration = duration;
		this.startTime = startTime;
		this.endTime = endTime;
		this.captionText = captionText;
	}

	public Cycle() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Cycle [captionText=" + captionText + ", duration=" + duration + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}

	public String getPlainText() {
		return plainText;
	}

}
