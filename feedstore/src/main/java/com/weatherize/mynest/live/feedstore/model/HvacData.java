package com.weatherize.mynest.live.feedstore.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("nesteventdata")
public class HvacData {
	
	@PrimaryKeyColumn(name = "eventid", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private UUID eventid;
	
	private LocalDateTime timeofevent;
	
	private String traitkey;
	
	private String traitvalue;
	
	public HvacData() {

	}

	public HvacData(UUID eventid, LocalDateTime timeofevent, String traitkey, String traitvalue) {
		super();
		this.eventid = eventid;
		this.timeofevent = timeofevent;
		this.traitkey = traitkey;
		this.traitvalue = traitvalue;
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

	public String getTraitkey() {
		return traitkey;
	}

	public void setTraitkey(String traitkey) {
		this.traitkey = traitkey;
	}

	public String getTraitvalue() {
		return traitvalue;
	}

	public void setTraitvalue(String traitvalue) {
		this.traitvalue = traitvalue;
	}

	@Override
	public String toString() {
		return "HvacData [eventid=" + eventid + ", timeofevent=" + timeofevent + ", traitkey=" + traitkey
				+ ", traitvalue=" + traitvalue + "]";
	}



}