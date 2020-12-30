package com.weatherize.mynest.live.streamprocessor.model;

import java.util.Date;

public class TemperatureData{

	private Date timeofcapture;

	private float temperature;
	private float timetotarget;
	private float humidity;
	private boolean hvacCycleOn;
	private String mode;


	public TemperatureData() {

	}

	public TemperatureData(Date timeofcapture, float temperature, float timetotarget, float humidity,
			boolean hvacCycleOn, String mode) {
		super();
		this.timeofcapture = timeofcapture;
		this.temperature = temperature;
		this.timetotarget = timetotarget;
		this.humidity = humidity;
		this.hvacCycleOn = hvacCycleOn;
		this.mode = mode;
	}
	
	public TemperatureData(TemperatureData data) {
		super();
		this.timeofcapture = data.getTimeofcapture();
		this.temperature = data.getTemperature();
		this.timetotarget =data.getTimetotarget();
		this.humidity =data.getHumidity();
		this.hvacCycleOn =data.isHvacCycleOn();
		this.mode = data.getMode();
	}
	
	public TemperatureData(Builder data) {
		super();
		this.timeofcapture = data.timeofcapture;
		this.temperature = data.temperature;
		this.timetotarget =data.timetotarget;
		this.humidity =data.humidity;
		this.hvacCycleOn =data.hvacCycleOn;
		this.mode = data.mode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemperatureData other = (TemperatureData) obj;
		if (Float.floatToIntBits(humidity) != Float.floatToIntBits(other.humidity))
			return false;
		if (hvacCycleOn != other.hvacCycleOn)
			return false;
		if (mode == null) {
			if (other.mode != null)
				return false;
		} else if (!mode.equals(other.mode))
			return false;
		if (Float.floatToIntBits(temperature) != Float.floatToIntBits(other.temperature))
			return false;
		if (timeofcapture == null) {
			if (other.timeofcapture != null)
				return false;
		} else if (!timeofcapture.equals(other.timeofcapture))
			return false;
		if (Float.floatToIntBits(timetotarget) != Float.floatToIntBits(other.timetotarget))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(humidity);
		result = prime * result + (hvacCycleOn ? 1231 : 1237);
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + Float.floatToIntBits(temperature);
		result = prime * result + ((timeofcapture == null) ? 0 : timeofcapture.hashCode());
		result = prime * result + Float.floatToIntBits(timetotarget);
		return result;
	}
	
	public boolean isHvacCycleOn() {
		return hvacCycleOn;
	}

	public void setHvacCycleOn(boolean hvacCycleOn) {
		this.hvacCycleOn = hvacCycleOn;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}


	public Date getTimeofcapture() {
		return timeofcapture;
	}

	public void setTimeofcapture(Date timeofcapture) {
		this.timeofcapture = timeofcapture;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getTimetotarget() {
		return timetotarget;
	}

	public void setTimetotarget(float timetotarget) {
		this.timetotarget = timetotarget;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "TemperatureData [timeofcapture=" + timeofcapture + ", temperature=" + temperature + ", timetotarget="
				+ timetotarget + ", humidity=" + humidity + ", hvacCycleOn=" + hvacCycleOn + ", mode=" + mode + "]";
	}

	
	 public static class Builder{
		 private Date timeofcapture;

			private float temperature;
			private float timetotarget;
			private float humidity;
			private boolean hvacCycleOn;
			private String mode;
			
			  public Builder timeofcapture(final Date timeofcapture) {
		            this.timeofcapture = timeofcapture;
		            return this;
		        }
			
			  public Builder temperature(final float temperature) {
		            this.temperature = temperature;
		            return this;
		        }
			  
			  public Builder timetotarget(final float timetotarget) {
		            this.timetotarget = timetotarget;
		            return this;
		        }
			  
			  public Builder humidity(final float humidity) {
		            this.humidity = humidity;
		            return this;
		        }
			  
			  public Builder hvacCycleOn(final boolean hvacCycleOn) {
		            this.hvacCycleOn = hvacCycleOn;
		            return this;
		        }
			  
			  public Builder mode(final String mode) {
		            this.mode = mode;
		            return this;
		        }
			  
			  public TemperatureData build() {
		            return new TemperatureData(this);
		        }
			  
	 }
}