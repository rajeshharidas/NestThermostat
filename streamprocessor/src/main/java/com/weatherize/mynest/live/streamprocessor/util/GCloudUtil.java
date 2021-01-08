package com.weatherize.mynest.live.streamprocessor.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.weatherize.mynest.live.streamprocessor.config.GCPConfig;
import com.weatherize.mynest.live.streamprocessor.model.DeviceEvent;
import com.weatherize.mynest.live.streamprocessor.model.DeviceStat;

public class GCloudUtil {

	private static final Logger logger = LoggerFactory.getLogger(GCloudUtil.class);

	private String sdmAuthUrl = "https://www.googleapis.com/oauth2/v4/token";
	private String sdmDevicesUrl = "https://smartdevicemanagement.googleapis.com/v1/enterprises/{appid}/devices/{deviceid}";

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	GCPConfig gcpConfig;

	public GCloudUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String GetSDMAuthToken(String authCode) {
		Map<String, String> params = new HashMap<>();
		params.put("client_id", gcpConfig.getSdmClient());
		params.put("client_secret", gcpConfig.getSdmSecret());
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", "https://www.google.com");
		params.put("code", authCode);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(sdmAuthUrl);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}

		logger.info("Querying URL: " + builder.toUriString());
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null,
				String.class);

		logger.info("Response from: " + builder.toUriString() + " " + response.getBody());

		JsonObject convertedObject = new Gson().fromJson(response.getBody(), JsonObject.class);

		String authToken = convertedObject.get("access_token").getAsString();
		logger.info("Auth token: " + authToken);

		return authToken;
	}

	public DeviceStat GetSDMDeviceStat(String authToken) {
		Map<String, String> params = new HashMap<>();
		params.put("appid", gcpConfig.getSdmAppId());
		params.put("deviceid", gcpConfig.getSdmDeviceId());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		httpHeaders.setBearerAuth(authToken);

		UriComponents builder = UriComponentsBuilder.fromHttpUrl(sdmDevicesUrl).buildAndExpand(params);

		logger.info("Constructing headers for : " + builder.toUriString());

		HttpEntity<String> headers = new HttpEntity<String>("parameters", httpHeaders);

		logger.info("Querying Device URL: " + builder.toUriString());
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, headers,
				String.class);

		JsonObject convertedObject = new Gson().fromJson(response.getBody(), JsonObject.class);

		JsonObject element = convertedObject.getAsJsonObject("traits");

		JsonElement humidityElement = element.getAsJsonObject().get("sdm.devices.traits.Humidity");
		JsonElement connectivityElement = element.getAsJsonObject().get("sdm.devices.traits.Connectivity");
		JsonElement fanTimerElement = element.getAsJsonObject().get("sdm.devices.traits.Fan");
		JsonElement thermostatElement = element.getAsJsonObject().get("sdm.devices.traits.ThermostatMode");
		JsonElement hvacElement = element.getAsJsonObject().get("sdm.devices.traits.ThermostatHvac");
		JsonElement tempElement = element.getAsJsonObject().get("sdm.devices.traits.Temperature");

		float ambientHumidityPercent = humidityElement != null
				? humidityElement.getAsJsonObject().get("ambientHumidityPercent").getAsFloat()
				: 0;
		String connectivityStatus = connectivityElement != null
				? connectivityElement.getAsJsonObject().get("status").getAsString()
				: "Unknown";
		String fanTimerMode = fanTimerElement != null ? fanTimerElement.getAsJsonObject().get("timerMode").getAsString()
				: "Unknown";
		String thermostatMode = thermostatElement != null
				? thermostatElement.getAsJsonObject().get("mode").getAsString()
				: "Unknown";
		String hvacStatus = hvacElement != null ? hvacElement.getAsJsonObject().get("status").getAsString() : "Unknown";
		float ambientTemperature = tempElement != null
				? tempElement.getAsJsonObject().get("ambientTemperatureCelsius").getAsFloat()
				: 0;

		DeviceStat deviceStat = new DeviceStat(ambientHumidityPercent, connectivityStatus, fanTimerMode, thermostatMode,
				hvacStatus, ambientTemperature);

		return deviceStat;
	}

	public DeviceEvent GetSDMDeviceEvent(String json) {

		JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);

		JsonElement eventId = convertedObject.get("eventId");

		JsonElement timestamp = convertedObject.get("timestamp");

		JsonObject resourceUpdate = convertedObject.get("resourceUpdate").getAsJsonObject();

		JsonObject element = resourceUpdate.get("traits").getAsJsonObject();

		JsonElement humidityElement = element.get("sdm.devices.traits.Humidity");
		JsonElement connectivityElement = element.get("sdm.devices.traits.Connectivity");
		JsonElement fanTimerElement = element.get("sdm.devices.traits.Fan");
		JsonElement thermostatElement = element.get("sdm.devices.traits.ThermostatMode");
		JsonElement hvacElement = element.get("sdm.devices.traits.ThermostatHvac");
		JsonElement tempElement = element.get("sdm.devices.traits.Temperature");

		Map<String, String> eventTraits = new HashMap<String, String>();

		if (humidityElement != null)
			eventTraits.put("humidity", humidityElement.getAsJsonObject().get("ambientHumidityPercent").getAsString());
		if (connectivityElement != null)
			eventTraits.put("connectivityStatus", connectivityElement.getAsJsonObject().get("status").getAsString());
		if (fanTimerElement != null)
			eventTraits.put("fanTimer", fanTimerElement.getAsJsonObject().get("timerMode").getAsString());
		if (thermostatElement != null)
			eventTraits.put("thermostatMode", thermostatElement.getAsJsonObject().get("mode").getAsString());
		if (hvacElement != null)
			eventTraits.put("hvacStatus", hvacElement.getAsJsonObject().get("status").getAsString());
		if (tempElement != null)
			eventTraits.put("temperature",
					tempElement.getAsJsonObject().get("ambientTemperatureCelsius").getAsString());

		DeviceEvent deviceEvent = new DeviceEvent(eventId.getAsString(), timestamp.getAsString(), eventTraits);

		return deviceEvent;

	}

}
