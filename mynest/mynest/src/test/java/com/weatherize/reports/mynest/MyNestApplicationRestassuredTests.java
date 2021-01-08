package com.weatherize.reports.mynest;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class MyNestApplicationRestassuredTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}

}
