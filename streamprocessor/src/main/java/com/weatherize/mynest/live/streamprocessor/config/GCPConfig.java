package com.weatherize.mynest.live.streamprocessor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:gcpconfig.properties")
public class GCPConfig {

	@Value( "${com.weatherize.mynest.sdm.clientid}" )
	private String sdmClient;

	@Value( "${com.weatherize.mynest.sdm.clientsecret}" )
	private String sdmSecret;

	@Value("${com.weatherize.mynest.sdm.appid}")
	private String sdmAppId;

	@Value("${com.weatherize.mynest.sdm.deviceid}")
	private String sdmDeviceId;
	
	
	@Value("${com.weather.mynest.sdm.subscriptionid}")
	private String sdmDeviceSubscription;

	@Value("${com.weather.mynest.sdm.subscriptiontopic}")
	private String sdmDeviceTopic;

	
	public String getSdmDeviceSubscription() {
		return sdmDeviceSubscription;
	}

	public void setSdmDeviceSubscription(String sdmDeviceSubscription) {
		this.sdmDeviceSubscription = sdmDeviceSubscription;
	}

	public String getSdmDeviceTopic() {
		return sdmDeviceTopic;
	}

	public void setSdmDeviceTopic(String sdmDeviceTopic) {
		this.sdmDeviceTopic = sdmDeviceTopic;
	}

	public String getSdmDeviceId() {
		return sdmDeviceId;
	}

	public void setSdmDeviceId(String sdmDeviceId) {
		this.sdmDeviceId = sdmDeviceId;
	}

	public String getSdmAppId() {
		return sdmAppId;
	}

	public void setSdmAppId(String sdmAppId) {
		this.sdmAppId = sdmAppId;
	}

	public String getSdmSecret() {
		return sdmSecret;
	}

	public void setSdmSecret(String sdmSecret) {
		this.sdmSecret = sdmSecret;
	}

	public String getSdmClient() {
		return sdmClient;
	}

	public void setSdmClient(String sdmClient) {
		this.sdmClient = sdmClient;
	}

	
}
