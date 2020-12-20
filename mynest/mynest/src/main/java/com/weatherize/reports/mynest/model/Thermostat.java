package com.weatherize.reports.mynest.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Thermostat")
public class Thermostat {

	@Override
	public String toString() {
		return "Thermostat [id=" + id + ", startTs=" + startTs + ", endTs=" + endTs + ", cycles=" + cycles + "]";
	}

	public Thermostat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Thermostat(String id, String startTs, String endTs, List<Cycle> cycles) {
		super();
		this.id = id;
		this.startTs = startTs;
		this.endTs = endTs;
		this.cycles = cycles;
	}

	@Id
	private String id;

	private String startTs;
	private String endTs;

	private List<Cycle> cycles;

	public String getId() {
		return id;
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

	public String getEndTs() {
		return this.convertDate(endTs);
	}

	public void setEndTs(String endTs) {
		this.endTs = endTs;
	}

	public String getStartTs() {
		return this.convertDate(startTs);
	}

	public void setStartTs(String startTs) {
		this.startTs = startTs;
	}

	public List<Cycle> getCycles() {
		return cycles;
	}

	public void setCycles(List<Cycle> cycles) {
		this.cycles = cycles;
	}
}
