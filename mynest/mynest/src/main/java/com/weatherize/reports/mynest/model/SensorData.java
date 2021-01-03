package com.weatherize.reports.mynest.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "SensorData")
@CompoundIndexes({ @CompoundIndex(name = "DateTimeIndex", def = "{'Date' : -1, 'Time' : -1}", unique=true) })
public class SensorData {

	@Id
	private String id;

	@Field(value = "Date")
	private String dateCaptured;
	@Field(value = "Time")
	private String timeCaptured;
	@Field(value = "avg(temp)")
	private String avgTemp;
	@Field(value = "avg(humidity)")
	private String avgHumidity;

	private String timestamp;
	private Date sortTimestamp;
	

	public SensorData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SensorData [id=" + id + ", dateCaptured=" + dateCaptured + ", timeCaptured=" + timeCaptured
				+ ", avgTemp=" + avgTemp + ", avgHumidity=" + avgHumidity + "]";
	}

	private String getTimeStamp(String dateCaptured, String timeCaptured) {

		String dateTime = dateCaptured + 'T' + timeCaptured + ":00Z";
		try {

			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

			Date date = utcFormat.parse(dateTime);

			DateFormat cstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			cstFormat.setTimeZone(TimeZone.getTimeZone("CST"));

			this.sortTimestamp = cstFormat.parse(cstFormat.format(date));
			return cstFormat.format(date);

		} catch (ParseException pex) {
			System.out.println(pex.getMessage());
		}
		return dateTime;
	}

	public String getTimestamp() {

		timestamp = getTimeStamp(this.dateCaptured, this.timeCaptured);
		return timestamp;
	}

	public Date getSortTimestamp() {
		return this.sortTimestamp;
	}

	public SensorData(String id, String dateCaptured, String timeCaptured, String avgTemp, String avgHumidity) {
		super();
		this.id = id;
		this.dateCaptured = dateCaptured;
		this.timeCaptured = timeCaptured;
		this.avgTemp = avgTemp;
		this.avgHumidity = avgHumidity;
		
	}

	public String getId() {
		return id;
	}

	public String getDateCaptured() {
		return dateCaptured;
	}

	public void setDateCaptured(String dateCaptured) {
		this.dateCaptured = dateCaptured;
	}

	public String getTimeCaptured() {
		return timeCaptured;
	}

	public void setTimeCaptured(String timeCaptured) {

		this.timeCaptured = timeCaptured;
		this.timestamp = getTimeStamp(this.dateCaptured, this.timeCaptured);
	}

	public String getAvgTemp() {		
		return avgTemp;
	}

	public void setAvgTemp(String avgTemp) {
		this.avgTemp = avgTemp;		
	}

	public String getAvgHumidity() {
		return avgHumidity;
	}

	public void setAvgHumidity(String avgHumidity) {
		this.avgHumidity = avgHumidity;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}