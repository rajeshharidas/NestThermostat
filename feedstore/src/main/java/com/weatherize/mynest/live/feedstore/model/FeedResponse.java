package com.weatherize.mynest.live.feedstore.model;

import java.util.List;

public class FeedResponse<T>{
	
	List<T> values;
	
	String cursorMark;
	int size;
	int number;
	
	
	public FeedResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "NestResponse [values=" + values + ", cursorMark=" + cursorMark
				+ ", size=" + size + ", number=" + number + "]";
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


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public FeedResponse(List<T> values, String cursorMark, int size, int number) {
		super();
		this.values = values;
		this.cursorMark = cursorMark;
		this.size = size;
		this.number = number;
	}
	
}
