package com.weatherize.reports.mynest.model;

import java.util.List;

public class NestResponse<T>{
	
	List<T> values;
	
	long totalElements;
	int totalPages;
	int size;
	int number;
	
	
	public NestResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "NestResponse [values=" + values + ", totalElements=" + totalElements + ", totalPages=" + totalPages
				+ ", size=" + size + ", number=" + number + "]";
	}


	public List<T> getValues() {
		return values;
	}


	public void setValues(List<T> values) {
		this.values = values;
	}


	public long getTotalElements() {
		return totalElements;
	}


	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}


	public int getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
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


	public NestResponse(List<T> values, long totalElements, int totalPages, int size, int number) {
		super();
		this.values = values;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.size = size;
		this.number = number;
	}
	
}
