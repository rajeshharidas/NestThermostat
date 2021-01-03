package com.weatherize.mynest.live.streamprocessor.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.weatherize.mynest.live.streamprocessor.config.GCPConfig;
import com.weatherize.mynest.live.streamprocessor.model.DeviceEvent;
import com.weatherize.mynest.live.streamprocessor.model.DeviceStat;

public class GCloudUtil {

	private String sdmAuthUrl = "https://www.googleapis.com/oauth2/v4/token?client_id={client_id}&client_secret=(client_secret}&grant_type={grant_type}";
	private String sdmDevicesUrl = "https://smartdevicemanagement.googleapis.com/v1/enterprises/{appid}/devices/{deviceid}";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	GCPConfig gcpConfig;
	
	public GCloudUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String GetSDMAuthToken(String authCode)
	{
		Map<String, String> params = new HashMap<>();
		params.put("client_id", gcpConfig.getSdmClient());
		params.put("client_secret", gcpConfig.getSdmSecret());
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", "https://www.google.com");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(sdmAuthUrl);
		for (Map.Entry<String, String> entry : params.entrySet()) {
		    builder.queryParam(entry.getKey(), entry.getValue());
		}

		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,null, String.class);
		
		JsonObject convertedObject = new Gson().fromJson(response.getBody(), JsonObject.class);
		
		String authToken = convertedObject.get("access_token").getAsString();
		
		return authToken;
	}
	
	public DeviceStat GetSDMDeviceStat(String authToken)
	{
		Map<String, String> params = new HashMap<>();
		params.put("appid", gcpConfig.getSdmAppId());
		params.put("deviceid", gcpConfig.getSdmDeviceId());

		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.setBearerAuth(authToken);
		
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(sdmDevicesUrl);
		for (Map.Entry<String, String> entry : params.entrySet()) {
		    builder.queryParam(entry.getKey(), entry.getValue());
		}

		RequestEntity<HttpHeaders> headers = new RequestEntity<HttpHeaders>(httpHeaders,HttpMethod.GET,URI.create(sdmDevicesUrl));

		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,headers, String.class);
		
		JsonObject convertedObject = new Gson().fromJson(response.getBody(), JsonObject.class);
		
		JsonObject element = convertedObject.getAsJsonObject("traits");
		
		float ambientHumidityPercent = element.getAsJsonObject().get("sdm.devices.traits.Humidity").getAsJsonObject().get("ambientHumidityPercent").getAsFloat();
		String connectivityStatus = element.getAsJsonObject().get("sdm.devices.traits.Connectivity").getAsJsonObject().get("status").getAsString();
		String fanTimerMode = element.getAsJsonObject().get("sdm.devices.traits.Fan").getAsJsonObject().get("timerMode").getAsString();
		String thermostatMode = element.getAsJsonObject().get("sdm.devices.traits.ThermostatMode").getAsJsonObject().get("mode").getAsString();
		String hvacStatus = element.getAsJsonObject().get("sdm.devices.traits.ThermostatHvac").getAsJsonObject().get("status").getAsString();
		float ambientTemperature = element.getAsJsonObject().get("sdm.devices.traits.Temperature").getAsJsonObject().get("ambientTemperatureCelsius").getAsFloat();
		
		
		DeviceStat deviceStat = new DeviceStat(ambientHumidityPercent, connectivityStatus, fanTimerMode,
				thermostatMode,  hvacStatus,  ambientTemperature);
		
		return deviceStat;
	}
	
	public DeviceEvent GetSDMDeviceEvent(String json) {
		
		
		JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);

		JsonObject timestamp = convertedObject.getAsJsonObject("timestamp");

		JsonObject element = convertedObject.getAsJsonObject("traits");
		
		float ambientHumidityPercent = element.get("traits").getAsJsonObject().get("sdm.devices.traits.Humidity").getAsJsonObject().get("ambientHumidityPercent").getAsFloat();
		String connectivityStatus = element.get("traits").getAsJsonObject().get("sdm.devices.traits.Connectivity").getAsJsonObject().get("status").getAsString();
		String fanTimerMode = element.get("traits").getAsJsonObject().get("sdm.devices.traits.Fan").getAsJsonObject().get("timerMode").getAsString();
		String thermostatMode = element.get("traits").getAsJsonObject().get("sdm.devices.traits.ThermostatMode").getAsJsonObject().get("mode").getAsString();
		String hvacStatus = element.get("traits").getAsJsonObject().get("sdm.devices.traits.ThermostatHvac").getAsJsonObject().get("status").getAsString();
		float ambientTemperature = element.get("traits").getAsJsonObject().get("sdm.devices.traits.Temperature").getAsJsonObject().get("ambientTemperatureCelsius").getAsFloat();
		
		
		DeviceEvent deviceEvent = new DeviceEvent(timestamp.getAsString(),ambientHumidityPercent, connectivityStatus, fanTimerMode,
				thermostatMode,  hvacStatus,  ambientTemperature);

		
		return deviceEvent;
		
	}
	
	
}
