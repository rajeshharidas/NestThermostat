package com.weatherize.mynest.live.streamprocessor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class GCloudUtil {

	@Autowired
	RestTemplate restTemplate;
	
	public GCloudUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
