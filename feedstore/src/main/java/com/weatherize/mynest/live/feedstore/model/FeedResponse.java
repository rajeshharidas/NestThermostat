package com.weatherize.mynest.live.feedstore.model;

import java.util.List;

public class FeedResponse<T>{
	
	List<T> values;
	
	
	public FeedResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "FeedResponse [values=" + values + "]";
	}


	public List<T> getValues() {
		return values;
	}


	public void setValues(List<T> values) {
		this.values = values;
	}


	public FeedResponse(List<T> values) {
		super();
		this.values = values;
	}
	
}
