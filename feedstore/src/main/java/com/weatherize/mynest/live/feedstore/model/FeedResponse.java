package com.weatherize.mynest.live.feedstore.model;

import java.nio.ByteBuffer;
import java.util.List;

public class FeedResponse<T>{
	
	List<T> values;
	
	String cursorMark;
	
	
	public FeedResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "FeedResponse [values=" + values + ", cursorMark=" + cursorMark + "]";
	}


	public List<T> getValues() {
		return values;
	}


	public void setValues(List<T> values) {
		this.values = values;
	}


	public String getCursorMark() {
		return cursorMark;
	}


	public void setCusorMark(String cursorMark) {
		this.cursorMark = cursorMark;
	}


	public FeedResponse(List<T> values, String cursorMark) {
		super();
		this.values = values;
		this.cursorMark = cursorMark;
	}
	
}
